package com.lang.token.filter;

import com.lang.token.core.DeFaultTokenOperation;
import com.lang.token.core.TokenOperation;
import com.lang.token.model.TokenInfo;
import com.lang.token.model.TokenUser;
import com.lang.token.model.TokenUserInfo;
import com.lang.token.sql.dao.UserDao;
import com.lang.token.util.PropertiesUtils;
import com.lang.token.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liu_yeye
 * @date 2018-05-11 15:54
 */
public abstract class LangFilter implements Filter {
    private TokenOperation tokenOperation = new DeFaultTokenOperation();
    private UserDao userDao = new UserDao();
    private static final String PARAM_NAME_LOGINUSERNAME = "loginUsername";
    private static final String PARAM_NAME_LOGINPASSWORD = "loginPassword";
    private String loginUsername = null;
    private String loginPassword = null;
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request =(HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String servletPath = request.getServletPath();
        String tokenPath = PropertiesUtils.getValue("token_path");
        response.setHeader("contentType","application/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        try{
            if(servletPath.startsWith(tokenPath)){
                String opt = request.getParameter("opt");
                String params = request.getParameter("params");
                String admin = request.getParameter("admin");
                validServletPath(opt,params,admin);
                Map<String,String> paramMap = params2Map(params);
                if("auth".equals(opt)){
                    //请求token
                    String userName = paramMap.get("userName");
                    String passWord = paramMap.get("passWord");
                    String signature = paramMap.get("signature");
                    validParam(userName,"userName参数不能为空");
                    validParam(passWord,"passWord参数不能为空");
                    validParam(signature,"signature参数不能为空");
                    TokenInfo tokenInfo = tokenOperation.validateUser(userName,passWord,signature);
                    String token = tokenOperation.encryptToken(tokenInfo);
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("{\"token\":\"").append(token).append("\",")
                            .append("\"expires\":\"").append(tokenInfo.getExpires()).append("\"}");
                    out.write(stringBuilder.toString());
                    out.flush();
                }else {
                    //验证后台登陆管理
                    Map<String,String> adminMap = params2Map(admin);
                    String loginName = adminMap.get(PARAM_NAME_LOGINUSERNAME);
                    String loginPwd = adminMap.get(PARAM_NAME_LOGINPASSWORD);
                    validParam(loginName,"loginUsername参数不能为空");
                    validParam(loginPwd,"loginPassword参数不能为空");
                    if(this.loginUsername.equals(loginName)&&this.loginPassword.equals(loginPwd)){
                        //添加token授权用户
                        if("add".equals(opt)){
                            String client = paramMap.get("client");
                            String clientType = paramMap.get("clientType");
                            String name = paramMap.get("name");
                            String email = paramMap.get("email");
                            String signature = paramMap.get("signature");
                            validParam(client,"client参数不能为空");
                            validParam(clientType,"clientType参数不能为空");
                            validParam(name,"name参数不能为空");
                            validParam(email,"email参数不能为空");
                            validParam(signature,"signature参数不能为空");
                            TokenUserInfo tokenUserInfo = new TokenUserInfo();
                            tokenUserInfo.setClient(client);
                            tokenUserInfo.setClientType(clientType);
                            tokenUserInfo.setName(name);
                            tokenUserInfo.setEmail(email);
                            //得到加密后的token授权用户
                            TokenUser tokenUser = tokenOperation.encryptUser(tokenUserInfo,signature);
                            userDao.addUser(tokenUser);
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("{\"userName\":\"").append(tokenUser.getUserName()).append("\",")
                                    .append("\"passWord\":\"").append(tokenUser.getPassWord()).append("\"}");
                            out.write(stringBuilder.toString());
                            out.flush();
                        }else if("delete".equals(opt)){
                            String userName = paramMap.get("userName");
                            validParam(userName,"userName参数不能为空");
                            userDao.delUser(userName);
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("{\"result\":\"").append("删除授权用户").append(userName).append("成功").append("\"}");
                            out.write(stringBuilder.toString());
                            out.flush();
                        }else {
                            throw new RuntimeException("请求的url参数opt无效");
                        }
                    }else{
                        throw new RuntimeException("请求的url参数admin验证失败");
                    }
                }
            }else{
                //从headertoken
                String token = request.getHeader("token");
                if(token == null){
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("{\"result\":\"").append("请求头信息token为空").append("\"}");
                    out.write(stringBuilder.toString());
                    out.flush();
                }else{
                    //解密token
                    TokenInfo tokenInfo = tokenOperation.decryptToken(token);
                    //验证token
                    tokenOperation.validateToken(tokenInfo);
                    response.reset();
                    chain.doFilter(request, response);
                }
            }
        }catch (Exception e){
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("{\"result\":\"").append(e.getMessage()).append("\"}");
            out.write(stringBuilder.toString());
            out.flush();
            e.printStackTrace();
        }
    }
    private void validServletPath(String opt ,String params,String admin){
        if(StringUtils.isEmpty(opt)){
            throw  new RuntimeException("请求的url参数opt有误");
        }
        if(StringUtils.isEmpty(params)){
            throw  new RuntimeException("请求的url参数params有误");
        }
        if(!"auth".equals(opt)&&StringUtils.isEmpty(admin)){
            throw  new RuntimeException("请求的url参数admin有误");
        }
    }
    private void validParam(String param,String message){
        if(StringUtils.isEmpty(param)){
            throw new RuntimeException(message);
        }
    }
    private Map<String,String> params2Map(String params){
        try{
            String[] paramJson = params.split(",");
            Map<String,String> paramMap = new HashMap<>(8);
            for(String param:paramJson){
                String[] singleParam = param.split(":");
                if(singleParam.length!=2){
                    throw  new RuntimeException("请求的json参数格式错误");
                }
                String[] onekeys =singleParam[0].split("\"");
                String key = onekeys[1];
                String[] oneValues = singleParam[1].split("\"");
                String value = oneValues[1];
                if(StringUtils.isEmpty(key)||StringUtils.isEmpty(value)){
                    throw  new RuntimeException("请求的json参数格式错误");
                }
                paramMap.put(key,value);
            }
            return paramMap;
        }catch (Exception e){
            throw  new RuntimeException("请求的json参数格式错误",e);
        }
    }
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String loginUsername = filterConfig.getInitParameter("loginUsername");
        String loginPassword = filterConfig.getInitParameter("loginPassword");
        if(StringUtils.isEmpty(loginPassword)){
            throw  new RuntimeException("filter initParameter：loginPassword不存在");
        }
        if(StringUtils.isEmpty(loginUsername)){
            throw  new RuntimeException("filter initParameter：loginUsername不存在");
        }
        this.loginUsername = loginUsername;
        this.loginPassword = loginPassword;
    }

    @Override
    public void destroy() {

    }
}

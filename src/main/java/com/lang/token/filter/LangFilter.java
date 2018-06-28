package com.lang.token.filter;

import com.lang.token.core.DeFaultTokenOperation;
import com.lang.token.core.TokenOperation;
import com.lang.token.model.TokenInfo;
import com.lang.token.util.PropertiesUtils;
import com.lang.token.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author liu_yeye
 * @date 2018-05-11 15:54
 */
public abstract class LangFilter implements Filter {
    private TokenOperation tokenOperation = new DeFaultTokenOperation();
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request =(HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String servletPath = request.getServletPath();
        String tokenPath = PropertiesUtils.getValue("token_path");
        PrintWriter out = response.getWriter();
        response.setContentType("application/json; charset=utf-8");
        try{
            if(tokenPath.equals(servletPath)){
                //请求token
                String userName = request.getParameter("userName");
                String passWord = request.getParameter("passWord");
                String signature = request.getParameter("signature");
                TokenInfo tokenInfo = tokenOperation.validateUser(userName,passWord,signature);
                String token = tokenOperation.encryptToken(tokenInfo);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("{\"token\":\"").append(token).append("\",")
                        .append("\"expires\":\"").append(tokenInfo.getExpires()).append("\"}");
                out.write(stringBuilder.toString());
                out.flush();
            }else{
                //从headertoken
                String token = request.getHeader("token");
                if(token == null){
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("{\"result\":\"").append("请求头信息token为空").append("\"}");
                    out.write(stringBuilder.toString());
                    out.flush();
                }
                //解密token
                TokenInfo tokenInfo = tokenOperation.decryptToken(token);
                //验证token
                tokenOperation.validateToken(tokenInfo);
                chain.doFilter(request, response);
            }
        }catch (Exception e){
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("{\"result\":\"").append(e.getMessage()).append("\"}");
            out.write(stringBuilder.toString());
            out.flush();
            e.printStackTrace();
        }
    }
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}

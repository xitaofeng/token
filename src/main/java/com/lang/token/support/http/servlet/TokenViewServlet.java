package com.lang.token.support.http.servlet;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.lang.token.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Properties;

/**
 * @author liu_yeye
 * @date 2018-05-11 10:27
 */
public class TokenViewServlet extends  HttpServlet   {
    private static final Logger LOG = LoggerFactory.getLogger(TokenViewServlet.class);
    public static final String PARAM_NAME_LOGINUSERNAME = "loginUsername";
    public static final String PARAM_NAME_LOGINPASSWORD = "loginPassword";
    protected String loginUsername = null;
    protected String loginPassword = null;

    @Override
    public void init() throws ServletException{
        this.initAuthEnv();
    }
    private void initAuthEnv() {
        String paramloginUsername = this.getInitParameter("loginUsername");
        if(!StringUtils.isEmpty(paramloginUsername)){
            this.loginUsername=paramloginUsername;
        }
        String paramloginPassword = this.getInitParameter("loginPassword");
        if(!StringUtils.isEmpty(paramloginPassword)){
            this.loginPassword=paramloginPassword;
        }
    }
    private boolean checkLogin(HttpServletRequest request) {
        String usernameParam = request.getParameter("loginUsername");
        String passwordParam = request.getParameter("loginPassword");
        if (null != this.loginUsername && null != this.loginPassword) {
            return this.loginUsername.equals(usernameParam) && this.loginPassword.equals(passwordParam);
        } else {
            return false;
        }
    }
    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

}

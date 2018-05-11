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
    private static final String PARAM_NAME_URL="url";
    private static final String PARAM_NAME_DRIVERCLASSNAME="driverClassName";
    private static final String PARAM_NAME_USERNAME="userName";
    private static final String PARAM_NAME_PASSWORD="passWord";
    public static final String PARAM_NAME_LOGINUSERNAME = "loginUsername";
    public static final String PARAM_NAME_LOGINPASSWORD = "loginPassword";
    protected String url = null;
    protected String driverClassName = null;
    protected String userName = null;
    protected String passWord = null;
    protected String loginUsername = null;
    protected String loginPassword = null;

    @Override
    public void init() throws ServletException{
        this.initAuthEnv();
        this.readProperties();
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
    private void readProperties(){
        Properties pps = new Properties();
        try {
            InputStream in = new BufferedInputStream(new FileInputStream("token.properties"));
            pps.load(in);
            String prop_url = pps.getProperty(DruidDataSourceFactory.PROP_URL);
            String prop_driverclassname = pps.getProperty(DruidDataSourceFactory.PROP_DRIVERCLASSNAME);
            String prop_username = pps.getProperty(DruidDataSourceFactory.PROP_USERNAME);
            String prop_password = pps.getProperty(DruidDataSourceFactory.PROP_PASSWORD);
            if(StringUtils.isEmpty(prop_url)){
                throw new IOException("URL is empty");
            }else{
                this.url=prop_url;
            }
            if(StringUtils.isEmpty(prop_driverclassname)){
                throw new IOException("prop_driverclassname is empty");
            }else{
                this.driverClassName=prop_driverclassname;
            }
            if(StringUtils.isEmpty(prop_username)){
                throw new IOException("ROOT is empty");
            }else{
                this.userName=prop_username;
            }
            if(StringUtils.isEmpty(prop_password)){
                throw new IOException("PASSWORD is empty");
            }else{
                this.passWord=prop_password;
            }
            in.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    private void writeProperties() {
        Properties prop = new Properties();
        try {
            OutputStream fos = new FileOutputStream("token.properties");
            prop.setProperty(DruidDataSourceFactory.PROP_URL , this.url);
            prop.store(fos, "Update '" + DruidDataSourceFactory.PROP_URL  + "' value");
            prop.setProperty(DruidDataSourceFactory.PROP_DRIVERCLASSNAME , this.driverClassName);
            prop.store(fos, "Update '" + DruidDataSourceFactory.PROP_DRIVERCLASSNAME  + "' value");
            prop.setProperty(DruidDataSourceFactory.PROP_USERNAME , this.userName);
            prop.store(fos, "Update '" + DruidDataSourceFactory.PROP_USERNAME  + "' value");
            prop.setProperty(DruidDataSourceFactory.PROP_PASSWORD , this.passWord);
            prop.store(fos, "Update '" + DruidDataSourceFactory.PROP_PASSWORD  + "' value");
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
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

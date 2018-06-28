package com.lang.token.sql.pool;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.lang.token.util.PropertiesUtils;
import com.lang.token.util.StringUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author liu_yeye
 * @date 2018-05-11 10:12
 */
public class DruidPool {

    private static DruidDataSource dataSource = null;
    static {
        try {
            String prop_url = PropertiesUtils.getValue(DruidDataSourceFactory.PROP_URL);
            String prop_driverclassname =  PropertiesUtils.getValue(DruidDataSourceFactory.PROP_DRIVERCLASSNAME);
            String prop_username =  PropertiesUtils.getValue(DruidDataSourceFactory.PROP_USERNAME);
            String prop_password =  PropertiesUtils.getValue(DruidDataSourceFactory.PROP_PASSWORD);
            if(StringUtils.isEmpty(prop_url)){
                throw new IOException("URL is empty");
            }
            if(StringUtils.isEmpty(prop_driverclassname)){
                throw new IOException("prop_driverclassname is empty");
            }
            if(StringUtils.isEmpty(prop_username)){
                throw new IOException("ROOT is empty");
            }
            if(StringUtils.isEmpty(prop_password)){
                throw new IOException("PASSWORD is empty");
            }
            Map<String,String> druidMap = new HashMap<String,String>();
            druidMap.put(DruidDataSourceFactory.PROP_URL, prop_url);
            druidMap.put(DruidDataSourceFactory.PROP_DRIVERCLASSNAME, prop_driverclassname);
            druidMap.put(DruidDataSourceFactory.PROP_USERNAME, prop_username);
            druidMap.put(DruidDataSourceFactory.PROP_PASSWORD, prop_password);
            dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(druidMap);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public  DruidPooledConnection druidDataSource() {
        try {
            return dataSource.getConnection();
        }catch (Exception e){
           e.printStackTrace();
        }
        return null;
    }
}

package com.lang.token.sql.pool;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;
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
    public  DruidPooledConnection DruidDataSource() throws Exception{
        Properties pps = new Properties();
        DruidDataSource dataSource = null;
        try {
            InputStream in = new BufferedInputStream(new FileInputStream("token.properties"));
            pps.load(in);
            String prop_url = pps.getProperty(DruidDataSourceFactory.PROP_URL);
            String prop_driverclassname = pps.getProperty(DruidDataSourceFactory.PROP_DRIVERCLASSNAME);
            String prop_username = pps.getProperty(DruidDataSourceFactory.PROP_USERNAME);
            String prop_password = pps.getProperty(DruidDataSourceFactory.PROP_PASSWORD);
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
            in.close();
            Map<String,String> druidMap = new HashMap<String,String>();
            druidMap.put(DruidDataSourceFactory.PROP_URL, prop_url);
            druidMap.put(DruidDataSourceFactory.PROP_DRIVERCLASSNAME, prop_driverclassname);
            druidMap.put(DruidDataSourceFactory.PROP_USERNAME, prop_username);
            druidMap.put(DruidDataSourceFactory.PROP_PASSWORD, prop_password);
            dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(druidMap);
        }catch (FileNotFoundException e){
            throw e;
        }catch (IOException e){
            throw e;
        }
        return dataSource == null?null:dataSource.getConnection();
    }
}

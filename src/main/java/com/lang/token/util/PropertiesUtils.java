package com.lang.token.util;

import com.lang.token.core.TokenException;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author liu_yeye
 * @date 2018-06-28 22:50
 */
public class PropertiesUtils {
    private static Properties pps = new Properties();
    static{
        try{
            pps.load(PropertiesUtils.class.getResourceAsStream("/token.properties"));
            if(pps.getProperty("token_path") == null){
                throw  new TokenException("token请求路径：token_path不存在");
            }
            if(pps.getProperty("aes_key") == null){
                throw  new TokenException("aes加密密钥：aes_key不存在");
            }
            if(pps.getProperty("aes_key").length()<16){
                throw  new TokenException("aes加密密钥长度不能小于16位");
            }
            if(pps.getProperty("expires")!=null&&Long.valueOf(pps.getProperty("expires"))<=0){
                throw  new TokenException("token有效时间：expires有误");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static String getValue(String key){
        return pps.getProperty(key);
    }
}

package com.lang.token.util;

import com.lang.token.model.TokenUser;
import com.lang.token.model.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * 加密解密
 * @author liu_yeye
 * @date 2018-05-11 10:02
 */
public class EncryptionUtils {
    private static final Logger LOG = LoggerFactory.getLogger(EncryptionUtils.class);
    private static final String  salt = "./1h/*029kh2lj11jmjxr3k1c12b&2e!";
    public static TokenUser encryptUserInfo(UserInfo userInfo){
        TokenUser tokenUser = null;
        return tokenUser;
    }
    public static byte[] toByteArray(Object o){
        byte[] bytes = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(o);
            objectOutputStream.flush();
            bytes = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            LOG.error("objectToByteArray failed, " + e);
        } finally {
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    LOG.error("close objectOutputStream failed, " + e);
                }
            }
            if (byteArrayOutputStream != null) {
                try {
                    byteArrayOutputStream.close();
                } catch (IOException e) {
                    LOG.error("close byteArrayOutputStream failed, " + e);
                }
            }
        }
        return bytes;
    }
}

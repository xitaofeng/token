package com.lang.token.core;

import com.lang.token.model.TokenInfo;
import com.lang.token.util.aes.AesUtils;
import com.lang.token.util.base64.Base64Utils;
import com.lang.token.util.kyro.KryoUtil;
import com.lang.token.util.md5.MD5Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @author liu_yeye
 * @date 2018-05-12 14:15
 */
public class AbstractEncryption implements Encryption {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractEncryption.class);
    protected  static final String  salt = "./1h/*029kh2lj11jmjxr3k1c12b&2e!";

    @Override
    public String base64EncoderStr(byte[] strBytes) {
        return Base64Utils.getEncoder().encodeToString(strBytes);
    }
    @Override
    public byte[] base64DecoderStr(String str) {
        return Base64Utils.getDecoder().decode(str);
    }
    @Override
    public byte[] aesEncryptBytes(byte[] bytes,String key) {
        return AesUtils.encrypt(bytes,key);
    }
    @Override
    public byte[] aesDecryptBytes(byte[] bytes,String key){
        return AesUtils.decrypt(bytes,key);
    }
    @Override
    public   Object byteArrayToObject(byte[] bytes,Class<?> c) {
        return KryoUtil.readObjectFromByteArray(bytes,c);
    }
    @Override
    public  byte[] objectToByteArray(Object o){
        return KryoUtil.writeObjectToByteArray(o);
    }

    @Override
    public String md5Str(byte[] bytes) {
        return MD5Utils.getEncrypt().toMd5String(base64EncoderStr(bytes));
    }
    @Override
    public String md5Str(String str) {
        return MD5Utils.getEncrypt().toMd5String(str);
    }
}

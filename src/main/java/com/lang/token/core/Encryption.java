package com.lang.token.core;

/**
 * @author liu_yeye
 * @date 2018-05-12 14:11
 */

public interface Encryption {
    /**
     * Byte数组转对象
     * @param bytes
     * @return
     */
    Object byteArrayToObject(byte[] bytes, Class<?> c);
    /**
     * 对象转Byte数组
     * @param o
     * @return
     */
    byte[] objectToByteArray(Object o);
    String base64EncoderStr(byte[] strBytes);
    byte[] base64DecoderStr(String str);
    byte[] aesEncryptBytes(byte[] bytes, String key);
    byte[] aesDecryptBytes(byte[] bytes, String key);
    String md5Str(byte[] bytes);
    String md5Str(String str);
}

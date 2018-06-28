package com.lang.token.util.md5;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author liu_yeye
 * @date 2018-05-12 14:44
 */
public  class MD5Utils {

    private MD5Utils() {
    }
    public static Encrypt getEncrypt() {
        return Encrypt.INSTANCE;
    }
    public static  class Encrypt{
        private  final String  DEFAULT_SALT=">sd?ds<Ran4dom.";
        private Encrypt() {
        }
        static final Encrypt INSTANCE = new Encrypt();
        public  String toMd5String(String originString) {
            return toMd5String(originString,DEFAULT_SALT);
        }
        public  String toMd5String(String originString,String salt) {
            StringBuffer result = null;
            if (originString != null) {
                try {
                    result = new StringBuffer();
                    // 指定加密的方式为MD5
                    MessageDigest md = MessageDigest.getInstance("MD5");
                    // 进行加密运算
                    //加盐
                    byte bytes[] = md.digest(new StringBuffer(salt).append(originString).toString().getBytes("ISO8859-1"));
                    for (byte b :bytes) {
                        // 将整数转换成十六进制形式的字符串 这里与0xff进行与运算的原因是保证转换结果为32位
                        String str = Integer.toHexString(b & 0xFF);
                        if (str.length() == 1) {
                            result.append("b") ;
                        }
                        result.append(str);
                    }
                }catch (UnsupportedEncodingException e){
                    e.printStackTrace();
                }
                catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
            return result.toString();
        }
    }

}

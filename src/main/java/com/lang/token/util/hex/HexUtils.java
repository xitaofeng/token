package com.lang.token.util.hex;

import java.util.Stack;

/**
 * @author liu_yeye
 * @date 2018-06-28 13:57
 */
public class HexUtils {
    private static final String C_CODES_STRING = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    /***
     * 将10进制转换为任意进制
     * @param intVal
     * @param base <=42
     * @return
     */
    public static String int2CodeString(long intVal,int base)  {

        int w_code_len = C_CODES_STRING.length();
        if (base >w_code_len){
            return null;
        }
        StringBuilder sb = new StringBuilder();
        Stack<String> s=new Stack<String>();
        while (intVal!=0){
            s.push(C_CODES_STRING.charAt((int)(intVal%base))+"");
            intVal/=base;
        }
        while (!s.empty()){
            sb.append(s.pop());
        }
        return sb.length()==0?"0":sb.toString();
    }
    /***
     *  任何进制转换,
     * @param s
     * @param srcBase s的进制
     * @param destBase 要转换为的进制
     * @return
     */
    public static String BaseConvert(String s,int srcBase,int destBase){
        if(srcBase == destBase){
            return s;
        }
        char[] chars = s.toCharArray();
        int len = chars.length;
        if(destBase != 10){//目标进制不是十进制 先转化为十进制
            s = BaseConvert(s,srcBase,10);
        }else{
            long n = 0;
            for(int i = len - 1; i >=0; i--){
                n+=C_CODES_STRING.indexOf(chars[i])*Math.pow(srcBase, len - i - 1);
            }
            return String.valueOf(n);
        }
        return int2CodeString(Integer.valueOf(s),destBase);
    }
    /**
     * 字符串转换成十六进制字符串
     */

    public static String str2HexStr(String str) {

        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder("");
        byte[] bs = str.getBytes();
        int bit;
        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
        }
        return sb.toString();
    }

    /**
     *
     * 十六进制转换字符串
     */

    public static String hexStr2Str(String hexStr) {
        String str = "0123456789ABCDEF";
        char[] hexs = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];
        int n;
        for (int i = 0; i < bytes.length; i++) {
            n = str.indexOf(hexs[2 * i]) * 16;
            n += str.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }
        return new String(bytes);
    }

    /**
     * bytes转换成十六进制字符串
     */
    public static String byte2HexStr(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
            // if (n<b.length-1) hs=hs+":";
        }
        return hs.toUpperCase();
    }

    private static byte uniteBytes(String src0, String src1) {
        byte b0 = Byte.decode("0x" + src0).byteValue();
        b0 = (byte) (b0 << 4);
        byte b1 = Byte.decode("0x" + src1).byteValue();
        byte ret = (byte) (b0 | b1);
        return ret;
    }

    /**
     * bytes转换成十六进制字符串
     */
    public static byte[] hexStr2Bytes(String src) {
        int m = 0, n = 0;
        int l = src.length() / 2;
        System.out.println(l);
        byte[] ret = new byte[l];
        for (int i = 0; i < l; i++) {
            m = i * 2 + 1;
            n = m + 1;
            ret[i] = uniteBytes(src.substring(i * 2, m), src.substring(m, n));
        }
        return ret;
    }
}

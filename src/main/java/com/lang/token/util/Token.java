package com.lang.token.util;

/**
 * @author liu_yeye
 * @date 2018-05-11 16:38
 */
public class Token {
    private static final byte defaultTokenMaxNum = 32;
    private static String[] tokenArray = null;
    private static int size=0;
    private Token() {
    }
    private static class TokenInstance{
        private static Token instance=new Token();
    }
    public static  Token getInstance(byte tokenMaxNum ){
        Token token = TokenInstance.instance;
        if(tokenArray == null){
            tokenArray = new String[tokenMaxNum];
            size = 0;
        }
        return token;
    }
    public static Token getInstance( ){
        return  getInstance(defaultTokenMaxNum);
    }
    public  String[] getTokenArray() {
        return tokenArray;
    }
}

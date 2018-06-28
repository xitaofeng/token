package com.lang.token.util;

import com.lang.token.model.TokenEntity;

import java.util.HashMap;

/**
 * @author liu_yeye
 * @date 2018-05-11 16:38
 */
public class Token {
    private static final int DEFAULT_TOKEN_MAXNUM = 2<<5;
    private static final int  TOKEN_MAXNUM = 1 << 30;
    private static  TokenEntity[] tokenArray = null;
    private static int size=0;
    private Token() {
    }
    static class TokenInstance{
         static final Token INSTANCE=new Token();
    }
    public static  Token getInstance(int tokenMaxNum ){
        Token token = TokenInstance.INSTANCE;
        if(tokenArray == null){
            tokenMaxNum = roundUpToPowerOf2(tokenMaxNum);
            tokenArray = new TokenEntity[tokenMaxNum];
            size = 0;
        }
        return token;
    }
    public static Token getInstance( ){
        return  getInstance(DEFAULT_TOKEN_MAXNUM);
    }
    public  TokenEntity[] getTokenArray() {
        return tokenArray;
    }
    private static int roundUpToPowerOf2(int number) {
        return number >= TOKEN_MAXNUM
                ? TOKEN_MAXNUM
                : (number > 1) ? Integer.highestOneBit((number - 1) << 1) : 1;
    }
}

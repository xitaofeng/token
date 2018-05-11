package com.lang.token.util;

/**
 * @author liu_yeye
 * @date 2018-05-11 15:15
 */
 abstract class AbstractToken implements LangTokenUtil {
    private Token token;
    AbstractToken( Token token){
        this.token = token;
    }
    public Token getToken(){
        return token;
    }
}

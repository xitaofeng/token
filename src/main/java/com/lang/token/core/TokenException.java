package com.lang.token.core;

import java.io.IOException;

/**
 * @author liu_yeye
 * @date 2018-08-04 13:54
 */
public class TokenException extends Exception{
    public TokenException() {
    }

    public TokenException(String message) {
        super(message);
    }

    public TokenException(String message, Throwable cause) {
        super(message, cause);
    }
}

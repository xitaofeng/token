package com.lang.token.util;

import java.util.Arrays;

/**
 * @author liu_yeye
 * @date 2018-05-11 9:56
 */
public class DefaultTokenUtils extends AbstractToken {
    DefaultTokenUtils(Token token) {
        super(token);
    }
    @Override
    public boolean push(String accessToken) {
        return false;
    }
    @Override
    public boolean contains(String accessToken) {
        return false;
    }

    @Override
    public boolean pop(String accessToken) {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }
}

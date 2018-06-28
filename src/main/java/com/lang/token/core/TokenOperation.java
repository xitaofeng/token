package com.lang.token.core;

import com.lang.token.model.TokenInfo;

/**
 * @author liu_yeye
 * @date 2018-06-27 22:48
 */
public interface TokenOperation {
    TokenInfo validateUser(String userName, String passWord, String signature);
    String encryptToken(TokenInfo tokenInfo);
    TokenInfo decryptToken(String token);
    void validateToken(TokenInfo tokenInfo);
}

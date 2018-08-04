package com.lang.token.core;

import com.lang.token.model.TokenInfo;
import com.lang.token.model.TokenUser;
import com.lang.token.model.TokenUserInfo;

/**
 * @author liu_yeye
 * @date 2018-06-27 22:48
 */
public interface TokenOperation {
    TokenInfo validateUser(String userName, String passWord, String signature) throws TokenException;
    String encryptToken(TokenInfo tokenInfo) throws TokenException;
    TokenInfo decryptToken(String token) throws TokenException;
    void validateToken(TokenInfo tokenInfo) throws TokenException;
    TokenUser encryptUser(TokenUserInfo tokenUserInfo, String signature) throws TokenException;
}

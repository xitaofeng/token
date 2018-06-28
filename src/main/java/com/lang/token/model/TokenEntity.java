package com.lang.token.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author liu_yeye
 * @date 2018-05-12 12:54
 */
public class TokenEntity implements Serializable {
    private String accessToken;
    private TokenInfo tokenInfo;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public TokenInfo getTokenInfo() {
        return tokenInfo;
    }

    public void setTokenInfo(TokenInfo tokenInfo) {
        this.tokenInfo = tokenInfo;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TokenEntity that = (TokenEntity) o;
        return Objects.equals(accessToken, that.accessToken);
    }
    @Override
    public int hashCode() {
        return Objects.hash(accessToken);
    }
}

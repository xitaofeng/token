package com.lang.token.model;

import java.io.Serializable;

/**
 * @author liu_yeye
 * @date 2018-05-12 12:46
 */
public class TokenInfo implements Serializable {
    private String userName;
    private long validTime;
    private long expires;
    private long salt;
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getValidTime() {
        return validTime;
    }

    public void setValidTime(long validTime) {
        this.validTime = validTime;
    }

    public long getExpires() {
        return expires;
    }

    public void setExpires(long expires) {
        this.expires = expires;
    }

    public long getSalt() {
        return salt;
    }

    public void setSalt(long salt) {
        this.salt = salt;
    }

    @Override
    public String toString() {
        return "TokenInfo{" +
                "userName='" + userName + '\'' +
                ", validTime=" + validTime +
                ", expires=" + expires +
                ", salt=" + salt +
                '}';
    }
}

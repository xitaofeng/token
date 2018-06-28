package com.lang.token.model;

import java.io.Serializable;

/**
 * @author liu_yeye
 * @date 2018-05-11 9:56
 */
public class TokenUser implements Serializable {
    private String userName;
    private String passWord;
    private String signature;
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    @Override
    public String toString() {
        return "TokenUser{" +
                "userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", signature='" + signature + '\'' +
                '}';
    }
}

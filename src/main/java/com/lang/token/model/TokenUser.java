package com.lang.token.model;

/**
 * @author liu_yeye
 * @date 2018-05-11 9:56
 */
public class TokenUser {
    private String userName;
    private String passWord;

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

    @Override
    public String toString() {
        return "TokenUser{" +
                "userName='" + userName + '\'' +
                '}';
    }
}

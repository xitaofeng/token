package com.lang.token.model;

import java.io.Serializable;

/**
 * @author liu_yeye
 * @date 2018-05-11 14:02
 */
public class TokenUserInfo implements Serializable {

    private String client;
    private String clientType;
    private String name;
    private String email;
    private long salt;

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getSalt() {
        return salt;
    }

    public void setSalt(long salt) {
        this.salt = salt;
    }
}

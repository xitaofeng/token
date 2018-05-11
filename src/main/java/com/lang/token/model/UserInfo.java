package com.lang.token.model;

/**
 * @author liu_yeye
 * @date 2018-05-11 14:02
 */
public class UserInfo {
    private String client;
    private String clientType;
    private String name;
    private String emial;

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

    public String getEmial() {
        return emial;
    }

    public void setEmial(String emial) {
        this.emial = emial;
    }
}

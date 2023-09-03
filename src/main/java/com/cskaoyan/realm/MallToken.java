package com.cskaoyan.realm;

import org.apache.shiro.authc.UsernamePasswordToken;


public class MallToken extends UsernamePasswordToken {
    String type;

    public MallToken() {
    }

    public MallToken(String username, String password, String type) {
        super(username, password);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

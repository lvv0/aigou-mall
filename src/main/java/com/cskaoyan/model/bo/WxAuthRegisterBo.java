package com.cskaoyan.model.bo;

import javax.validation.constraints.Pattern;

public class WxAuthRegisterBo {

    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,16}$", message = "密码必须包含大小写字母和数字的组合，可以使用特殊字符，长度在8-16之间")
    private String password;

    private String code;
    private String wxCode;
    private String mobile;

    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_]{5,15}$", message = "用户名以字母开头，长度在6-16之间，允许字母数字下划线")
    private String username;

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setWxCode(String wxCode) {
        this.wxCode = wxCode;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public String getCode() {
        return code;
    }

    public String getWxCode() {
        return wxCode;
    }

    public String getMobile() {
        return mobile;
    }

    public String getUsername() {
        return username;
    }
}

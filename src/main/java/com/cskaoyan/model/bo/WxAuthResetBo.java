package com.cskaoyan.model.bo;

import javax.validation.constraints.Pattern;

public class WxAuthResetBo {
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,16}$", message = "密码必须包含大小写字母和数字的组合，可以使用特殊字符，长度在8-16之间")
    private String password;
    private String code;
    private String mobile;

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public String getCode() {
        return code;
    }

    public String getMobile() {
        return mobile;
    }
}

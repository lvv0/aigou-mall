package com.cskaoyan.model.bean;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
public class Admin {
    private Integer id;

    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_]{5,15}$", message = "用户名以字母开头，长度在6-16之间，允许字母数字下划线")
    private String username;

    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,16}$", message = "密码必须包含大小写字母和数字的组合，可以使用特殊字符，长度在8-16之间")
    private String password;

    private String lastLoginIp;

    private Date lastLoginTime;

    private String avatar;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date addTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private Boolean deleted;

    private Integer[] roleIds;
}
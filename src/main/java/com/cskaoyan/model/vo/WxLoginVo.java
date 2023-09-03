package com.cskaoyan.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Name : WxLoginVo.java
 * @Time : 2021/8/14 11:20
 * @Author : Ashe
 * @Software : IntelliJ IDEA
 */
@Data
public class WxLoginVo {

    private WxUserInfoBean userInfo;

    private Date tokenExpire;

    private String token;

}

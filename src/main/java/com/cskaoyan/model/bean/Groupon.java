package com.cskaoyan.model.bean;

import lombok.Data;

import java.util.Date;
@Data
public class Groupon {
    private Integer id;

    private Integer orderId;

    private Integer grouponId;

    private Integer rulesId;

    private Integer userId;

    private Integer creatorUserId;

    private Date addTime;

    private Date updateTime;

    private String shareUrl;

    private Boolean payed;

    private Boolean deleted;

}
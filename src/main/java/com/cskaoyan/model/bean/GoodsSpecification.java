package com.cskaoyan.model.bean;

import lombok.Data;

import java.util.Date;

@Data
public class GoodsSpecification {
    private Integer id;

    private Integer goodsId;

    private String specification;

    private String value;

    private String picUrl;

    private Date addTime;

    private Date updateTime;

    private Boolean deleted;

}
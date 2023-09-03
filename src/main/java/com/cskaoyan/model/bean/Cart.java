package com.cskaoyan.model.bean;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Cart {
    private Integer id;

    private Integer userId;

    private Integer goodsId;

    private String goodsSn;

    private String goodsName;

    private Integer productId;

    private BigDecimal price;

    private Integer number;

    private String[] specifications;

    private Boolean checked;

    private String picUrl;

    private Date addTime;

    private Date updateTime;

    private Boolean deleted;

}
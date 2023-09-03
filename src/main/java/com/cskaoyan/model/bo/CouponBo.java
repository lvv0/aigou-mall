package com.cskaoyan.model.bo;

import lombok.Data;

import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Name : CouponBo.java
 * @Time : 2021/8/17 13:39
 * @Author : Ashe
 * @Software : IntelliJ IDEA
 */
@Data
public class CouponBo {

    private Integer id;

    private String name;

    private String desc;

    private String tag;

    @Min(value = 0, message = "参数必须是数字")
    private String total;

    @Min(value = 0, message = "参数必须是数字")
    private String discount;

    @Min(value = 0, message = "参数必须是数字")
    private String min;

    @Min(value = 0, message = "参数必须是数字")
    private String limit;

    private Short type;

    private Short status;

    private Short goodsType;

    private String[] goodsValue;

    private String code;

    private Short timeType;

    @Min(value = 0, message = "参数必须是数字")
    private String days;

    private Date startTime;

    private Date endTime;

    private Date addTime;

    private Date updateTime;

    private Boolean deleted;

}

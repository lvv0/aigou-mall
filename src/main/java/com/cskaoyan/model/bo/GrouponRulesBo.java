package com.cskaoyan.model.bo;

import lombok.Data;

import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Name : GrouponRulesBo.java
 * @Time : 2021/8/17 12:45
 * @Author : Ashe
 * @Software : IntelliJ IDEA
 */
@Data
public class GrouponRulesBo {

    private Integer id;

    @Min(value = 0, message = "参数必须是数字")
    private String goodsId;

    private String goodsName;

    private String picUrl;

    @Min(value = 0, message = "参数必须是数字")
    private String discount;

    @Min(value = 0, message = "参数必须是数字")
    private String discountMember;

    private Date addTime;

    private Date updateTime;

    private Date expireTime;

    private Boolean deleted;

}

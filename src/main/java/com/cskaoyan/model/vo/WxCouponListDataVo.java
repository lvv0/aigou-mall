package com.cskaoyan.model.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class WxCouponListDataVo {
    private BigDecimal min;
    private String name;
    private BigDecimal discount;
    private Short days;
    private Integer id;
    private String tag;
    private String desc;

}

package com.cskaoyan.model.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class WxCouponMyListDataVo {
    private BigDecimal min;
    private String name;
    private BigDecimal discount;
    private Date startTime;
    private Integer id;
    private String tag;
    private Date endTime;
    private String desc;

}

package com.cskaoyan.model.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class WxCouponMyListVo {
    private List<WxCouponMyListDataVo> data;
    private Long count;
}

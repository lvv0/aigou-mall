package com.cskaoyan.model.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WxCartTotalVo {
    private BigDecimal checkedGoodsAmount;
    private Integer checkedGoodsCount;
    private BigDecimal goodsAmount;
    private Integer goodsCount;
}

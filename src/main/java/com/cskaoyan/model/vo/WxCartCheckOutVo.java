package com.cskaoyan.model.vo;

import com.cskaoyan.model.bean.Address;
import com.cskaoyan.model.bean.Cart;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class WxCartCheckOutVo {

    private Integer grouponRulesId = 0; //暂不写，返回0
    private BigDecimal grouponPrice = BigDecimal.ZERO; //暂不写，返回0
    private BigDecimal actualPrice; //实付款
    private BigDecimal orderTotalPrice; //订单总额
    private BigDecimal couponPrice; //优惠券优惠额
    private Integer couponId;   // 优惠券id
    private Integer availableCouponLength; //可用优惠券个数
    private BigDecimal freightPrice; //运费价格
    private BigDecimal goodsTotalPrice; //商品总额
    private Integer addressId; //地址id
    private Address checkedAddress; //地址
    private List<Cart> checkedGoodsList; //订单中商品详情列表

}

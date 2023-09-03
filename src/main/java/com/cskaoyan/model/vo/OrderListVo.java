package com.cskaoyan.model.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author huxudong
 * @version 1.0
 * @date 2021/8/12 21:34
 */
@Data
public class OrderListVo {

    private Long total;

    private List<InnerOrderVo> items;

    @Data
    public static class InnerOrderVo{
        private Integer id;

        private Integer userId;

        private String orderSn;

        private Short orderStatus;

        private String consignee;

        private String mobile;

        private String address;

        private String message;

        private String shipChannel;

        private String shipSn;

        private Date shipTime;

        private Date confirmTime;

        private BigDecimal goodsPrice;

        private BigDecimal freightPrice;

        private BigDecimal couponPrice;

        private BigDecimal integralPrice;

        private BigDecimal grouponPrice;

        private BigDecimal orderPrice;

        private BigDecimal actualPrice;

        private Short comments;

        private Date endTime;

        private Date addTime;

        private Date updateTime;

        private Boolean deleted;

    }




}

package com.cskaoyan.model.vo;

import com.cskaoyan.model.bean.OrderGoods;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author huxudong
 * @version 1.0
 * @date 2021/8/14 14:29
 */
public class WxOrderDetailVo {

    /**
     * orderInfo : {"consignee":"张松","address":"安徽省 合肥市 瑶海区 软件新城二期","addTime":"2021-08-14 10:50:49","orderSn":"20210814141589","actualPrice":111,"mobile":"18566549962","orderStatusText":"已取消","goodsPrice":111,"couponPrice":0,"id":74,"freightPrice":0,"handleOption":{"cancel":false,"delete":true,"pay":false,"comment":false,"confirm":false,"refund":false,"rebuy":false}}
     * orderGoods : [{"id":96,"orderId":74,"goodsId":1181053,"goodsName":"mm","goodsSn":"987789","productId":432,"number":1,"price":111,"specifications":["1"],"picUrl":"http://182.92.235.201:8083/wx/storage/fetch/sbdcljutvg50wnemx73d.jpg","comment":0,"addTime":"2021-08-14 10:50:49","updateTime":"2021-08-14 10:50:49","deleted":false}]
     */

    private OrderInfoBean orderInfo;
    private ExpressInfo expressInfo;
    private List<OrderGoods> orderGoods;

    public ExpressInfo getExpressInfo() {
        return expressInfo;
    }

    public void setExpressInfo(ExpressInfo expressInfo) {
        this.expressInfo = expressInfo;
    }

    public OrderInfoBean getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfoBean orderInfo) {
        this.orderInfo = orderInfo;
    }

    public List<OrderGoods> getOrderGoods() {
        return orderGoods;
    }

    public void setOrderGoods(List<OrderGoods> orderGoods) {
        this.orderGoods = orderGoods;
    }

    public static class OrderInfoBean {
        /**
         * consignee : 张松
         * address : 安徽省 合肥市 瑶海区 软件新城二期
         * addTime : 2021-08-14 10:50:49
         * orderSn : 20210814141589
         * actualPrice : 111
         * mobile : 18566549962
         * orderStatusText : 已取消
         * goodsPrice : 111
         * couponPrice : 0
         * id : 74
         * freightPrice : 0
         * handleOption : {"cancel":false,"delete":true,"pay":false,"comment":false,"confirm":false,"refund":false,"rebuy":false}
         */

        private String consignee;
        private String address;
        private Date addTime;
        private String orderSn;
        private BigDecimal actualPrice;
        private String mobile;
        private String orderStatusText;
        private BigDecimal goodsPrice;
        private BigDecimal couponPrice;
        private int id;
        private BigDecimal freightPrice;
        private WxOrderHandleOptionVo handleOption;

        public String getConsignee() {
            return consignee;
        }

        public void setConsignee(String consignee) {
            this.consignee = consignee;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Date getAddTime() {
            return addTime;
        }

        public void setAddTime(Date addTime) {
            this.addTime = addTime;
        }

        public String getOrderSn() {
            return orderSn;
        }

        public void setOrderSn(String orderSn) {
            this.orderSn = orderSn;
        }

        public BigDecimal getActualPrice() {
            return actualPrice;
        }

        public void setActualPrice(BigDecimal actualPrice) {
            this.actualPrice = actualPrice;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getOrderStatusText() {
            return orderStatusText;
        }

        public void setOrderStatusText(String orderStatusText) {
            this.orderStatusText = orderStatusText;
        }

        public BigDecimal getGoodsPrice() {
            return goodsPrice;
        }

        public void setGoodsPrice(BigDecimal goodsPrice) {
            this.goodsPrice = goodsPrice;
        }

        public BigDecimal getCouponPrice() {
            return couponPrice;
        }

        public void setCouponPrice(BigDecimal couponPrice) {
            this.couponPrice = couponPrice;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public BigDecimal getFreightPrice() {
            return freightPrice;
        }

        public void setFreightPrice(BigDecimal freightPrice) {
            this.freightPrice = freightPrice;
        }

        public WxOrderHandleOptionVo getHandleOption() {
            return handleOption;
        }

        public void setHandleOption(WxOrderHandleOptionVo handleOption) {
            this.handleOption = handleOption;
        }
    }

    public static class ExpressInfo {
        private String shipperName;
        private String logisticCode;

        public String getShipperName() {
            return shipperName;
        }

        public void setShipperName(String shipperName) {
            this.shipperName = shipperName;
        }

        public String getLogisticCode() {
            return logisticCode;
        }

        public void setLogisticCode(String logisticCode) {
            this.logisticCode = logisticCode;
        }
    }
}

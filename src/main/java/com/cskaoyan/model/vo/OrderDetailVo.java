package com.cskaoyan.model.vo;

import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author huxudong
 * @version 1.0
 * @date 2021/8/12 22:44
 */
public class OrderDetailVo {

    /**
     * orderGoods : [{"id":69,"orderId":53,"goodsId":1116011,"goodsName":"蔓越莓曲奇 200克","goodsSn":"1116011","productId":167,"number":1,"price":36,"specifications":["标准"],"picUrl":"http://yanxuan.nosdn.127.net/767b370d07f3973500db54900bcbd2a7.png","comment":0,"addTime":"2021-07-14 22:51:34","updateTime":"2021-07-14 22:51:34","deleted":false}]
     * user : {"nickname":"测试用户","avatar":"https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif?imageView2/1/w/80/h/80"}
     * order : {"id":53,"userId":1,"orderSn":"20210714238268","orderStatus":201,"consignee":"松哥","mobile":"15976753826","address":"湖北省 武汉市 洪山区 花山街道软件新城2期","message":"","goodsPrice":36,"freightPrice":8,"couponPrice":0,"integralPrice":0,"grouponPrice":0,"orderPrice":44,"actualPrice":44,"comments":0,"endTime":"2021-07-14 23:40:36","addTime":"2021-07-14 22:51:34","updateTime":"2021-07-14 23:40:36","deleted":false}
     */

    private UserBean user;
    private OrderListVo.InnerOrderVo order;
    private List<OrderGoodsBean> orderGoods;

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public OrderListVo.InnerOrderVo getOrder() {
        return order;
    }

    public void setOrder(OrderListVo.InnerOrderVo order) {
        this.order = order;
    }

    public List<OrderGoodsBean> getOrderGoods() {
        return orderGoods;
    }

    public void setOrderGoods(List<OrderGoodsBean> orderGoods) {
        this.orderGoods = orderGoods;
    }

    public static class UserBean {
        /**
         * nickname : 测试用户
         * avatar : https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif?imageView2/1/w/80/h/80
         */

        private String nickname;
        private String avatar;

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }

    public static class OrderGoodsBean {
        /**
         * id : 69
         * orderId : 53
         * goodsId : 1116011
         * goodsName : 蔓越莓曲奇 200克
         * goodsSn : 1116011
         * productId : 167
         * number : 1
         * price : 36
         * specifications : ["标准"]
         * picUrl : http://yanxuan.nosdn.127.net/767b370d07f3973500db54900bcbd2a7.png
         * comment : 0
         * addTime : 2021-07-14 22:51:34
         * updateTime : 2021-07-14 22:51:34
         * deleted : false
         */

        private int id;
        private int orderId;
        private int goodsId;
        private String goodsName;
        private String goodsSn;
        private int productId;
        private short number;
        private BigDecimal price;
        private String picUrl;
        private int comment;
        private Date addTime;
        private Date updateTime;
        private boolean deleted;
        private String specifications;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public int getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(int goodsId) {
            this.goodsId = goodsId;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getGoodsSn() {
            return goodsSn;
        }

        public void setGoodsSn(String goodsSn) {
            this.goodsSn = goodsSn;
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public short getNumber() {
            return number;
        }

        public void setNumber(short number) {
            this.number = number;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public int getComment() {
            return comment;
        }

        public void setComment(int comment) {
            this.comment = comment;
        }

        public Date getAddTime() {
            return addTime;
        }

        public void setAddTime(Date addTime) {
            this.addTime = addTime;
        }

        public Date getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Date updateTime) {
            this.updateTime = updateTime;
        }

        public boolean isDeleted() {
            return deleted;
        }

        public void setDeleted(boolean deleted) {
            this.deleted = deleted;
        }

        public String getSpecifications() {
            return specifications;
        }

        public void setSpecifications(String specifications) {
            this.specifications = specifications;
        }
    }
}

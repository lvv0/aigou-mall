package com.cskaoyan.model.vo;

import com.cskaoyan.model.bean.Log;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author huxudong
 * @version 1.0
 * @date 2021/8/14 10:45
 */
public class WxOrderListVo {

    /**
     * data : [{"orderStatusText":"未付款","isGroupin":false,"orderSn":"20210814320093","actualPrice":111,"goodsList":[{"number":1,"picUrl":"http://182.92.235.201:8083/wx/storage/fetch/sbdcljutvg50wnemx73d.jpg","id":93,"goodsName":"mm"}],"id":71,"handleOption":{"cancel":true,"delete":false,"pay":true,"comment":false,"confirm":false,"refund":false,"rebuy":false}},{"orderStatusText":"未付款","isGroupin":false,"orderSn":"20210814823720","actualPrice":111,"goodsList":[{"number":1,"picUrl":"http://182.92.235.201:8083/wx/storage/fetch/sbdcljutvg50wnemx73d.jpg","id":92,"goodsName":"mm"}],"id":70,"handleOption":{"cancel":true,"delete":false,"pay":true,"comment":false,"confirm":false,"refund":false,"rebuy":false}},{"orderStatusText":"未付款","isGroupin":false,"orderSn":"20210814824862","actualPrice":9999,"goodsList":[{"number":1,"picUrl":"http://182.92.235.201:8083/wx/storage/fetch/jcvi6mo1om8yv4yg65ze.jpg","id":91,"goodsName":"小可爱"}],"id":69,"handleOption":{"cancel":true,"delete":false,"pay":true,"comment":false,"confirm":false,"refund":false,"rebuy":false}}]
     * count : 3
     * totalPages : 1
     */

    private Long count;
    private Long totalPages;
    private List<DataBean> data;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Long totalPages) {
        this.totalPages = totalPages;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * orderStatusText : 未付款
         * isGroupin : false
         * orderSn : 20210814320093
         * actualPrice : 111
         * goodsList : [{"number":1,"picUrl":"http://182.92.235.201:8083/wx/storage/fetch/sbdcljutvg50wnemx73d.jpg","id":93,"goodsName":"mm"}]
         * id : 71
         * handleOption : {"cancel":true,"delete":false,"pay":true,"comment":false,"confirm":false,"refund":false,"rebuy":false}
         */

        private String orderStatusText;
        private boolean isGroupin;
        private String orderSn;
        private BigDecimal actualPrice;
        private int id;
        private WxOrderHandleOptionVo handleOption;
        private List<GoodsListBean> goodsList;

        public String getOrderStatusText() {
            return orderStatusText;
        }

        public void setOrderStatusText(String orderStatusText) {
            this.orderStatusText = orderStatusText;
        }

        public boolean isIsGroupin() {
            return isGroupin;
        }

        public void setIsGroupin(boolean isGroupin) {
            this.isGroupin = isGroupin;
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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public WxOrderHandleOptionVo getHandleOption() {
            return handleOption;
        }

        public void setHandleOption(WxOrderHandleOptionVo handleOption) {
            this.handleOption = handleOption;
        }

        public List<GoodsListBean> getGoodsList() {
            return goodsList;
        }

        public void setGoodsList(List<GoodsListBean> goodsList) {
            this.goodsList = goodsList;
        }

        public static class GoodsListBean {
            /**
             * number : 1
             * picUrl : http://182.92.235.201:8083/wx/storage/fetch/sbdcljutvg50wnemx73d.jpg
             * id : 93
             * goodsName : mm
             */

            private int number;
            private String picUrl;
            private int id;
            private String goodsName;

            public int getNumber() {
                return number;
            }

            public void setNumber(int number) {
                this.number = number;
            }

            public String getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }
        }
    }
}

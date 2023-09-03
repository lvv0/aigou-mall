package com.cskaoyan.model.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Name : WxGoodsRelatedVo.java
 * @Time : 2021/8/15 04:52
 * @Author : Ashe
 * @Software : IntelliJ IDEA
 */
@Data
public class WxGoodsRelatedVo {


    private List<GoodsListBean> goodsList;

    @Data
    public static class GoodsListBean {

        private int id;
        private String name;
        private String brief;
        private String picUrl;
        private boolean isNew;
        private boolean isHot;
        private BigDecimal counterPrice;
        private BigDecimal retailPrice;
    }
}

package com.cskaoyan.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Name : WxGoodsListVo.java
 * @Time : 2021/8/14 23:23
 * @Author : Ashe
 * @Software : IntelliJ IDEA
 */
@Data
public class WxGoodsListVo {

    private Long count;
    private List<GoodsListBean> goodsList;
    private List<FilterCategoryListBean> filterCategoryList;

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

    @Data
    public static class FilterCategoryListBean {

        private int id;
        private String name;
        private String keywords;
        private String desc;
        private int pid;
        private String iconUrl;
        private String picUrl;
        private String level;
        private int sortOrder;
        private Date addTime;
        private Date updateTime;
        private boolean deleted;
    }

}

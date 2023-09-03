package com.cskaoyan.model.vo;

import com.cskaoyan.model.bean.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Name : WxGoodsDetailVo.java
 * @Time : 2021/8/15 01:38
 * @Author : Ashe
 * @Software : IntelliJ IDEA
 */
@Data
public class WxGoodsDetailVo {

    private Long userHasCollect;
    private String shareImage = "";
    private CommentBean comment;
    private Brand brand;
    private Goods info;
    private List<SpecificationListBean> specificationList;
    private List<?> groupon = new ArrayList<>();
    private List<Issue> issue;
    private List<GoodsAttribute> attribute;
    private List<GoodsProduct> productList;

    @Data
    public static class CommentBean {

        private Long count;
        private List<DataBean> data;
    }

    @Data
    public static class DataBean {

        private Date addTime;
        private String nickname;
        private int id;
        private String avatar;
        private String content;
        private String[] picList;

    }

    @Data
    public static class SpecificationListBean {

        private String name;
        private List<GoodsSpecification> valueList;
    }

}

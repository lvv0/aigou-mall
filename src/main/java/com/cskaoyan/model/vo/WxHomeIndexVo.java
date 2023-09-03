package com.cskaoyan.model.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Name : WxHomeIndexVo.java
 * @Time : 2021/8/14 16:53
 * @Author : Ashe
 * @Software : IntelliJ IDEA
 */
@Data
public class WxHomeIndexVo {

    private List<NewGoodsListBean> newGoodsList;
    private List<CouponListBean> couponList;
    private List<ChannelBean> channel;
    private List<GrouponListBean> grouponList;
    private List<BannerBean> banner;
    private List<BrandListBean> brandList;
    private List<HotGoodsListBean> hotGoodsList;
    private List<TopicListBean> topicList;
    private List<FloorGoodsListBean> floorGoodsList;

    @Data
    public static class NewGoodsListBean {

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
    public static class CouponListBean {

        private int id;
        private String name;
        private String desc;
        private String tag;
        private BigDecimal discount;
        private BigDecimal min;
        private int days;
        private Date startTime;
        private Date endTime;
    }

    @Data
    public static class ChannelBean {

        private int id;
        private String name;
        private String iconUrl;
    }

    @Data
    public static class GrouponListBean {

        private BigDecimal groupon_price;
        private GoodsBean goods;
        private int groupon_member;
    }

    @Data
    public static class GoodsBean {

        private int id;
        private String name;
        private String brief;
        private String picUrl;
        private BigDecimal counterPrice;
        private BigDecimal retailPrice;
    }


    @Data
    public static class BannerBean {

        private int id;
        private String name;
        private String link;
        private String url;
        private int position;
        private String content;
        private boolean enabled;
        private Date addTime;
        private Date updateTime;
        private boolean deleted;

    }

    @Data
    public static class BrandListBean {

        private int id;
        private String name;
        private String desc;
        private String picUrl;
        private BigDecimal floorPrice;

    }

    @Data
    public static class HotGoodsListBean {

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
    public static class TopicListBean {

        private int id;
        private String title;
        private String subtitle;
        private String price;
        private String readCount;
        private String picUrl;
    }

    @Data
    public static class FloorGoodsListBean {

        private String name;
        private int id;
        private List<GoodsListBean> goodsList;
    }

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

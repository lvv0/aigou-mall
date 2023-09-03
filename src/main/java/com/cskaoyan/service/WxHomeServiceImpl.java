package com.cskaoyan.service;

import com.cskaoyan.mapper.*;
import com.cskaoyan.model.bean.*;
import com.cskaoyan.model.bean.System;
import com.cskaoyan.model.vo.WxHomeIndexVo;
import com.cskaoyan.utils.UserInfoUtils;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Name : WxHomeServiceImpl.java
 * @Time : 2021/8/14 16:02
 * @Author : Ashe
 * @Software : IntelliJ IDEA
 */
@Service
public class WxHomeServiceImpl implements WxHomeService {

    @Autowired
    UserInfoUtils userInfoUtils;

    @Autowired
    GoodsMapper goodsMapper;

    @Autowired
    CouponMapper couponMapper;

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    GrouponRulesMapper grouponRulesMapper;

    @Autowired
    AdMapper adMapper;

    @Autowired
    BrandMapper brandMapper;

    @Autowired
    TopicMapper topicMapper;

    @Autowired
    SystemMapper systemMapper;

    @Autowired
    FootPrintMapper footPrintMapper;

    @Override
    public WxHomeIndexVo index() {

        SystemExample systemExample = new SystemExample();

        SystemExample.Criteria systemCriteria = systemExample.createCriteria();

        systemCriteria.andDeletedEqualTo(false);

        List<System> systems = systemMapper.selectByExample(systemExample);

        Integer indexTopic = null;

        Integer indexNew = null;

        Integer indexHot = null;

        Integer indexBrand = null;

        Integer catlogList = null;

        Integer catlogGoods = null;

        for (System system : systems) {

            if ("cskaoyan_mall_wx_index_topic".equals(system.getKeyName())) {
                indexTopic = Integer.parseInt(system.getKeyValue());
            }
            if ("cskaoyan_mall_wx_index_new".equals(system.getKeyName())) {
                indexNew = Integer.parseInt(system.getKeyValue());
            }
            if ("cskaoyan_mall_wx_index_hot".equals(system.getKeyName())) {
                indexHot = Integer.parseInt(system.getKeyValue());
            }
            if ("cskaoyan_mall_wx_index_brand".equals(system.getKeyName())) {
                indexBrand = Integer.parseInt(system.getKeyValue());
            }
            if ("cskaoyan_mall_wx_catlog_list".equals(system.getKeyName())) {
                catlogList = Integer.parseInt(system.getKeyValue());
            }
            if ("cskaoyan_mall_wx_catlog_goods".equals(system.getKeyName())) {
                catlogGoods = Integer.parseInt(system.getKeyValue());
            }
        }

        WxHomeIndexVo wxHomeIndexVo = new WxHomeIndexVo();

        GoodsExample newGoodsExample = new GoodsExample();

        GoodsExample.Criteria newGoodsCriteria = newGoodsExample.createCriteria();

        newGoodsCriteria.andDeletedEqualTo(false).andIsNewEqualTo(true);

        List<Goods> newGoods = goodsMapper.selectByExample(newGoodsExample);

        if (newGoods.size() > indexNew) {
            newGoods = newGoods.subList(0, indexNew);
        }

        List<WxHomeIndexVo.NewGoodsListBean> newGoodsList = new ArrayList<>();

        for (Goods good : newGoods) {

            WxHomeIndexVo.NewGoodsListBean bean = new WxHomeIndexVo.NewGoodsListBean();

            bean.setId(good.getId());

            bean.setName(good.getName());

            bean.setBrief(good.getBrief());

            bean.setPicUrl(good.getPicUrl());

            bean.setNew(good.getIsNew());

            bean.setHot(good.getIsHot());

            bean.setCounterPrice(good.getCounterPrice());

            bean.setRetailPrice(good.getRetailPrice());

            newGoodsList.add(bean);
        }

        wxHomeIndexVo.setNewGoodsList(newGoodsList);

        CouponExample couponExample = new CouponExample();

        CouponExample.Criteria couponCriteria = couponExample.createCriteria();

        couponExample.setOrderByClause("discount asc");

        couponCriteria.andDeletedEqualTo(false).andTypeEqualTo((short) 0);

        List<Coupon> coupons = couponMapper.selectByExample(couponExample);

        List<WxHomeIndexVo.CouponListBean> couponList = new ArrayList<>();

        for (Coupon coupon : coupons) {

            WxHomeIndexVo.CouponListBean bean = new WxHomeIndexVo.CouponListBean();

            bean.setId(coupon.getId());

            bean.setName(coupon.getName());

            bean.setDesc(coupon.getDesc());

            bean.setTag(coupon.getTag());

            bean.setDiscount(coupon.getDiscount());

            bean.setMin(coupon.getMin());

            bean.setDays(coupon.getDays());

            bean.setStartTime(coupon.getStartTime());

            bean.setEndTime(coupon.getEndTime());

            couponList.add(bean);
        }

        wxHomeIndexVo.setCouponList(couponList);

        CategoryExample categoryExample = new CategoryExample();

        CategoryExample.Criteria categorycriteria = categoryExample.createCriteria();

        categorycriteria.andDeletedEqualTo(false).andPidEqualTo(0);

        List<Category> categories = categoryMapper.selectByExample(categoryExample);

        if (categories.size() > catlogList) {
            categories = categories.subList(0, catlogList);
        }

        List<WxHomeIndexVo.ChannelBean> channelList = new ArrayList<>();

        for (Category category : categories) {

            WxHomeIndexVo.ChannelBean bean = new WxHomeIndexVo.ChannelBean();

            bean.setId(category.getId());

            bean.setName(category.getName());

            bean.setIconUrl(category.getIconUrl());

            channelList.add(bean);
        }

        wxHomeIndexVo.setChannel(channelList);

        GrouponRulesExample grouponRulesExample = new GrouponRulesExample();

        GrouponRulesExample.Criteria grouponRulesCriteria = grouponRulesExample.createCriteria();

        grouponRulesCriteria.andDeletedEqualTo(false);

        List<GrouponRules> grouponRules = grouponRulesMapper.selectByExample(grouponRulesExample);

        List<WxHomeIndexVo.GrouponListBean> grouponList = new ArrayList<>();

        for (GrouponRules grouponRule : grouponRules) {

            WxHomeIndexVo.GrouponListBean bean = new WxHomeIndexVo.GrouponListBean();

            bean.setGroupon_price(grouponRule.getDiscount());

            bean.setGroupon_member(grouponRule.getDiscountMember());

            Goods goodsDetail = goodsMapper.selectByPrimaryKey(grouponRule.getGoodsId());

            WxHomeIndexVo.GoodsBean goodsBean = new WxHomeIndexVo.GoodsBean();

            goodsBean.setId(goodsDetail.getId());

            goodsBean.setName(goodsDetail.getName());

            goodsBean.setBrief(goodsDetail.getBrief());

            goodsBean.setPicUrl(goodsDetail.getPicUrl());

            goodsBean.setCounterPrice(goodsDetail.getCounterPrice());

            goodsBean.setRetailPrice(goodsDetail.getRetailPrice());

            bean.setGoods(goodsBean);

            grouponList.add(bean);
        }

        wxHomeIndexVo.setGrouponList(grouponList);

        AdExample adExample = new AdExample();

        AdExample.Criteria adCriteria = adExample.createCriteria();

        adCriteria.andDeletedEqualTo(false).andEnabledEqualTo(true);

        List<Ad> ads = adMapper.selectByExample(adExample);

        List<WxHomeIndexVo.BannerBean> bannerList = new ArrayList<>();

        for (Ad ad : ads) {

            WxHomeIndexVo.BannerBean bean = new WxHomeIndexVo.BannerBean();

            bean.setId(ad.getId());

            bean.setName(ad.getName());

            bean.setLink(ad.getLink());

            bean.setUrl(ad.getUrl());

            bean.setPosition(ad.getPosition());

            bean.setContent(ad.getContent());

            bean.setEnabled(ad.getEnabled());

            bean.setAddTime(ad.getAddTime());

            bean.setUpdateTime(ad.getUpdateTime());

            bean.setDeleted(ad.getDeleted());

            bannerList.add(bean);
        }

        wxHomeIndexVo.setBanner(bannerList);

        BrandExample brandExample = new BrandExample();

        BrandExample.Criteria brandcriteria = brandExample.createCriteria();

        brandcriteria.andDeletedEqualTo(false);

        List<Brand> brands = brandMapper.selectByExample(brandExample);

        if (brands.size() > indexBrand) {
            brands = brands.subList(0, indexBrand);
        }

        List<WxHomeIndexVo.BrandListBean> brandList = new ArrayList<>();

        for (Brand brand : brands) {

            WxHomeIndexVo.BrandListBean bean = new WxHomeIndexVo.BrandListBean();

            bean.setId(brand.getId());

            bean.setName(brand.getName());

            bean.setDesc(brand.getDesc());

            bean.setPicUrl(brand.getPicUrl());

            bean.setFloorPrice(brand.getFloorPrice());

            brandList.add(bean);
        }

        wxHomeIndexVo.setBrandList(brandList);

        if (userInfoUtils.getUsername() == null) {

            GoodsExample hotGoodsExample = new GoodsExample();

            GoodsExample.Criteria hotGoodscriteria = hotGoodsExample.createCriteria();

            hotGoodscriteria.andDeletedEqualTo(false).andIsHotEqualTo(true);

            List<Goods> hotGoods = goodsMapper.selectByExample(hotGoodsExample);

            if (hotGoods.size() > indexHot) {
                hotGoods = hotGoods.subList(0, indexHot);
            }

            List<WxHomeIndexVo.HotGoodsListBean> hotGoodsList = new ArrayList<>();

            for (Goods good : hotGoods) {

                WxHomeIndexVo.HotGoodsListBean bean = new WxHomeIndexVo.HotGoodsListBean();

                bean.setId(good.getId());

                bean.setName(good.getName());

                bean.setBrief(good.getBrief());

                bean.setPicUrl(good.getPicUrl());

                bean.setNew(good.getIsNew());

                bean.setHot(good.getIsHot());

                bean.setCounterPrice(good.getCounterPrice());

                bean.setRetailPrice(good.getRetailPrice());

                hotGoodsList.add(bean);
            }

            wxHomeIndexVo.setHotGoodsList(hotGoodsList);

        } else {

            FootPrintExample footPrintExample = new FootPrintExample();

            FootPrintExample.Criteria criteria = footPrintExample.createCriteria();

            footPrintExample.setOrderByClause("add_time desc");

            criteria.andDeletedEqualTo(false).andUserIdEqualTo(userInfoUtils.getUserId());

            List<FootPrint> footPrints = footPrintMapper.selectByExample(footPrintExample);

            List<Integer> list = new ArrayList<>();

            for (FootPrint footPrint : footPrints) {
                Integer goodsId = footPrint.getGoodsId();
                if (!list.contains(goodsId)) {
                    list.add(goodsId);
                }
            }

            List<WxHomeIndexVo.HotGoodsListBean> hotGoodsList = new ArrayList<>();

            int count = 0;

            List<Integer> goodList = new ArrayList<>();

            for (Integer index : list) {

                Goods goods = goodsMapper.selectByPrimaryKey(index);

                GoodsExample goodsExample = new GoodsExample();

                GoodsExample.Criteria goodsCriteria = goodsExample.createCriteria();

                goodsCriteria.andDeletedEqualTo(false).andCategoryIdEqualTo(goods.getCategoryId());

                List<Goods> goodsList = goodsMapper.selectByExample(goodsExample);

                for (Goods good : goodsList) {

                    if (!list.contains(good.getId()) && !goodList.contains(good.getId())){

                        goodList.add(good.getId());

                        WxHomeIndexVo.HotGoodsListBean bean = new WxHomeIndexVo.HotGoodsListBean();

                        bean.setId(good.getId());

                        bean.setName(good.getName());

                        bean.setBrief(good.getBrief());

                        bean.setPicUrl(good.getPicUrl());

                        bean.setNew(good.getIsNew());

                        bean.setHot(good.getIsHot());

                        bean.setCounterPrice(good.getCounterPrice());

                        bean.setRetailPrice(good.getRetailPrice());

                        hotGoodsList.add(bean);

                        ++count;

                        break;
                    }
                }
                if (count == indexHot){
                    break;
                }
            }
            if (count == indexHot) {
                wxHomeIndexVo.setHotGoodsList(hotGoodsList);
            } else {
                GoodsExample hotGoodsExample = new GoodsExample();

                GoodsExample.Criteria hotGoodscriteria = hotGoodsExample.createCriteria();

                hotGoodscriteria.andDeletedEqualTo(false).andIsHotEqualTo(true);

                List<Goods> hotGoods = goodsMapper.selectByExample(hotGoodsExample);

                if (hotGoods.size() > indexHot - count) {
                    hotGoods = hotGoods.subList(0, indexHot - count);
                }

                for (Goods good : hotGoods) {

                    WxHomeIndexVo.HotGoodsListBean bean = new WxHomeIndexVo.HotGoodsListBean();

                    bean.setId(good.getId());

                    bean.setName(good.getName());

                    bean.setBrief(good.getBrief());

                    bean.setPicUrl(good.getPicUrl());

                    bean.setNew(good.getIsNew());

                    bean.setHot(good.getIsHot());

                    bean.setCounterPrice(good.getCounterPrice());

                    bean.setRetailPrice(good.getRetailPrice());

                    hotGoodsList.add(bean);
                }
                wxHomeIndexVo.setHotGoodsList(hotGoodsList);
            }
        }

        TopicExample topicExample = new TopicExample();

        TopicExample.Criteria topicCriteria = topicExample.createCriteria();

        topicCriteria.andDeletedEqualTo(false);

        List<Topic> topics = topicMapper.selectByExample(topicExample);

        if (topics.size() > indexTopic) {
            topics = topics.subList(0, indexTopic);
        }

        List<WxHomeIndexVo.TopicListBean> topicList = new ArrayList<>();

        for (Topic topic : topics) {

            WxHomeIndexVo.TopicListBean bean = new WxHomeIndexVo.TopicListBean();

            bean.setId(topic.getId());

            bean.setTitle(topic.getTitle());

            bean.setSubtitle(topic.getSubtitle());

            bean.setPrice(topic.getPrice());

            bean.setReadCount(topic.getReadCount());

            bean.setPicUrl(topic.getPicUrl());

            topicList.add(bean);
        }

        wxHomeIndexVo.setTopicList(topicList);

        List<WxHomeIndexVo.FloorGoodsListBean> floorGoodsList = new ArrayList<>();

        for (WxHomeIndexVo.ChannelBean channelBean : channelList) {

            WxHomeIndexVo.FloorGoodsListBean bean = new WxHomeIndexVo.FloorGoodsListBean();

            bean.setId(channelBean.getId());

            bean.setName(channelBean.getName());

            List<WxHomeIndexVo.GoodsListBean> goodsList = new ArrayList<>();

            List<Integer> list = categoryMapper.selectL2ByL1Id(channelBean.getId());

            Integer index = 0;

            for (Integer integer : list) {

                GoodsExample goodsExample = new GoodsExample();

                GoodsExample.Criteria criteria = goodsExample.createCriteria();

                criteria.andDeletedEqualTo(false).andCategoryIdEqualTo(integer);

                List<Goods> goods = goodsMapper.selectByExample(goodsExample);

                if (goods.size() != 0) {

                    for (Goods good : goods) {

                        WxHomeIndexVo.GoodsListBean listBean = new WxHomeIndexVo.GoodsListBean();

                        listBean.setId(good.getId());

                        listBean.setName(good.getName());

                        listBean.setBrief(good.getBrief());

                        listBean.setPicUrl(good.getPicUrl());

                        listBean.setNew(good.getIsNew());

                        listBean.setHot(good.getIsHot());

                        listBean.setCounterPrice(good.getCounterPrice());

                        listBean.setRetailPrice(good.getRetailPrice());

                        goodsList.add(listBean);

                        if (++index == catlogGoods) {
                            break;
                        }
                    }
                    if (index == catlogGoods) {
                        break;
                    }
                }
            }
            bean.setGoodsList(goodsList);

            floorGoodsList.add(bean);
        }
        wxHomeIndexVo.setFloorGoodsList(floorGoodsList);

        return wxHomeIndexVo;
    }

}

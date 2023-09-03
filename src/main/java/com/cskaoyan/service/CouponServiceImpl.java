package com.cskaoyan.service;

import com.cskaoyan.mapper.CouponMapper;
import com.cskaoyan.mapper.CouponUserMapper;
import com.cskaoyan.model.bean.Coupon;
import com.cskaoyan.model.bean.CouponExample;
import com.cskaoyan.model.bean.CouponUser;
import com.cskaoyan.model.bean.CouponUserExample;
import com.cskaoyan.model.bo.BaseParamBo;
import com.cskaoyan.model.bo.CouponBo;
import com.cskaoyan.model.vo.BaseRespData;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Name : CouponServiceImpl.java
 * @Time : 2021/8/12 19:24
 * @Author : Ashe
 * @Software : IntelliJ IDEA
 */
@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    CouponMapper couponMapper;

    @Autowired
    CouponUserMapper couponUserMapper;

    @Override
    public BaseRespData query(String name, Short type, Short status, BaseParamBo param) {

        PageHelper.startPage(param.getPage(), param.getLimit());

        CouponExample example = new CouponExample();

        example.setOrderByClause(param.getSort() + " " + param.getOrder());

        CouponExample.Criteria criteria = example.createCriteria();

        criteria.andDeletedEqualTo(false);

        if (name != null && !"".equals(name)) {
            criteria.andNameLike("%" + name + "%");
        }

        if (type != null) {
            criteria.andTypeEqualTo(type);
        }

        if (status != null) {
            criteria.andStatusEqualTo(status);
        }

        List<Coupon> items = couponMapper.selectByExample(example);

        PageInfo<Coupon> pageInfo = new PageInfo<>(items);

        long total = pageInfo.getTotal();

        return BaseRespData.create(items, total);

    }

    @Override
    public Coupon detail(Integer id) {

        Coupon item = couponMapper.selectByPrimaryKey(id);

        return item;

    }

    @Override
    public BaseRespData listuser(Integer couponId, Integer userId, Short status, BaseParamBo param) {

        PageHelper.startPage(param.getPage(), param.getLimit());

        CouponUserExample example = new CouponUserExample();

        example.setOrderByClause(param.getSort() + " " + param.getOrder());

        CouponUserExample.Criteria criteria = example.createCriteria();

        criteria.andCouponIdEqualTo(couponId);

        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }

        if (status != null) {
            criteria.andStatusEqualTo(status);
        }

        List<CouponUser> items = couponUserMapper.selectByExample(example);

        PageInfo<CouponUser> pageInfo = new PageInfo<>(items);

        long total = pageInfo.getTotal();

        return BaseRespData.create(items, total);

    }

    @Override
    public void delete(Coupon coupon) {

        couponMapper.updateByPrimaryKeySelective(coupon);

    }

    @Override
    public Coupon update(CouponBo couponBo) {

        Coupon coupon = new Coupon();

        coupon.setId(couponBo.getId());

        coupon.setName(couponBo.getName());

        coupon.setDesc(couponBo.getDesc());

        coupon.setTag(couponBo.getTag());

        coupon.setTotal(Integer.parseInt(couponBo.getTotal()));

        coupon.setDiscount(new BigDecimal(couponBo.getDiscount()));

        coupon.setMin(new BigDecimal(couponBo.getMin()));

        coupon.setLimit(Short.parseShort(couponBo.getLimit()));

        coupon.setType(couponBo.getType());

        coupon.setStatus(couponBo.getStatus());

        coupon.setGoodsType(couponBo.getGoodsType());

        coupon.setGoodsValue(couponBo.getGoodsValue());

        coupon.setCode(couponBo.getCode());

        coupon.setTimeType(couponBo.getTimeType());

        coupon.setDays(Short.parseShort(couponBo.getDays()));

        coupon.setStartTime(couponBo.getStartTime());

        coupon.setEndTime(couponBo.getEndTime());

        coupon.setAddTime(couponBo.getAddTime());

        coupon.setUpdateTime(couponBo.getUpdateTime());

        coupon.setDeleted(couponBo.getDeleted());

        couponMapper.updateByPrimaryKeySelective(coupon);

        Coupon couponVo = couponMapper.selectByPrimaryKey(coupon.getId());

        return couponVo;

    }

    @Override
    public Coupon create(CouponBo couponBo) {

        Coupon coupon = new Coupon();

        coupon.setId(couponBo.getId());

        coupon.setName(couponBo.getName());

        coupon.setDesc(couponBo.getDesc());

        coupon.setTag(couponBo.getTag());

        coupon.setTotal(Integer.parseInt(couponBo.getTotal()));

        coupon.setDiscount(new BigDecimal(couponBo.getDiscount()));

        coupon.setMin(new BigDecimal(couponBo.getMin()));

        coupon.setLimit(Short.parseShort(couponBo.getLimit()));

        coupon.setType(couponBo.getType());

        coupon.setStatus(couponBo.getStatus());

        coupon.setGoodsType(couponBo.getGoodsType());

        coupon.setGoodsValue(couponBo.getGoodsValue());

        coupon.setCode(couponBo.getCode());

        coupon.setTimeType(couponBo.getTimeType());

        coupon.setDays(Short.parseShort(couponBo.getDays()));

        if(coupon.getTimeType() == 1){
            if (couponBo.getStartTime() != null) {
                coupon.setStartTime(couponBo.getStartTime());
            }
            if (couponBo.getEndTime() != null) {
                coupon.setEndTime(couponBo.getEndTime());
            }
        }

        coupon.setAddTime(couponBo.getAddTime());

        coupon.setUpdateTime(couponBo.getUpdateTime());

        coupon.setDeleted(couponBo.getDeleted());

        couponMapper.insertSelective(coupon);

        return coupon;

    }


}

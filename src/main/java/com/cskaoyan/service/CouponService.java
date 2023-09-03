package com.cskaoyan.service;

import com.cskaoyan.model.bean.Coupon;
import com.cskaoyan.model.bo.BaseParamBo;
import com.cskaoyan.model.bo.CouponBo;
import com.cskaoyan.model.vo.BaseRespData;

/**
 * @Name : CouponService.java
 * @Time : 2021/8/12 19:24
 * @Author : Ashe
 * @Software : IntelliJ IDEA
 */
public interface CouponService {

    BaseRespData query(String name, Short type, Short status, BaseParamBo param);

    Coupon detail(Integer id);

    BaseRespData listuser(Integer couponId, Integer userId, Short status, BaseParamBo param);

    void delete(Coupon coupon);

    Coupon update(CouponBo couponBo);

    Coupon create(CouponBo couponBo);
}

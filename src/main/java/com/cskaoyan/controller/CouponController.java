package com.cskaoyan.controller;

import com.cskaoyan.model.bean.Coupon;
import com.cskaoyan.model.bo.BaseParamBo;
import com.cskaoyan.model.bo.CouponBo;
import com.cskaoyan.model.vo.BaseRespData;
import com.cskaoyan.model.vo.BaseRespVo;
import com.cskaoyan.service.CouponService;
import com.cskaoyan.utils.ValidationUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Name : CouponController.java
 * @Time : 2021/8/12 19:18
 * @Author : Ashe
 * @Software : IntelliJ IDEA
 */

@RestController
@RequestMapping("admin/coupon")
public class CouponController {

    @Autowired
    CouponService couponService;

    @RequiresPermissions(value = {"*","admin:coupon:list"},logical = Logical.OR)
    @RequestMapping("list")
    public BaseRespVo list(String name, Short type, Short status, BaseParamBo param) {

        BaseRespData baseRespData = couponService.query(name, type, status, param);

        return BaseRespVo.ok(baseRespData);

    }

    @RequiresPermissions(value = {"*","admin:coupon:read"},logical = Logical.OR)
    @RequestMapping("read")
    public BaseRespVo read(Integer id) {

        Coupon item = couponService.detail(id);

        return BaseRespVo.ok(item);

    }

    @RequiresPermissions(value = {"*","admin:coupon:listuser"},logical = Logical.OR)
    @RequestMapping("listuser")
    public BaseRespVo listuser(Integer couponId, String userId, Short status, BaseParamBo param) {

        Integer parseInt = null;

        if (userId == null || userId.equals("")){

            BaseRespData baseRespData = couponService.listuser(couponId, parseInt, status, param);

            return BaseRespVo.ok(baseRespData);
        }

        try {
            parseInt = Integer.parseInt(userId);
        } catch (NumberFormatException e) {
            return BaseRespVo.fail("参数值不对!");
        }

        BaseRespData baseRespData = couponService.listuser(couponId, parseInt, status, param);

        return BaseRespVo.ok(baseRespData);

    }

    @RequiresPermissions(value = {"*","admin:coupon:delete"},logical = Logical.OR)
    @RequestMapping("delete")
    public BaseRespVo delete(@RequestBody Coupon coupon) {

        coupon.setDeleted(true);

        couponService.delete(coupon);

        return BaseRespVo.ok();

    }

    @RequiresPermissions(value = {"*","admin:coupon:update"},logical = Logical.OR)
    @RequestMapping("update")
    public BaseRespVo update(@Validated @RequestBody CouponBo couponBo, BindingResult bindingResult) {

        BaseRespVo baseRespVo = ValidationUtils.dealWithFiledError(bindingResult);

        if (baseRespVo.getErrno() == 640){
            return baseRespVo;
        }

        Coupon couponVo = couponService.update(couponBo);

        return BaseRespVo.ok(couponVo);

    }

    @RequiresPermissions(value = {"*","admin:coupon:create"},logical = Logical.OR)
    @RequestMapping("create")
    public BaseRespVo create(@Validated @RequestBody CouponBo couponBo, BindingResult bindingResult){

        BaseRespVo baseRespVo = ValidationUtils.dealWithFiledError(bindingResult);

        if (baseRespVo.getErrno() == 640){
            return baseRespVo;
        }

        Coupon couponVo = couponService.create(couponBo);

        return BaseRespVo.ok(couponVo);

    }

}

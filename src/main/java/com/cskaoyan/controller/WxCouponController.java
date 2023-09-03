package com.cskaoyan.controller;

import com.cskaoyan.model.bo.WxCouponExchangeBo;
import com.cskaoyan.model.bo.WxCouponReceiveBo;
import com.cskaoyan.model.vo.*;
import com.cskaoyan.service.WxCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("wx/coupon")
public class WxCouponController {

    @Autowired
    WxCouponService wxCouponService;

    @RequestMapping("list")
    public BaseRespVo list(Integer page, Integer size) {
        WxCouponListVo wxCouponListVo = wxCouponService.list(page, size);
        if (wxCouponListVo != null) {
            return BaseRespVo.ok(wxCouponListVo);
        } else return BaseRespVo.fail("服务器繁忙！");
    }

    @RequestMapping("receive")
    public BaseRespVo receive(@RequestBody WxCouponReceiveBo wxCouponReceiveBo) {
        int result;
        result = wxCouponService.receive(wxCouponReceiveBo.getCouponId());
        if (result == 200) {
            return BaseRespVo.ok();
        } else if (result == 740) {
            return BaseRespVo.fail3("优惠券已经领取过");
        } else if (result == 400) {
            return BaseRespVo.fail3("该优惠卷已被领取完！");
        } else return BaseRespVo.fail("服务器繁忙！");
    }

    @RequestMapping("mylist")
    public BaseRespVo mylist(Integer status, Integer page, Integer size) {
        WxCouponMyListVo wxCouponMyListVo = wxCouponService.mylist(status, page, size);
        if (wxCouponMyListVo != null) {
            return BaseRespVo.ok(wxCouponMyListVo);
        }
        return BaseRespVo.fail("无！");
    }

    @RequestMapping("exchange")
    public BaseRespVo exchange(@RequestBody WxCouponExchangeBo wxCouponExchangeBo) {
        int result = wxCouponService.exchange(wxCouponExchangeBo.getCode());
        if (result == 200) {
            return BaseRespVo.ok();
        } else if (result == 740) {
            return BaseRespVo.fail(740, "优惠券已兑换");
        } else if (result == 742) {
            return BaseRespVo.fail(742, "优惠券不正确");
        } else return BaseRespVo.fail("领取失败！");
    }

    @RequestMapping("selectlist")
    public BaseRespVo selectlist(Integer cartId, Integer grouponRulesId) {
        List<WxCouponMyListDataVo> wxCouponListDataVoList = wxCouponService.selectlist(cartId, grouponRulesId);
        if (wxCouponListDataVoList != null) {
            return BaseRespVo.ok(wxCouponListDataVoList);
        } else return BaseRespVo.fail("你没有优惠卷");
    }

}

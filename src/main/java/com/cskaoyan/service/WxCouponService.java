package com.cskaoyan.service;

import com.cskaoyan.model.vo.WxCouponListDataVo;
import com.cskaoyan.model.vo.WxCouponListVo;
import com.cskaoyan.model.vo.WxCouponMyListDataVo;
import com.cskaoyan.model.vo.WxCouponMyListVo;

import java.util.List;

public interface WxCouponService {
    WxCouponListVo list(Integer page, Integer size);

    int receive(Integer couponId);

    WxCouponMyListVo mylist(Integer status, Integer page, Integer size);

    int exchange(String code);

    List<WxCouponMyListDataVo> selectlist(Integer cartId, Integer grouponRulesId);
}

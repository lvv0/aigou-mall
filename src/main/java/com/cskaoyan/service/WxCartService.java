package com.cskaoyan.service;

import com.cskaoyan.model.bo.WxCartAddBo;
import com.cskaoyan.model.bo.WxCartCheckedBo;
import com.cskaoyan.model.bo.WxCartUpdateBo;
import com.cskaoyan.model.vo.WxCartCheckOutVo;
import com.cskaoyan.model.vo.WxCartIndexVo;

public interface WxCartService {
    WxCartIndexVo index(String username, Integer userId);

    WxCartIndexVo checked(WxCartCheckedBo wxCartCheckedBo, String username);

    int update(WxCartUpdateBo wxCartUpdateBo);

    WxCartIndexVo delete(Integer[] productIds, String username);

    Integer add(WxCartAddBo wxCartAddBo, String username);

    Integer fastAdd(WxCartAddBo wxCartAddBo, String username);

    WxCartCheckOutVo checkOut(Integer cartId, Integer addressId, Integer couponId, Integer grouponRulesId, String username);

    Integer goodsCount(String username);
}

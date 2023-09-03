package com.cskaoyan.controller;

import com.cskaoyan.model.vo.BaseRespVo;
import com.cskaoyan.model.vo.StatRespVo;
import com.cskaoyan.service.StatService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：lww
 * @description： 统计报表
 * @date ：2021/8/12 20:15
 */
@RestController
@RequestMapping("admin/stat")
public class StatController {
    @Autowired
    StatService statService;

    /**
     * 用户统计
     * @return
     */
    @RequiresPermissions(value = {"*","admin:stat:user"},logical = Logical.OR)
    @RequestMapping("user")
    public BaseRespVo getUserStat(){
        StatRespVo userStat = statService.getUserStat();
        return BaseRespVo.ok(userStat);
    }

    /**
     * 订单统计
     * @return
     */
    @RequiresPermissions(value = {"*","admin:stat:order"},logical = Logical.OR)
    @RequestMapping("order")
    public BaseRespVo getOrderStat(){
        StatRespVo orderStat = statService.getOrderStat();
        return BaseRespVo.ok(orderStat);
    }

    /**
     * 商品统计
     * @return
     */
    @RequiresPermissions(value = {"*","admin:stat:goods"},logical = Logical.OR)
    @RequestMapping("goods")
    public BaseRespVo GetGoodsStat(){
        StatRespVo goodsStat = statService.getGoodsStat();
        return BaseRespVo.ok(goodsStat);
    }
}

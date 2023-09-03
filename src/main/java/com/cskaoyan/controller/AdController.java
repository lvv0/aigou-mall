package com.cskaoyan.controller;

import com.cskaoyan.model.bean.Ad;
import com.cskaoyan.model.bo.BaseParamBo;
import com.cskaoyan.model.vo.BaseRespData;
import com.cskaoyan.model.vo.BaseRespVo;
import com.cskaoyan.service.AdService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Name : AdController.java
 * @Time : 2021/8/11 21:11
 * @Author : Ashe
 * @Software : IntelliJ IDEA
 */
@RestController
@RequestMapping("admin/ad")
public class AdController {

    @Autowired
    AdService adService;

    @RequiresPermissions(value = {"*","admin:ad:list"},logical = Logical.OR)
    @RequestMapping("list")
    public BaseRespVo list(String name, String content, BaseParamBo param) {

        BaseRespData baseRespData = adService.query(name, content, param);

        return BaseRespVo.ok(baseRespData);

    }

    @RequiresPermissions(value = {"*","admin:ad:update"},logical = Logical.OR)
    @RequestMapping("update")
    public BaseRespVo update(@RequestBody Ad ad) {

        Ad adVo = adService.update(ad);

        return BaseRespVo.ok(adVo);

    }

    @RequiresPermissions(value = {"*","admin:ad:create"},logical = Logical.OR)
    @RequestMapping("create")
    public BaseRespVo create(@RequestBody Ad ad) {

        Ad adVo = adService.create(ad);

        return BaseRespVo.ok(adVo);

    }

    @RequiresPermissions(value = {"*","admin:ad:delete"},logical = Logical.OR)
    @RequestMapping("delete")
    public BaseRespVo delete(@RequestBody Ad ad) {

        ad.setDeleted(true);

        adService.delete(ad);

        return BaseRespVo.ok();

    }

}

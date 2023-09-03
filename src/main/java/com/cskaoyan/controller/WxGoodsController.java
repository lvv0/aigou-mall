package com.cskaoyan.controller;


import com.cskaoyan.model.bo.WxGoodsParamBo;
import com.cskaoyan.model.vo.*;
import com.cskaoyan.service.WxGoodsService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Name : WxGoodsController.java
 * @Time : 2021/8/14 22:48
 * @Author : Ashe
 * @Software : IntelliJ IDEA
 */
@RestController
@RequestMapping("wx/goods")
public class WxGoodsController {

    @Autowired
    WxGoodsService wxGoodsService;

    @RequestMapping("count")
    public BaseRespVo count() {

        WxGoodsCountVo wxGoodsCountVo = wxGoodsService.count();

        return BaseRespVo.ok(wxGoodsCountVo);

    }

    @RequestMapping("list")
    public BaseRespVo list(String keyword, WxGoodsParamBo param, Integer categoryId, Integer brandId, Boolean isNew, Boolean isHot) {

        WxGoodsListVo wxGoodsListVo = wxGoodsService.list(keyword, param, categoryId, brandId, isNew, isHot);

        return BaseRespVo.ok(wxGoodsListVo);

    }

    @RequestMapping("detail")
    public BaseRespVo detail(Integer id) {

        WxGoodsDetailVo wxGoodsDetailVo = wxGoodsService.detail(id);

        return BaseRespVo.ok(wxGoodsDetailVo);

    }

    @RequestMapping("related")
    public BaseRespVo related(Integer id) {

        WxGoodsRelatedVo wxGoodsRelatedVo = wxGoodsService.related(id);

        return BaseRespVo.ok(wxGoodsRelatedVo);

    }

    @RequestMapping("category")
    public BaseRespVo category(Integer id) {

        WxGoodsCategoryVo wxGoodsCategoryVo = wxGoodsService.category(id);

        return BaseRespVo.ok(wxGoodsCategoryVo);

    }


}

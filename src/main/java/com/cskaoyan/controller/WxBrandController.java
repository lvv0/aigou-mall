package com.cskaoyan.controller;

import com.cskaoyan.model.bean.Brand;
import com.cskaoyan.model.vo.BaseRespVo;
import com.cskaoyan.model.vo.WxBrandDetailVo;
import com.cskaoyan.model.vo.WxBrandListVo;
import com.cskaoyan.service.WxBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：lww
 * @description：
 * @date ：2021/8/14 17:26
 */
@RestController
@RequestMapping("wx/brand")
public class WxBrandController {

    @Autowired
    WxBrandService wxBrandService;

    @RequestMapping("list")
    public BaseRespVo getBrandList(Integer page,Integer size){
        WxBrandListVo brandList = wxBrandService.getBrandList(page, size);
        return BaseRespVo.ok(brandList);
    }

    @RequestMapping("detail")
    public BaseRespVo getBrandDetail(Integer id){
        Brand brandDetail = wxBrandService.getBrandDetail(id);
        return BaseRespVo.ok(new WxBrandDetailVo(brandDetail));
    }
}

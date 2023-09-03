package com.cskaoyan.controller;

import com.cskaoyan.model.vo.BaseRespVo;
import com.cskaoyan.model.vo.WxCategoryVo;
import com.cskaoyan.service.WxCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：lww
 * @description：
 * @date ：2021/8/14 20:52
 */
@RestController
@RequestMapping("wx/catalog")
public class WxCategoryController {
    @Autowired
    WxCategoryService wxCategoryService;
    @RequestMapping("index")
    public BaseRespVo getCategoryIndex(){
        WxCategoryVo categoryIndex = wxCategoryService.getCategoryIndex();
        return BaseRespVo.ok(categoryIndex);
    }
    @RequestMapping("current")
    public BaseRespVo getCurrentCategory(Integer id){
        WxCategoryVo currentCategory = wxCategoryService.getCurrentCategory(id);
        return BaseRespVo.ok(currentCategory);
    }
}

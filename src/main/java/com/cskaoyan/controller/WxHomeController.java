package com.cskaoyan.controller;

import com.cskaoyan.model.vo.BaseRespVo;
import com.cskaoyan.model.vo.WxHomeIndexVo;
import com.cskaoyan.service.WxHomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Name : WxHomeController.java
 * @Time : 2021/8/14 10:43
 * @Author : Ashe
 * @Software : IntelliJ IDEA
 */
@RestController
@RequestMapping("wx/home")
public class WxHomeController {

    @Autowired
    WxHomeService wxHomeService;

    @RequestMapping("index")
    public BaseRespVo index() {

        WxHomeIndexVo wxHomeIndexVo = wxHomeService.index();

        return BaseRespVo.ok(wxHomeIndexVo);

    }


}

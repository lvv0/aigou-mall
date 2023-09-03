package com.cskaoyan.controller;

import com.cskaoyan.model.vo.BaseRespVo;
import com.cskaoyan.model.vo.WxUserIndexVo;
import com.cskaoyan.service.WxUserService;
import com.cskaoyan.utils.UserInfoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Name : WxUserController.java
 * @Time : 2021/8/14 12:11
 * @Author : Ashe
 * @Software : IntelliJ IDEA
 */
@RestController
@RequestMapping("wx/user")
public class WxUserController {

    @Autowired
    UserInfoUtils userInfoUtils;

    @Autowired
    WxUserService wxUserService;

    @RequestMapping("index")
    public BaseRespVo index(){

        Integer userId = userInfoUtils.getUserId();

        WxUserIndexVo wxUserIndexVo= wxUserService.index(userId);

        return BaseRespVo.ok(wxUserIndexVo);

    }



}

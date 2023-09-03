package com.cskaoyan.controller;

import com.cskaoyan.model.bo.ExpressConfigBo;
import com.cskaoyan.model.bo.MallConfigBo;
import com.cskaoyan.model.bo.OrderConfigBo;
import com.cskaoyan.model.bo.WxConfigBo;
import com.cskaoyan.model.vo.*;
import com.cskaoyan.service.ConfigManageService;
import com.cskaoyan.utils.ValidationUtils;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author ：lww
 * @description：配置管理
 * @date ：2021/8/12 15:00
 */
@RestController
@RequestMapping("admin/config")
public class ConfigManageController {
    @Autowired
    ConfigManageService configManageService;

    @RequiresPermissions(value = {"*","admin:config:mall:list"},logical = Logical.OR)
    @GetMapping("mall")
    public BaseRespVo getMallInfo(){
        MallConfigVo mallInfo = configManageService.getMallInfo();
        return BaseRespVo.ok(mallInfo);
    }

    @RequiresPermissions(value = {"*","admin:config:mall:updateConfigs"},logical = Logical.OR)
    @PostMapping("mall")
    public BaseRespVo updateMallInfo(@Validated @RequestBody MallConfigBo mallConfigBo, BindingResult bindingResult){
        BaseRespVo baseRespVo = ValidationUtils.dealWithFiledError(bindingResult);
        if(baseRespVo.getErrno()==640){
            return baseRespVo;
        }
        int i = configManageService.updateMallInfo(mallConfigBo);
        return BaseRespVo.ok();
    }
    @RequiresPermissions(value = {"*","admin:config:express:list"},logical = Logical.OR)
    @GetMapping("express")
    public BaseRespVo getExpressInfo(){
        ExpressConfigVo expressInfo = configManageService.getExpressInfo();
        return BaseRespVo.ok(expressInfo);
    }
    @RequiresPermissions(value = {"*","admin:config:express:updateConfigs"},logical = Logical.OR)
    @PostMapping("express")
    public BaseRespVo updateExpressInfo(@Validated@RequestBody ExpressConfigBo expressConfigBo,BindingResult bindingResult){
        BaseRespVo baseRespVo = ValidationUtils.dealWithFiledError(bindingResult);
        if(baseRespVo.getErrno()==640){
            return baseRespVo;
        }
        int i = configManageService.updateExpressInfo(expressConfigBo);
        return BaseRespVo.ok();
    }

    @RequiresPermissions(value = {"*","admin:config:order:list"},logical = Logical.OR)
    @GetMapping("order")
    public BaseRespVo getOrderConfig(){
        OrderConfigVo orderConfig = configManageService.getOrderConfig();
        return BaseRespVo.ok(orderConfig);
    }
    @RequiresPermissions(value = {"*","admin:config:order:updateConfigs"},logical = Logical.OR)
    @PostMapping("order")
    public BaseRespVo updateOrderConfig(@Validated@RequestBody OrderConfigBo orderConfigBo,BindingResult bindingResult){
        BaseRespVo baseRespVo = ValidationUtils.dealWithFiledError(bindingResult);
        if(baseRespVo.getErrno()==640){
            return baseRespVo;
        }
        int i = configManageService.updateOrderConfig(orderConfigBo);
        return BaseRespVo.ok();
    }

    @RequiresPermissions(value = {"*","admin:config:wx:list"},logical = Logical.OR)
    @GetMapping("wx")
    public BaseRespVo getWxConfig(){
        WxConfigVo wxConfig = configManageService.getWxConfig();
        return BaseRespVo.ok(wxConfig);
    }
    @RequiresPermissions(value = {"*","admin:config:wx:updateConfigs"},logical = Logical.OR)
    @PostMapping("wx")
    public BaseRespVo updateWxConfig(@Validated@RequestBody WxConfigBo wxConfigBo, BindingResult bindingResult){
        BaseRespVo baseRespVo = ValidationUtils.dealWithFiledError(bindingResult);
        if(baseRespVo.getErrno()==640){
            return baseRespVo;
        }
        int i = configManageService.updateWxConfig(wxConfigBo);
        return BaseRespVo.ok();
    }

}

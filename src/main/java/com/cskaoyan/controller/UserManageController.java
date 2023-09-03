package com.cskaoyan.controller;

import com.cskaoyan.model.bo.BaseParamBo;
import com.cskaoyan.model.vo.BaseRespVo;
import com.cskaoyan.model.vo.UserManageListVo;
import com.cskaoyan.service.UserManageService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：lww
 * @description：用户管理
 * @date ：2021/8/11 20:12
 */
@RestController
@RequestMapping("admin")
public class UserManageController {
    @Autowired
    UserManageService userManageService;

    @RequiresPermissions(value = {"*","admin:user:list"},logical = Logical.OR)
    @RequestMapping("user/list")
    public BaseRespVo getUserList(String username, String mobile, BaseParamBo baseParamBo){
        UserManageListVo userManageListVo = userManageService.getUserList(username, mobile, baseParamBo);
        return BaseRespVo.ok(userManageListVo);
    }
    @RequiresPermissions(value = {"*","admin:address:list"},logical = Logical.OR)
    @RequestMapping("address/list")
    public BaseRespVo getAddressList(String userId,String name,BaseParamBo baseParamBo){
        UserManageListVo addressListVo = userManageService.getAddressList(userId, name, baseParamBo);
        return BaseRespVo.ok(addressListVo);
    }
    @RequiresPermissions(value = {"*","admin:collect:list"},logical = Logical.OR)
    @RequestMapping("collect/list")
    public BaseRespVo getCollectList(String userId,String valueId,BaseParamBo baseParamBo){
        UserManageListVo collectList = userManageService.getCollectList(userId, valueId, baseParamBo);
        return BaseRespVo.ok(collectList);
    }
    @RequiresPermissions(value = {"*","admin:footprint:list"},logical = Logical.OR)
    @RequestMapping("footprint/list")
    public BaseRespVo getFootprintList(String userId,String goodsId,BaseParamBo baseParamBo){
        UserManageListVo footprintList = userManageService.getFootprintList(userId, goodsId, baseParamBo);
        return BaseRespVo.ok(footprintList);
    }
    @RequiresPermissions(value = {"*","admin:history:list"},logical = Logical.OR)
    @RequestMapping("history/list")
    public BaseRespVo getHistoryList(String userId,String keyword,BaseParamBo baseParamBo){
        UserManageListVo searchHistoryList = userManageService.getSearchHistoryList(userId, keyword, baseParamBo);
        return BaseRespVo.ok(searchHistoryList);
    }
    @RequiresPermissions(value = {"*","admin:feedback:list"},logical = Logical.OR)
    @RequestMapping("feedback/list")
    public BaseRespVo getFeedbackList(String username,String id,BaseParamBo baseParamBo){
        UserManageListVo footprintList = userManageService.getFeedBackList(username, id, baseParamBo);
        return BaseRespVo.ok(footprintList);
    }
}

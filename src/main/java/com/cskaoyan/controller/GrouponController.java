package com.cskaoyan.controller;

import com.cskaoyan.model.bean.GrouponRules;
import com.cskaoyan.model.bo.BaseParamBo;
import com.cskaoyan.model.bo.GrouponRulesBo;
import com.cskaoyan.model.vo.BaseRespData;
import com.cskaoyan.model.vo.BaseRespVo;
import com.cskaoyan.service.GrouponService;
import com.cskaoyan.utils.ValidationUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Name : GrouponController.java
 * @Time : 2021/8/13 11:52
 * @Author : Ashe
 * @Software : IntelliJ IDEA
 */
@RestController
@RequestMapping("admin/groupon")
public class GrouponController {

    @Autowired
    GrouponService grouponService;

    @RequiresPermissions(value = {"*","admin:groupon:list"},logical = Logical.OR)
    @RequestMapping("list")
    public BaseRespVo list(String goodsId, BaseParamBo param) {

        Integer parseInt = null;

        if (goodsId == null || goodsId.equals("")){

            BaseRespData baseRespData = grouponService.query(parseInt, param);

            return BaseRespVo.ok(baseRespData);
        }

        try {
            parseInt = Integer.parseInt(goodsId);
        } catch (NumberFormatException e) {
            return BaseRespVo.fail("参数值不对!");
        }

        BaseRespData baseRespData = grouponService.query(parseInt, param);

        return BaseRespVo.ok(baseRespData);

    }

    @RequiresPermissions(value = {"*","admin:groupon:update"},logical = Logical.OR)
    @RequestMapping("update")
    public BaseRespVo update(@Validated @RequestBody GrouponRulesBo grouponRulesBo, BindingResult bindingResult) {

        BaseRespVo baseRespVo = ValidationUtils.dealWithFiledError(bindingResult);

        if (baseRespVo.getErrno() == 640){
            return baseRespVo;
        }

        Integer update = grouponService.update(grouponRulesBo);

        if (update.equals(0)){
            return BaseRespVo.fail("不允许修改商品id!");
        }

        return BaseRespVo.ok();

    }

    @RequiresPermissions(value = {"*","admin:groupon:delete"},logical = Logical.OR)
    @RequestMapping("delete")
    public BaseRespVo delete(@RequestBody GrouponRules grouponRules) {

        grouponRules.setDeleted(true);

        grouponService.delete(grouponRules);

        return BaseRespVo.ok();

    }

    @RequiresPermissions(value = {"*","admin:groupon:create"},logical = Logical.OR)
    @RequestMapping("create")
    public BaseRespVo create(@Validated @RequestBody GrouponRulesBo grouponRulesBo, BindingResult bindingResult) {

        BaseRespVo baseRespVo = ValidationUtils.dealWithFiledError(bindingResult);

        if (baseRespVo.getErrno() == 640){
            return baseRespVo;
        }

        GrouponRules grouponRulesVo = grouponService.create(grouponRulesBo);

        if (grouponRulesVo == null) {
            return BaseRespVo.fail("商品id参数值不对!");
        }

        return BaseRespVo.ok(grouponRulesVo);

    }

    @RequestMapping("listRecord")
    public BaseRespVo listRecord(String goodsId, BaseParamBo param){

        Integer parseInt = null;

        if (goodsId == null || goodsId.equals("")){

            BaseRespData baseRespData = grouponService.listRecord(parseInt, param);

            return BaseRespVo.ok(baseRespData);
        }

        try {
            parseInt = Integer.parseInt(goodsId);
        } catch (NumberFormatException e) {
            return BaseRespVo.fail("参数值不对!");
        }

        BaseRespData baseRespData = grouponService.listRecord(parseInt, param);

        return BaseRespVo.ok(baseRespData);

    }

}

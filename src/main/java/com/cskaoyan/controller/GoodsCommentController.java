package com.cskaoyan.controller;

import com.cskaoyan.model.bean.Comment;
import com.cskaoyan.model.bo.GoodsCommentListBo;
import com.cskaoyan.model.vo.BaseRespVo;
import com.cskaoyan.model.vo.GoodsCommentListVo;
import com.cskaoyan.service.GoodsCommentService;
import com.cskaoyan.utils.ValidationUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("admin/comment")
@RestController
public class GoodsCommentController {

    @Autowired
    GoodsCommentService goodsCommentService;

    @RequiresPermissions(value = {"*","admingit :comment:list"},logical = Logical.OR)
    @RequestMapping("list")
    public BaseRespVo list(@Validated GoodsCommentListBo goodsCommentListBo, BindingResult bindingResult){
//        BaseRespVo baseRespVo = ValidationUtils.dealWithFiledError(bindingResult);
//        if (baseRespVo != null){
//            return baseRespVo;
//        }
        GoodsCommentListVo goodsCommentListVo = goodsCommentService.list(goodsCommentListBo);
        if (goodsCommentListVo != null){
            return BaseRespVo.ok(goodsCommentListVo);
        }else {
            return BaseRespVo.fail("服务器繁忙！");
        }
    }

    @RequiresPermissions(value = {"*","admin:comment:delete"},logical = Logical.OR)
    @RequestMapping("delete")
    public BaseRespVo delete(@RequestBody Comment comment){
        if (goodsCommentService.delete(comment)){
            return BaseRespVo.ok();
        }else return BaseRespVo.fail("服务器繁忙！");

    }
}

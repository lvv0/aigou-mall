package com.cskaoyan.controller;

import com.cskaoyan.model.bean.Topic;
import com.cskaoyan.model.bo.BaseParamBo;
import com.cskaoyan.model.vo.BaseRespData;
import com.cskaoyan.model.vo.BaseRespVo;
import com.cskaoyan.service.TopicService;
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
 * @Name : TopicController.java
 * @Time : 2021/8/12 23:37
 * @Author : Ashe
 * @Software : IntelliJ IDEA
 */
@RestController
@RequestMapping("admin/topic")
public class TopicController {

    @Autowired
    TopicService topicService;

    @RequiresPermissions(value = {"*","admin:topic:list"},logical = Logical.OR)
    @RequestMapping("list")
    public BaseRespVo list(String title, String subtitle, BaseParamBo param) {

        BaseRespData baseRespData = topicService.query(title, subtitle, param);

        return BaseRespVo.ok(baseRespData);

    }

    @RequiresPermissions(value = {"*","admin:topic:update"},logical = Logical.OR)
    @RequestMapping("update")
    public BaseRespVo update(@Validated @RequestBody Topic topic, BindingResult bindingResult){

        BaseRespVo baseRespVo = ValidationUtils.dealWithFiledError(bindingResult);

        if (baseRespVo.getErrno() == 640){
            return baseRespVo;
        }

        Topic topicVo = topicService.update(topic);

        return BaseRespVo.ok(topicVo);

    }

    @RequiresPermissions(value = {"*","admin:topic:delete"},logical = Logical.OR)
    @RequestMapping("delete")
    public BaseRespVo delete(@RequestBody Topic topic){

        topic.setDeleted(true);

        topicService.delete(topic);

        return BaseRespVo.ok();

    }

    @RequiresPermissions(value = {"*","admin:topic:create"},logical = Logical.OR)
    @RequestMapping("create")
    public BaseRespVo create(@Validated @RequestBody Topic topic,BindingResult bindingResult){

        BaseRespVo baseRespVo = ValidationUtils.dealWithFiledError(bindingResult);

        if (baseRespVo.getErrno() == 640){
            return baseRespVo;
        }

        Topic topicVo = topicService.create(topic);

        return BaseRespVo.ok(topicVo);

    }


}

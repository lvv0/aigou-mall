package com.cskaoyan.controller;

import com.cskaoyan.model.bo.BaseParamBo;
import com.cskaoyan.model.vo.BaseRespData;
import com.cskaoyan.model.vo.BaseRespVo;
import com.cskaoyan.service.LogService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin/log")
public class LogController {
    @Autowired
    LogService logService;

    @RequiresPermissions(value = {"*","admin:log:list"},logical = Logical.OR)
    @GetMapping("list")
    public BaseRespVo list(BaseParamBo baseParamBo, String name) {
        BaseRespData baseRespData = logService.list(baseParamBo, name);
        if (baseRespData == null) {
            return BaseRespVo.fail("something wrong");
        }
        return BaseRespVo.ok(baseRespData);
    }
}

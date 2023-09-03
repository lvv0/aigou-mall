package com.cskaoyan.controller;

import com.cskaoyan.model.bean.Admin;
import com.cskaoyan.model.bo.BaseParamBo;
import com.cskaoyan.model.vo.AdminVo;
import com.cskaoyan.model.vo.BaseRespData;
import com.cskaoyan.model.vo.BaseRespVo;
import com.cskaoyan.service.AdminService;
import com.cskaoyan.utils.ValidationUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin/admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    @RequiresPermissions(value = {"*", "admin:admin:list"}, logical = Logical.OR)
    @GetMapping("list")
    public BaseRespVo list(BaseParamBo baseParamBo, String username) {

        //Subject subject = SecurityUtils.getSubject();
        //boolean permitted = subject.isPermitted("admin:admin:list");

        BaseRespData baseRespData = adminService.listAdmin(baseParamBo, username);
        return BaseRespVo.ok(baseRespData);
    }

    @RequiresPermissions(value = {"*", "admin:admin:delete"}, logical = Logical.OR)
    @PostMapping("delete")
    public BaseRespVo delete(@RequestBody Admin admin) {
        Integer code = adminService.delete(admin);
        if (code == 200) {
            return BaseRespVo.ok();
        }
        return BaseRespVo.fail();
    }

    @RequiresPermissions(value = {"*", "admin:admin:create"}, logical = Logical.OR)
    @PostMapping("create")
    public BaseRespVo create(@RequestBody @Validated Admin admin, BindingResult bindingResult) {
        //默认头像 密码用户名校验
        BaseRespVo baseRespVo = ValidationUtils.dealWithValueError(bindingResult);
        if (baseRespVo != null) {
            return baseRespVo;
        }
        AdminVo adminVo = adminService.create(admin);
        if (adminVo == null) {
            return BaseRespVo.fail("管理员名称已存在");
        }
        return BaseRespVo.ok(adminVo);
    }

    @RequiresPermissions(value = {"*", "admin:admin:update"}, logical = Logical.OR)
    @PostMapping("update")
    public BaseRespVo update(@RequestBody @Validated Admin admin, BindingResult bindingResult) {
        //密码用户名校验
        BaseRespVo baseRespVo = ValidationUtils.dealWithValueError(bindingResult);
        if (baseRespVo != null) {
            return baseRespVo;
        }
        AdminVo adminVo = adminService.update(admin);
        if (adminVo == null) {
            return BaseRespVo.fail("用户名重复");
        }
        return BaseRespVo.ok(adminVo);
    }

}



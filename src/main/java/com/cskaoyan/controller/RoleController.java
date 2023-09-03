package com.cskaoyan.controller;

import com.cskaoyan.model.bean.Role;
import com.cskaoyan.model.bo.BaseParamBo;
import com.cskaoyan.model.bo.PermissionPostBo;
import com.cskaoyan.model.vo.*;
import com.cskaoyan.service.RoleService;
import com.cskaoyan.utils.ValidationUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin/role")
public class RoleController {
    @Autowired
    RoleService roleService;

    @RequiresPermissions(value = {"*", "admin:role:read"}, logical = Logical.OR)
    @GetMapping("options")
    public BaseRespVo options() {
        List<RoleOptionsVo> roleOptionsVoList = roleService.selectRole();
        return BaseRespVo.ok(roleOptionsVoList);
    }

    @RequiresPermissions(value = {"*", "admin:role:list"}, logical = Logical.OR)
    @GetMapping("list")
    public BaseRespVo list(BaseParamBo baseParamBo, String name) {
        BaseRespData<Role> baseRespData = roleService.list(baseParamBo, name);
        return BaseRespVo.ok(baseRespData);
    }

    @RequiresPermissions(value = {"*", "admin:role:create"}, logical = Logical.OR)
    @PostMapping("create")
    public BaseRespVo create(@RequestBody @Validated Role role, BindingResult bindingResult) {
        BaseRespVo baseRespVo = ValidationUtils.dealWithValueError(bindingResult);
        if (baseRespVo != null) {
            return baseRespVo;
        }
        RoleVo roleVo = roleService.create(role.getName(), role.getDesc());
        if (roleVo == null) {
            return BaseRespVo.fail("角色名称重复");
        }
        return BaseRespVo.ok(roleVo);
    }

    @RequiresPermissions(value = {"*", "admin:role:delete"}, logical = Logical.OR)
    @PostMapping("delete")
    public BaseRespVo delete(@RequestBody Role role) {
        Integer code = roleService.delete(role);
        if (code != 200) {
            return BaseRespVo.fail("删除失败");
        }
        return BaseRespVo.ok();
    }

    @RequiresPermissions(value = {"*", "admin:role:update"}, logical = Logical.OR)
    @PostMapping("update")
    public BaseRespVo update(@RequestBody @Validated Role role, BindingResult bindingResult) {
        BaseRespVo baseRespVo = ValidationUtils.dealWithValueError(bindingResult);
        if (baseRespVo != null) {
            return baseRespVo;
        }
        Integer code = roleService.update(role);
        if (code == 404) {
            return BaseRespVo.fail("角色名称重复");
        }
        if (code == 500) {
            return BaseRespVo.fail("插入失败");
        }
        return BaseRespVo.ok();
    }

    @RequiresPermissions(value = {"*", "admin:role:permission:get"}, logical = Logical.OR)
    @GetMapping("permissions")
    public BaseRespVo permissionsGet(Integer roleId) {
        RolePermissionsVo rolePermissionsVo = roleService.permissionsGet(roleId);
        if (rolePermissionsVo == null) {
            return BaseRespVo.fail("something wrong");
        }
        return BaseRespVo.ok(rolePermissionsVo);
    }

    @RequiresPermissions(value = {"*", "admin:role:permission:update"}, logical = Logical.OR)
    @PostMapping("permissions")
    public BaseRespVo permissionsPost(@RequestBody PermissionPostBo permissionPostBo) {
        int code = roleService.permissionsPost(permissionPostBo);
        if (code != 200) {
            return BaseRespVo.fail("授权失败");
        }
        return BaseRespVo.ok();
    }
}

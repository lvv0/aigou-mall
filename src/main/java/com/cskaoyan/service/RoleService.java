package com.cskaoyan.service;

import com.cskaoyan.model.bean.Role;
import com.cskaoyan.model.bo.BaseParamBo;
import com.cskaoyan.model.bo.PermissionPostBo;
import com.cskaoyan.model.vo.BaseRespData;
import com.cskaoyan.model.vo.RoleOptionsVo;
import com.cskaoyan.model.vo.RolePermissionsVo;
import com.cskaoyan.model.vo.RoleVo;

import java.util.List;

public interface RoleService {
    List<RoleOptionsVo> selectRole();

    BaseRespData<Role> list(BaseParamBo baseParamBo, String name);

    RoleVo create(String name, String desc);

    Integer delete(Role role);

    Integer update(Role role);

    RolePermissionsVo permissionsGet(Integer roleId);

    int permissionsPost(PermissionPostBo permissionPostBo);
}

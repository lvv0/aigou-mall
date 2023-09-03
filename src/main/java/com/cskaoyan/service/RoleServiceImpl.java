package com.cskaoyan.service;

import com.cskaoyan.mapper.PermissionMapper;
import com.cskaoyan.mapper.PermissionTMapper;
import com.cskaoyan.mapper.RoleMapper;
import com.cskaoyan.model.bean.Permission;
import com.cskaoyan.model.bean.PermissionExample;
import com.cskaoyan.model.bean.Role;
import com.cskaoyan.model.bean.RoleExample;
import com.cskaoyan.model.bo.BaseParamBo;
import com.cskaoyan.model.bo.PermissionPostBo;
import com.cskaoyan.model.vo.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleMapper roleMapper;

    @Autowired
    PermissionMapper permissionMapper;

    @Autowired
    PermissionTMapper permissionTMapper;

    @Override
    public List<RoleOptionsVo> selectRole() {
        List<RoleOptionsVo> roleOptionsVoList = roleMapper.selectRole();
        return roleOptionsVoList;
    }

    @Override
    public BaseRespData<Role> list(BaseParamBo baseParamBo, String name) {
        PageHelper.startPage(baseParamBo.getPage(), baseParamBo.getLimit());
        RoleExample roleExample = new RoleExample();
        RoleExample.Criteria criteria = roleExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        if (name != null && !"".equals(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        roleExample.setOrderByClause(baseParamBo.getSort() + " " + baseParamBo.getOrder());
        List<Role> roleList = roleMapper.selectByExample(roleExample);
        PageInfo<Role> rolePageInfo = new PageInfo<>(roleList);
        long total = rolePageInfo.getTotal();
        return new BaseRespData<Role>(roleList, total);
    }

    @Override
    public RoleVo create(String name, String desc) {
        RoleExample roleExample = new RoleExample();
        RoleExample.Criteria criteria = roleExample.createCriteria();
        criteria.andNameEqualTo(name);
        criteria.andDeletedEqualTo(false);
        long l = roleMapper.countByExample(roleExample);
        if (l > 0) {
            return null;
        }
        Role role = new Role();
        role.setName(name);
        role.setDesc(desc);
        role.setAddTime(new Date());
        role.setUpdateTime(new Date());
        int i = roleMapper.insertSelective(role);
        if (i != 1) {
            return null;
        }
        Role role1 = roleMapper.selectByPrimaryKey(role.getId());
        RoleVo roleVo = new RoleVo();
        roleVo.setId(role1.getId());
        roleVo.setName(role1.getName());
        roleVo.setDesc(role1.getDesc());
        roleVo.setAddTime(role1.getAddTime());
        roleVo.setUpdateTime(role1.getUpdateTime());
        return roleVo;
    }

    @Override
    public Integer delete(Role role) {
        // todo 删除角色的权限
        Role role1 = new Role();
        role1.setId(role.getId());
        role1.setDeleted(true);
        int i = roleMapper.updateByPrimaryKeySelective(role1);
        if (i != 1) {
            return 500;
        }
        return 200;
    }

    @Override
    public Integer update(Role role) {
        RoleExample roleExample = new RoleExample();
        RoleExample.Criteria criteria = roleExample.createCriteria();
        criteria.andNameEqualTo(role.getName());
        criteria.andIdNotEqualTo(role.getId());
        criteria.andDeletedEqualTo(false);
        long l = roleMapper.countByExample(roleExample);
        if (l > 0) {
            return 404;
        }
        Role role1 = new Role();
        role1.setName(role.getName());
        role1.setDesc(role.getDesc());
        role1.setId(role.getId());
        role1.setUpdateTime(new Date());
        int i = roleMapper.updateByPrimaryKeySelective(role1);
        if (i != 1) {
            return 500;
        }
        return 200;
    }

    @Override
    //@Transactional(isolation = Isolation.REPEATABLE_READ)
    public RolePermissionsVo permissionsGet(Integer roleId) {
        PermissionExample permissionExample = new PermissionExample();
        PermissionExample.Criteria criteria = permissionExample.createCriteria();
        criteria.andRoleIdEqualTo(roleId);
        criteria.andDeletedEqualTo(false);
        List<Permission> permissions = permissionMapper.selectByExample(permissionExample);

        List<String> stringList = null;

        if (roleId == 1) {
            stringList = permissionTMapper.selectAllThird();
        }

        if (stringList == null) {
            stringList = new ArrayList<>();
            for (Permission permission : permissions) {
                stringList.add(permission.getPermission());
            }
        }
        List<SystemPermissions> systemPermissionsList = new ArrayList<>();
        //List<PermissionT> permissionTOneLevels = permissionTMapper.selectOneLevel();
        //for (PermissionT permissionTOneLevel : permissionTOneLevels) {
        //    SystemPermissions systemPermissions = new SystemPermissions();
        //    systemPermissions.setId(permissionTOneLevel.getLabel());
        //    systemPermissions.setLabel(permissionTOneLevel.getLabel());
        //    List<Children> childrenList = new ArrayList<>();
        //    List<PermissionT> permissionTTwoLevels = permissionTMapper.selectTwoLevel(permissionTOneLevel.getId());
        //    for (PermissionT permissionTTwoLevel : permissionTTwoLevels) {
        //        Children children = new Children();
        //        children.setId(permissionTTwoLevel.getLabel());
        //        children.setLabel(permissionTTwoLevel.getLabel());
        //        List<Child> childList = new ArrayList<>();
        //        List<PermissionT> permissionTThreeLevels = permissionTMapper.selectThreeLevel(permissionTTwoLevel.getId());
        //        for (PermissionT permissionTThreeLevel : permissionTThreeLevels) {
        //            Child child = new Child();
        //            child.setId(permissionTThreeLevel.getPermission());
        //            child.setLabel(permissionTThreeLevel.getLabel());
        //            child.setApi(permissionTThreeLevel.getApi());
        //            childList.add(child);
        //        }
        //        children.setChildren(childList);
        //        childrenList.add(children);
        //    }
        //    systemPermissions.setChildren(childrenList);
        //    systemPermissionsList.add(systemPermissions);
        //}

        List<PermissionT> permissionTLevels1 = permissionTMapper.selectOneLevel();
        List<PermissionT> permissionTLevels2 = permissionTMapper.selectSecLevel();
        List<PermissionT> permissionTLevels3 = permissionTMapper.selectThrLevel();
        for (PermissionT permissionT : permissionTLevels1) {
            SystemPermissions systemPermissions = new SystemPermissions();
            systemPermissions.setId(permissionT.getLabel());
            systemPermissions.setLabel(permissionT.getLabel());
            List<Children> childrenList = new ArrayList<>();
            for (PermissionT t : permissionTLevels2) {
                if (t.getFatherId().equals(permissionT.getId())) {
                    Children children = new Children();
                    children.setId(t.getLabel());
                    children.setLabel(t.getLabel());
                    List<Child> children1 = new ArrayList<>();
                    for (PermissionT permissionT1 : permissionTLevels3) {
                        if (permissionT1.getFatherId().equals(t.getId())) {
                            Child child = new Child();
                            child.setLabel(permissionT1.getLabel());
                            child.setId(permissionT1.getPermission());
                            child.setApi(permissionT1.getApi());
                            children1.add(child);
                        }
                    }
                    children.setChildren(children1);
                    childrenList.add(children);
                }
            }
            systemPermissions.setChildren(childrenList);
            systemPermissionsList.add(systemPermissions);
        }


        RolePermissionsVo rolePermissionsVo = new RolePermissionsVo();
        rolePermissionsVo.setAssignedPermissions(stringList);
        rolePermissionsVo.setSystemPermissions(systemPermissionsList);
        return rolePermissionsVo;
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public int permissionsPost(PermissionPostBo permissionPostBo) {
        //todo 可以考虑不删除更新
        PermissionExample permissionExample = new PermissionExample();
        PermissionExample.Criteria criteria = permissionExample.createCriteria();
        criteria.andRoleIdEqualTo(permissionPostBo.getRoleId());
        Permission permission = new Permission();
        permission.setDeleted(true);
        permissionMapper.updateByExampleSelective(permission, permissionExample);

        List<String> permissions = permissionPostBo.getPermissions();
        Permission permission1 = new Permission();
        permission1.setRoleId(permissionPostBo.getRoleId());
        permission1.setAddTime(new Date());
        permission1.setUpdateTime(new Date());
        for (String s : permissions) {
            permission1.setPermission(s);
            permissionMapper.insertSelective(permission1);
        }

        return 200;
    }
}

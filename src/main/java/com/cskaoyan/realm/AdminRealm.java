package com.cskaoyan.realm;

import com.cskaoyan.mapper.AdminMapper;
import com.cskaoyan.mapper.PermissionMapper;
import com.cskaoyan.model.bean.Admin;
import com.cskaoyan.model.bean.AdminExample;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ：lww
 * @description：自定义realm
 * @date ：2021/8/13 11:35
 */
@Component
public class AdminRealm extends AuthorizingRealm {

    @Autowired
    AdminMapper adminMapper;

    @Autowired
    PermissionMapper permissionMapper;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        MallToken token= (MallToken) authenticationToken;
        String username = token.getUsername();
        String credential = adminMapper.getCredentialByName(username);
        return new SimpleAuthenticationInfo(username,credential,this.getName());
    }
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        //授权 → 提供权限
        //获得principal信息
        //获得doGetAuthen方法（上面这个方法），构造的authenticationInfo中的principal信息
        String primaryPrincipal = (String) principalCollection.getPrimaryPrincipal();
        //根据principal信息可以查询对应的权限信息
        List<String> permissions = selectPermissionsByPrincipalFromDb(primaryPrincipal);

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.addStringPermissions(permissions);//提供权限 👉 找到所有的钥匙

        return authorizationInfo;
    }

    private List<String> selectPermissionsByPrincipalFromDb(String primaryPrincipal) {
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andUsernameEqualTo(primaryPrincipal);
        criteria.andDeletedEqualTo(false);
        List<Admin> admins = adminMapper.selectByExample(adminExample);
        List<String> permission = new ArrayList<>();
        for (Admin admin : admins) {
            Integer[] roleIds = admin.getRoleIds();
            for (Integer roleId : roleIds) {
                List<String> list = permissionMapper.selectPermissionByRoleId(roleId);
                permission.addAll(list);
            }
        }
        return permission;
    }

}

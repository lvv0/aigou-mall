package com.cskaoyan.realm;

import com.cskaoyan.mapper.UserMapper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WxRealm extends AuthorizingRealm {

    @Autowired
    UserMapper userMapper;


    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        MallToken token= (MallToken) authenticationToken;
        String username = token.getUsername();
        String credential = userMapper.getPassWordByUserName(username);
        return new SimpleAuthenticationInfo(username,credential,this.getName());
    }
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }
}

package com.cskaoyan.utils;

import com.cskaoyan.mapper.UserMapper;
import com.cskaoyan.model.bean.User;
import com.cskaoyan.model.bean.UserExample;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserInfoUtils {
    @Autowired
    UserMapper userMapper;

    public String getUsername() {
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        return username;
    }

    public Integer getUserId() {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        // TODO 自定义异常
        criteria.andUsernameEqualTo(getUsername());
        List<User> users = userMapper.selectByExample(userExample);
        return users.get(0).getId();
    }
}

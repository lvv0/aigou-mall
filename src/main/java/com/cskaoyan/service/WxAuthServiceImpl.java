package com.cskaoyan.service;

import com.cskaoyan.converter.StringToDateConverter;
import com.cskaoyan.mapper.UserMapper;
import com.cskaoyan.model.bean.User;
import com.cskaoyan.model.bean.UserExample;
import com.cskaoyan.model.bo.WxAuthRegisterBo;
import com.cskaoyan.model.bo.WxAuthResetBo;
import com.cskaoyan.model.bo.WxLoginBo;
import com.cskaoyan.model.vo.WxLoginVo;
import com.cskaoyan.model.vo.WxUserInfoBean;
import com.cskaoyan.utils.UserInfoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Name : WxAuthServiceImpl.java
 * @Time : 2021/8/14 11:04
 * @Author : Ashe
 * @Software : IntelliJ IDEA
 */
@Service
public class WxAuthServiceImpl implements WxAuthService{

    @Autowired
    UserMapper userMapper;

    @Autowired
    StringToDateConverter stringToDateConverter;

    @Autowired
    UserInfoUtils userInfoUtils;

    @Override
    public WxLoginVo login(WxLoginBo wxLoginBo) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUsernameEqualTo(wxLoginBo.getUsername());
        criteria.andDeletedEqualTo(false);
        List<User> users = userMapper.selectByExample(userExample);
        if (users.size() != 1 || users == null){
            return null;
        }
        WxLoginVo wxLoginVo = new WxLoginVo();
        WxUserInfoBean wxUserInfoBean = new WxUserInfoBean();
        for (User user : users) {
            wxUserInfoBean.setNickName(user.getNickname());
            wxUserInfoBean.setAvatarUrl(user.getAvatar());
        }
        wxLoginVo.setUserInfo(wxUserInfoBean);
        return wxLoginVo;
    }

    @Override
    public int register(WxAuthRegisterBo wxAuthRegisterBo) {
        int result1 = userMapper.judgeUserNameIsExist(wxAuthRegisterBo.getUsername());
        if (result1 == 1){
            return 300;
        }
        int result2 = userMapper.judgeMobilePhoneIsExist(wxAuthRegisterBo.getMobile());
        if (result2 == 1){
            return 400;
        }
        User user = new User();
        user.setUsername(wxAuthRegisterBo.getUsername());
        user.setPassword(wxAuthRegisterBo.getPassword());
        user.setMobile(wxAuthRegisterBo.getMobile());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = simpleDateFormat.format(new Date());
        Date convert = stringToDateConverter.convert(time);
        user.setAddTime(convert);
        user.setUpdateTime(convert);
        user.setUserLevel((byte) 0);
        user.setStatus((byte) 0);
        //  用户昵称 和 用户（默认）头像
        user.setNickname(wxAuthRegisterBo.getUsername());
        user.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif?imageView2/1/w/80/h/80");
        int result3 = userMapper.insertSelective(user);
        if (result3 == 1){
            return 200;
        }else return 500;
    }

    @Override
    public boolean reset(WxAuthResetBo wxAuthResetBo) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andMobileEqualTo(wxAuthResetBo.getMobile());
        criteria.andDeletedEqualTo(false);
        User user = new User();
        user.setPassword(wxAuthResetBo.getPassword());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = simpleDateFormat.format(new Date());
        Date convert = stringToDateConverter.convert(time);
        user.setUpdateTime(convert);
        int result = userMapper.updateByExampleSelective(user,userExample);
        return result == 1;
    }
}

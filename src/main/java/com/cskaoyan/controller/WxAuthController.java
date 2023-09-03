package com.cskaoyan.controller;

import com.cskaoyan.config.AliyunComponent;
import com.cskaoyan.model.bo.WxAuthRegCaptchaBo;
import com.cskaoyan.model.bo.WxAuthRegisterBo;
import com.cskaoyan.model.bo.WxAuthResetBo;
import com.cskaoyan.model.bo.WxLoginBo;
import com.cskaoyan.model.vo.BaseRespVo;
import com.cskaoyan.model.vo.WxLoginVo;
import com.cskaoyan.realm.MallToken;
import com.cskaoyan.service.WxAuthService;
import com.cskaoyan.utils.PasswordProcess;
import com.cskaoyan.utils.ValidationUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * @Name : WxAuthController.java
 * @Time : 2021/8/14 11:03
 * @Author : Ashe
 * @Software : IntelliJ IDEA
 */
@RestController
@RequestMapping("wx/auth")
public class WxAuthController {

    @Autowired
    WxAuthService wxAuthService;

    @Autowired
    AliyunComponent aliyunComponent;

    static String code;

    static Long sendTimeMillis; //发送验证码时的时间戳

    @RequestMapping("login")
    public BaseRespVo login(@RequestBody WxLoginBo wxLoginBo) {
        wxLoginBo.setPassword(PasswordProcess.passwordProcess(wxLoginBo.getPassword()));
        MallToken token = new MallToken(wxLoginBo.getUsername(), wxLoginBo.getPassword(), "wx");
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
        } catch (Exception e) {
            return BaseRespVo.fail("用户名或密码错误");
        }
        Session session = subject.getSession();
        WxLoginVo wxLoginVo = wxAuthService.login(wxLoginBo);
        if (wxLoginVo != null) {
            wxLoginVo.setToken(session.getId().toString());
            wxLoginVo.setTokenExpire(session.getLastAccessTime());
            return BaseRespVo.ok(wxLoginVo);
        } else return BaseRespVo.fail("登录失败");
    }

    @RequestMapping("logout")
    public BaseRespVo logout(){
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            subject.logout();
            return BaseRespVo.ok();
        }
        return BaseRespVo.fail("登出失败");
    }

    @RequestMapping("regCaptcha")
    public BaseRespVo regCaptcha(@RequestBody WxAuthRegCaptchaBo wxAuthRegCaptchaBo) {
        code = getFourRandom();
        System.out.println(code);
        aliyunComponent.sendMsg(wxAuthRegCaptchaBo.getMobile(), code);
        sendTimeMillis = System.currentTimeMillis();
        return BaseRespVo.ok();
    }

    //随机生成4位验证码
    public String getFourRandom() {
        Random random = new Random();
        String fourRandom = random.nextInt(10000) + "";
        int randLength = fourRandom.length();
        if (randLength < 4) {
            for (int i = 1; i <= 4 - randLength; i++)
                fourRandom = "0" + fourRandom;
        }
        return fourRandom;
    }

    @RequestMapping("register")
    public BaseRespVo register(@RequestBody @Validated WxAuthRegisterBo wxAuthRegisterBo, BindingResult bindingResult) {
        BaseRespVo baseRespVo = ValidationUtils.dealWithValueError(bindingResult);
        if (baseRespVo != null) {
            return baseRespVo;
        }

        wxAuthRegisterBo.setPassword(PasswordProcess.passwordProcess(wxAuthRegisterBo.getPassword()));
        long currentTimeMillis = System.currentTimeMillis();
        //判断验证码是否超过5分钟（验证码5分钟有效）
        if (currentTimeMillis - sendTimeMillis > 300000){
            return BaseRespVo.fail("验证码失效！");
        }
        if (code == null || code.length() == 0 || !code.equals(wxAuthRegisterBo.getCode())){
            return BaseRespVo.fail(703,"验证码错误");
        }
        int result = wxAuthService.register(wxAuthRegisterBo);
        if (result == 200){
            return BaseRespVo.ok("注册成功！");
        }else if (result == 300){
            return BaseRespVo.fail("用户名已存在！");
        }else if (result == 400){
            return BaseRespVo.fail("该手机号已被注册！");
        } else return BaseRespVo.fail("注册失败！");
    }

    @RequestMapping("reset")
    public BaseRespVo reset(@RequestBody @Validated WxAuthResetBo wxAuthResetBo, BindingResult bindingResult){
        BaseRespVo baseRespVo = ValidationUtils.dealWithValueError(bindingResult);
        if (baseRespVo != null) {
            return baseRespVo;
        }

        wxAuthResetBo.setPassword(PasswordProcess.passwordProcess(wxAuthResetBo.getPassword()));
        long currentTimeMillis = System.currentTimeMillis();
        //判断验证码是否超过5分钟（验证码5分钟有效）
        if (currentTimeMillis - sendTimeMillis > 300000){
            return BaseRespVo.fail("验证码失效！");
        }
        if (code == null || code.length() == 0 || !code.equals(wxAuthResetBo.getCode())){
            return BaseRespVo.fail(703,"验证码错误");
        }
        if (wxAuthService.reset(wxAuthResetBo)){
            return BaseRespVo.ok("重置密码成功！");
        }else return BaseRespVo.fail("重置密码失败！");
    }
}

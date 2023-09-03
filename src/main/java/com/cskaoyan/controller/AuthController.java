package com.cskaoyan.controller;

import com.cskaoyan.model.bo.LoginUserBo;
import com.cskaoyan.realm.MallToken;
import com.cskaoyan.service.AdminLoginService;
import com.cskaoyan.model.vo.AdminLoginInfoVo;
import com.cskaoyan.model.vo.BaseRespVo;
import com.cskaoyan.model.vo.DashBoardVo;
import com.cskaoyan.utils.PasswordProcess;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * @author ：lww
 * @description：登录、登出
 * @date ：2021/8/11 18:55
 */
@RestController
@RequestMapping("admin")
public class AuthController {
    @Autowired
    AdminLoginService adminLoginService;

    @RequestMapping("auth/login")
    public BaseRespVo login(@RequestBody LoginUserBo loginUserBo){
//        loginUserBo.setPassword(PasswordProcess.passwordProcess(loginUserBo.getPassword()));
        loginUserBo.setPassword(loginUserBo.getPassword());
        MallToken token = new MallToken(loginUserBo.getUsername(), loginUserBo.getPassword(), "admin");
        Subject subject = SecurityUtils.getSubject();
        try{
            subject.login(token);
        }catch (Exception e){
            return BaseRespVo.fail("用户名或密码错误");
        }
        Session session = subject.getSession();

        return BaseRespVo.ok(session.getId());
    }
    @RequestMapping("auth/info")
    public BaseRespVo info(String token, HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        AdminLoginInfoVo loginInfoVo = adminLoginService.getAdminInfo(username,ip);
        return BaseRespVo.ok(loginInfoVo);
    }

    @RequestMapping("auth/logout")
    public BaseRespVo logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return BaseRespVo.ok();
    }

    @RequestMapping("dashboard")
    public BaseRespVo dashboard(){
        DashBoardVo dashBoardVo = adminLoginService.getAllInfo();

        return BaseRespVo.ok(dashBoardVo);
    }

    //@PostMapping("profile/password")
    //public BaseRespVo setPassword(AdminPasswordBo adminPasswordBo);
}

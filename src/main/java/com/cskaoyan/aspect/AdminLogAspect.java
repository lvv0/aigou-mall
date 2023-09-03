package com.cskaoyan.aspect;

import com.cskaoyan.model.bean.Log;
import com.cskaoyan.model.vo.BaseRespVo;
import com.cskaoyan.service.LogService;
import com.cskaoyan.utils.UserInfoUtils;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class AdminLogAspect {
    Log log = new Log();
    Map<String, String> urlToActionMap;
    Map<String, Integer> urlToTypeMap;
    @Autowired
    UserInfoUtils userInfoUtils;


    {
        urlToActionMap = new HashMap<>();
        urlToActionMap.put("/admin/auth/login", "登录");
        urlToActionMap.put("/admin/auth/logout", "退出");
        urlToActionMap.put("/admin/admin/create", "创建管理员");
        urlToActionMap.put("/admin/admin/update", "修改管理员信息");
        urlToActionMap.put("/admin/admin/delete", "删除管理员");

        /*
         * 1 安全操作
         * 2 订单操作
         * 3 其他操作
         * 前端定义的
         */
        urlToTypeMap = new HashMap<>();
        urlToTypeMap.put("/admin/auth/login", 1);
        urlToTypeMap.put("/admin/auth/logout", 1);
        urlToTypeMap.put("/admin/admin/create", 1);
        urlToTypeMap.put("/admin/admin/update", 1);
        urlToTypeMap.put("/admin/admin/delete", 1);
    }

    @Autowired
    LogService logService;

    @Pointcut("execution(com.cskaoyan.model.vo.BaseRespVo com.cskaoyan.controller.AdminController.create(..))")
    public void adminControllerCreatePointcut() {
    }

    @Pointcut("execution(com.cskaoyan.model.vo.BaseRespVo com.cskaoyan.controller.AdminController.delete(..))")
    public void adminControllerDeletePointcut() {
    }

    @Pointcut("execution(com.cskaoyan.model.vo.BaseRespVo com.cskaoyan.controller.AdminController.update(..))")
    public void adminControllerUpdatePointcut() {
    }

    @Pointcut("execution(com.cskaoyan.model.vo.BaseRespVo com.cskaoyan.controller.AuthController.login(..))")
    public void authControllerLoginPointcut() {
    }

    @Pointcut("execution(com.cskaoyan.model.vo.BaseRespVo com.cskaoyan.controller.AuthController.logout(..))")
    public void authControllerLogoutPointcut() {
    }

    @Before("adminControllerDeletePointcut() || adminControllerCreatePointcut() || adminControllerUpdatePointcut() ||" +
            "authControllerLoginPointcut() || authControllerLogoutPointcut()")
    public void before() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        log.setIp(request.getRemoteAddr());
        log.setAction(urlToActionMap.get(request.getRequestURI()));
        log.setType(urlToTypeMap.get(request.getRequestURI()));
        log.setAdmin(userInfoUtils.getUsername());
    }

    @AfterReturning(value = "adminControllerDeletePointcut() || adminControllerCreatePointcut() || adminControllerUpdatePointcut() ||" +
            "authControllerLoginPointcut() || authControllerLogoutPointcut()", returning = "result")
    public void afterReturning(BaseRespVo result) {
        if (result.getErrno() == 0) {
            log.setStatus(true);
        } else {
            log.setStatus(false);
        }
        if (result.getErrno() == 0) {
            log.setResult("");
        } else {
            log.setResult(result.getErrmsg());
        }
    }

    @After("adminControllerDeletePointcut() || adminControllerCreatePointcut() || adminControllerUpdatePointcut() ||" +
            "authControllerLoginPointcut() || authControllerLogoutPointcut()")
    public void after() {
        if (log.getAdmin() == null) {
            log.setAdmin(userInfoUtils.getUsername());
        }
        if (log.getAdmin() == null) {
            log.setAdmin("匿名用户");
        }
        logService.log(log);
    }
}

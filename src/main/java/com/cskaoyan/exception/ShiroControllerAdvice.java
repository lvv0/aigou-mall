package com.cskaoyan.exception;

import com.cskaoyan.model.vo.BaseRespVo;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ShiroControllerAdvice {

    @ExceptionHandler(AuthorizationException.class)
    public BaseRespVo authorizationException(AuthorizationException exception) {
        //exception.printStackTrace();
        return BaseRespVo.fail("没有权限");
    }

}

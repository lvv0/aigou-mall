package com.cskaoyan.utils;


import com.cskaoyan.model.vo.BaseRespVo;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;


public class ValidationUtils {
    public static BaseRespVo dealWithFiledError(BindingResult bindingResult){

        if(bindingResult.hasFieldErrors()){
            FieldError fieldError = bindingResult.getFieldError();
            //请求参数
            String filedName = fieldError.getField();
            //被拒绝的值
            Object rejectedValue = fieldError.getRejectedValue();
            //返回信息
            String defaultMessage = fieldError.getDefaultMessage();
            return BaseRespVo.fail(defaultMessage);
        }
        return BaseRespVo.ok();
    }

    public static BaseRespVo dealWithValueError(BindingResult bindingResult){

        if(bindingResult.hasFieldErrors()){
            FieldError fieldError = bindingResult.getFieldError();
            String defaultMessage = fieldError.getDefaultMessage();
            return BaseRespVo.fail(defaultMessage);
        }
        return null;
    }
}

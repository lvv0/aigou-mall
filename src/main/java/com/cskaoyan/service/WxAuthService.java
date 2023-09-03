package com.cskaoyan.service;

import com.cskaoyan.model.bo.WxAuthRegisterBo;
import com.cskaoyan.model.bo.WxAuthResetBo;
import com.cskaoyan.model.bo.WxLoginBo;
import com.cskaoyan.model.vo.WxLoginVo;

/**
 * @Name : WxAuthService.java
 * @Time : 2021/8/14 11:04
 * @Author : Ashe
 * @Software : IntelliJ IDEA
 */
public interface WxAuthService {
    WxLoginVo login(WxLoginBo wxLoginBo);

    int register(WxAuthRegisterBo wxAuthRegisterBo);

    boolean reset(WxAuthResetBo wxAuthResetBo);
}

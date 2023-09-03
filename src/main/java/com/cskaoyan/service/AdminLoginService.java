package com.cskaoyan.service;

import com.cskaoyan.model.vo.AdminLoginInfoVo;
import com.cskaoyan.model.vo.DashBoardVo;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author ：lww
 * @description：
 * @date ：2021/8/11 19:30
 */

public interface AdminLoginService {
    DashBoardVo getAllInfo();
    AdminLoginInfoVo getAdminInfo(String username,String ip);


}

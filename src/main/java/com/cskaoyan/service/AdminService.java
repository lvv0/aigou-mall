package com.cskaoyan.service;

import com.cskaoyan.model.bean.Admin;
import com.cskaoyan.model.bo.BaseParamBo;
import com.cskaoyan.model.vo.AdminVo;
import com.cskaoyan.model.vo.BaseRespData;

public interface AdminService {
    BaseRespData listAdmin(BaseParamBo baseParamBo, String username);

    Integer delete(Admin admin);

    AdminVo create(Admin admin);

    AdminVo update(Admin admin);
}

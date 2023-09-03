package com.cskaoyan.service;

import com.cskaoyan.model.bean.Ad;
import com.cskaoyan.model.bo.BaseParamBo;
import com.cskaoyan.model.vo.BaseRespData;

/**
 * @Name : AdService.java
 * @Time : 2021/8/11 21:14
 * @Author : Ashe
 * @Software : IntelliJ IDEA
 */
public interface AdService {

    BaseRespData query(String name, String content, BaseParamBo param);

    Ad update(Ad ad);

    Ad create(Ad ad);

    void delete(Ad ad);

}

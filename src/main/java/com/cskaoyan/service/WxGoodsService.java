package com.cskaoyan.service;

import com.cskaoyan.model.bo.BaseParamBo;
import com.cskaoyan.model.bo.WxGoodsParamBo;
import com.cskaoyan.model.vo.*;

/**
 * @Name : WxGoodsService.java
 * @Time : 2021/8/14 22:49
 * @Author : Ashe
 * @Software : IntelliJ IDEA
 */
public interface WxGoodsService {

    WxGoodsCountVo count();

    WxGoodsListVo list(String keyword, WxGoodsParamBo param, Integer categoryId, Integer brandId, Boolean isNew, Boolean isHot);

    WxGoodsDetailVo detail(Integer id);

    WxGoodsRelatedVo related(Integer id);

    WxGoodsCategoryVo category(Integer id);

}

package com.cskaoyan.service;

import com.cskaoyan.model.vo.WxCategoryVo;

public interface WxCategoryService {
    WxCategoryVo getCategoryIndex();
    WxCategoryVo getCurrentCategory(Integer id);

}

package com.cskaoyan.service;

import com.cskaoyan.model.bean.Brand;
import com.cskaoyan.model.vo.WxBrandListVo;

public interface WxBrandService {
    WxBrandListVo getBrandList(Integer page,Integer size);
    Brand getBrandDetail(Integer id);
}

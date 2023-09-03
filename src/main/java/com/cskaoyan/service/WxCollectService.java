package com.cskaoyan.service;

import com.cskaoyan.model.bean.Collect;
import com.cskaoyan.model.vo.WxCollectListVo;

import java.util.Map;

public interface WxCollectService {
    Map<String, String> addOrDelete(Collect collect);

    WxCollectListVo list(Integer type, Integer page, Integer size);
}

package com.cskaoyan.service;

import com.cskaoyan.model.vo.WxFootPrintVo;


import java.util.List;

public interface WxFootPrintService {
    WxFootPrintVo list(Integer page, Integer size);

    boolean delete(Integer id);
}

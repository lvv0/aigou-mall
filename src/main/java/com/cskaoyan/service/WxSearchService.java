package com.cskaoyan.service;

import com.cskaoyan.model.vo.WxSearchVo;

import java.util.List;

public interface WxSearchService {
    WxSearchVo getSearchIndex(String username);
    List<String> getKeyHelp(String keyword);
    int clearSearchHistory(String username);
}

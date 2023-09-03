package com.cskaoyan.controller;

import com.cskaoyan.model.vo.BaseRespVo;
import com.cskaoyan.model.vo.WxSearchVo;
import com.cskaoyan.service.WxSearchService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ：lww
 * @description：
 * @date ：2021/8/14 22:08
 */
@RestController
@RequestMapping("wx/search")
public class WxSearchController {
    @Autowired
    WxSearchService wxSearchService;
    @RequestMapping("index")
    public BaseRespVo getSearchIndex(){
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        WxSearchVo searchIndex = wxSearchService.getSearchIndex(username);
        return BaseRespVo.ok(searchIndex);
    }

    @RequestMapping("helper")
    public BaseRespVo getSearchHelper(String keyword){
        List<String> keyHelp = wxSearchService.getKeyHelp(keyword);
        return BaseRespVo.ok(keyHelp);
    }
    @RequestMapping("clearhistory")
    public BaseRespVo clearHistory(){
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        wxSearchService.clearSearchHistory(username);
        return BaseRespVo.ok();
    }
}

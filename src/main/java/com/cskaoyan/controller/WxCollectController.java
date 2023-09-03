package com.cskaoyan.controller;

import com.cskaoyan.model.bean.Collect;
import com.cskaoyan.model.vo.BaseRespVo;
import com.cskaoyan.model.vo.WxCollectListVo;
import com.cskaoyan.service.WxCollectService;
import com.cskaoyan.utils.UserInfoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("wx/collect")
public class WxCollectController {
    @Autowired
    WxCollectService wxCollectService;

    @Autowired
    UserInfoUtils userInfoUtils;

    @PostMapping("addordelete")
    public BaseRespVo addOrDelete(@RequestBody Collect collect) {
        if (userInfoUtils.getUsername() == null) {
            return BaseRespVo.fail5();
        }
        Map<String, String> map = wxCollectService.addOrDelete(collect);
        if (map == null) {
            return BaseRespVo.fail("something wrong");
        }
        return BaseRespVo.ok(map);
    }

    @GetMapping("list")
    public BaseRespVo list(Integer type, Integer page, Integer size) {
        WxCollectListVo wxCollectListVo = wxCollectService.list(type, page, size);
        if (wxCollectListVo == null) {
            return BaseRespVo.fail("something wrong");
        }
        return BaseRespVo.ok(wxCollectListVo);
    }



}

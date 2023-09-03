package com.cskaoyan.controller;

import com.cskaoyan.model.bo.WxFootPrintDeleteBo;
import com.cskaoyan.model.vo.BaseRespVo;
import com.cskaoyan.model.vo.WxFootPrintVo;
import com.cskaoyan.service.WxFootPrintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("wx/footprint")
public class WxFootPrintController {

    @Autowired
    WxFootPrintService wxFootPrintService;

    @RequestMapping("list")
    public BaseRespVo list(Integer page,Integer size){
        WxFootPrintVo wxFootPrintVo = wxFootPrintService.list(page,size);
        if (wxFootPrintVo != null){
            return BaseRespVo.ok(wxFootPrintVo);
        }else return BaseRespVo.fail("服务器繁忙！");
    }

    @RequestMapping("delete")
    public BaseRespVo delete(@RequestBody WxFootPrintDeleteBo wxFootPrintDeleteBo){
        if (wxFootPrintService.delete(wxFootPrintDeleteBo.getId())){
            return BaseRespVo.ok();
        }else {
            return BaseRespVo.fail("服务器繁忙！");
        }
    }
}

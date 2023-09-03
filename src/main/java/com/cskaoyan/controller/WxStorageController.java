package com.cskaoyan.controller;

import com.cskaoyan.model.vo.BaseRespVo;
import com.cskaoyan.model.vo.GoodsStotagePicVo;
import com.cskaoyan.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @Name : WxStorageController.java
 * @Time : 2021/8/16 15:15
 * @Author : Ashe
 * @Software : IntelliJ IDEA
 */
@RestController
@RequestMapping("wx/storage")
public class WxStorageController {

    @Value("${server.address}")
    String address;

    @Value("${server.port}")
    String port;

    @Autowired
    GoodsService goodsService;

    @RequestMapping("upload")
    public BaseRespVo upload(MultipartFile file, HttpServletRequest request){
        String netPath = "http://" + address + ":" + port;
        GoodsStotagePicVo goodsStotagePicVo = goodsService.create(file,netPath);
        if (goodsStotagePicVo != null){
            return BaseRespVo.ok(goodsStotagePicVo);
        }else return BaseRespVo.fail();
    }

}

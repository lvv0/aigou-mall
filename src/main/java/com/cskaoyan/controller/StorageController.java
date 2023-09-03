package com.cskaoyan.controller;

import com.cskaoyan.model.bean.Storage;
import com.cskaoyan.model.bo.BaseParamBo;
import com.cskaoyan.model.vo.BaseRespData;
import com.cskaoyan.model.vo.BaseRespVo;
import com.cskaoyan.model.vo.GoodsStotagePicVo;
import com.cskaoyan.service.GoodsService;
import com.cskaoyan.service.StorageService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("admin/storage")
public class StorageController {

    @Value("${server.address}")
    String address;

    @Value("${server.port}")
    String port;

    @Autowired
    GoodsService goodsService;

    @Autowired
    StorageService storageService;

    @RequiresPermissions(value = {"*","admin:storage:create"},logical = Logical.OR)
    @RequestMapping("create")
    public BaseRespVo create(MultipartFile file, HttpServletRequest request){
        String netPath = "http://" + address + ":" + port;
        GoodsStotagePicVo goodsStotagePicVo = goodsService.create(file,netPath);
        if (goodsStotagePicVo != null){
            return BaseRespVo.ok(goodsStotagePicVo);
        }else return BaseRespVo.fail();
    }

    @RequiresPermissions(value = {"*","admin:storage:delete"},logical = Logical.OR)
    @PostMapping("delete")
    public BaseRespVo delete(@RequestBody Storage storage) {
        int code = storageService.delete(storage);
        if (code != 200) {
            return BaseRespVo.fail("删除失败");
        }
        return BaseRespVo.ok();
    }

    @RequiresPermissions(value = {"*","admin:storage:list"},logical = Logical.OR)
    @GetMapping("list")
    public BaseRespVo list(BaseParamBo baseParamBo, String name, String key) {
        BaseRespData baseRespData = storageService.list(baseParamBo, name, key);
        return BaseRespVo.ok(baseRespData);
    }

    @RequiresPermissions(value = {"*","admin:storage:update"},logical = Logical.OR)
    @PostMapping("update")
    public BaseRespVo update(@RequestBody Storage storage) {
        Storage storage1 = storageService.update(storage);
        if (storage1 == null) {
            return BaseRespVo.fail("修改失败");
        }
        return BaseRespVo.ok(storage1);
    }
}

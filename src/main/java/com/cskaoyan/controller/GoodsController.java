package com.cskaoyan.controller;

import com.cskaoyan.model.bean.Goods;
import com.cskaoyan.model.bo.GoodsCreateBo;
import com.cskaoyan.model.bo.GoodsListBo;
import com.cskaoyan.model.bo.GoodsUpdateBo;
import com.cskaoyan.model.vo.BaseRespData;
import com.cskaoyan.model.vo.BaseRespVo;
import com.cskaoyan.model.vo.GoodsCatAndBrandVo;
import com.cskaoyan.model.vo.GoodsDetailVo;
import com.cskaoyan.service.GoodsService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin/goods")
public class GoodsController {

    @Autowired
    GoodsService goodsService;

    @RequiresPermissions(value = {"*","admin:goods:list"},logical = Logical.OR)
    @RequestMapping("list")
    public BaseRespVo list(GoodsListBo goodsListBo){
        BaseRespData baseRespData = goodsService.list(goodsListBo);
        return BaseRespVo.ok(baseRespData);
    }

    @RequestMapping("catAndBrand")
    public BaseRespVo catAndBrand(){
        GoodsCatAndBrandVo goodsCatAndBrandVo = goodsService.catAndBrand();
        return BaseRespVo.ok(goodsCatAndBrandVo);
    }

    @RequiresPermissions(value = {"*","admin:goods:create"},logical = Logical.OR)
    @RequestMapping("create")
    public BaseRespVo create(@RequestBody GoodsCreateBo goodsCreateBo){
        if (goodsService.createGoods(goodsCreateBo)){
            return BaseRespVo.ok();
        }else {
            return BaseRespVo.fail("服务器繁忙");
        }
    }

    @RequiresPermissions(value = {"*","admin:goods:detail"},logical = Logical.OR)
    @RequestMapping("detail")
    public BaseRespVo detail(Integer id){
        GoodsDetailVo goodsDetailVo = goodsService.detail(id);
        if (goodsDetailVo != null){
            return BaseRespVo.ok(goodsDetailVo);
        }else {
            return BaseRespVo.fail();
        }
    }

    @RequiresPermissions(value = {"*","admin:goods:update"},logical = Logical.OR)
    @RequestMapping("update")
    public BaseRespVo update(@RequestBody GoodsUpdateBo goodsUpdateBo){
        if (goodsService.update(goodsUpdateBo)){
            return BaseRespVo.ok();
        }else return BaseRespVo.fail("服务器繁忙！");
    }

    @RequiresPermissions(value = {"*","admin:goods:delete"},logical = Logical.OR)
    @RequestMapping("delete")
    public BaseRespVo delete(@RequestBody Goods goods){
        if (goodsService.delete(goods)){
            return BaseRespVo.ok();
        }else return BaseRespVo.fail("服务器繁忙！");
    }
}

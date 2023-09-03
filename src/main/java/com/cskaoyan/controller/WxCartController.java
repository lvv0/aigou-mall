package com.cskaoyan.controller;

import com.cskaoyan.model.bo.WxCartAddBo;
import com.cskaoyan.model.bo.WxCartCheckedBo;
import com.cskaoyan.model.bo.WxCartUpdateBo;
import com.cskaoyan.model.vo.BaseRespVo;
import com.cskaoyan.model.vo.WxCartCheckOutVo;
import com.cskaoyan.model.vo.WxCartIndexVo;
import com.cskaoyan.service.WxCartService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("wx/cart")
public class WxCartController {
    @Autowired
    WxCartService cartService;

    private String getUsername() {
        Subject subject = SecurityUtils.getSubject();
        String principal = (String) subject.getPrincipal();
        return principal;
    }

    @GetMapping("index")
    public BaseRespVo index() {
        String username = getUsername();
        WxCartIndexVo wxCartIndexVo = cartService.index(username, 0);
        if (wxCartIndexVo == null) {
            return BaseRespVo.fail("something wrong");
        }
        return BaseRespVo.ok(wxCartIndexVo);
    }

    @PostMapping("checked")
    public BaseRespVo checked(@RequestBody WxCartCheckedBo wxCartCheckedBo) {
        WxCartIndexVo wxCartIndexVo = cartService.checked(wxCartCheckedBo, getUsername());
        if (wxCartIndexVo == null) {
            return BaseRespVo.fail("something wrong");
        }
        return BaseRespVo.ok(wxCartIndexVo);
    }

    @PostMapping("update")
    public BaseRespVo update(@RequestBody WxCartUpdateBo wxCartUpdateBo) {
        int code = cartService.update(wxCartUpdateBo);
        if (code != 200) {
            return BaseRespVo.fail("更新失败");
        }
        return BaseRespVo.ok();
    }

    @PostMapping("delete")
    public BaseRespVo delete(@RequestBody Map<String, Integer[]> map) {
        WxCartIndexVo wxCartIndexVo = cartService.delete(map.get("productIds"), getUsername());
        if (wxCartIndexVo == null) {
            return BaseRespVo.fail("删除失败");
        }
        return BaseRespVo.ok(wxCartIndexVo);
    }

    @PostMapping("add")
    public BaseRespVo add(@RequestBody WxCartAddBo wxCartAddBo) {
        String username = getUsername();
        if (username == null) {
            return BaseRespVo.fail5();
        }
        Integer goodsNum = cartService.add(wxCartAddBo, username);
        if (goodsNum == null) {
            return BaseRespVo.fail("添加失败");
        }
        return BaseRespVo.ok(goodsNum);
    }

    @PostMapping("fastadd")
    public BaseRespVo fastAdd(@RequestBody WxCartAddBo wxCartAddBo) {
        String username = getUsername();
        if (username == null) {
            return BaseRespVo.fail5();
        }
        Integer cartId = cartService.fastAdd(wxCartAddBo, username);
        if (cartId == null) {
            return BaseRespVo.fail("添加失败");
        }
        return BaseRespVo.ok(cartId);
    }

    @GetMapping("checkout")
    public BaseRespVo checkOut(Integer cartId, Integer addressId, Integer couponId, Integer grouponRulesId) {
        WxCartCheckOutVo wxCartCheckOutVo = cartService.checkOut(cartId, addressId, couponId, grouponRulesId, getUsername());
        if (wxCartCheckOutVo == null) {
            return BaseRespVo.fail("something wrong");
        }
        return BaseRespVo.ok(wxCartCheckOutVo);
    }

    @GetMapping("goodscount")
    public BaseRespVo goodsCount() {
        String username = getUsername();
        if (username == null) {
            return BaseRespVo.ok(0);
        }
        Integer goodsCount = cartService.goodsCount(username);
        if (goodsCount == null) {
            return BaseRespVo.fail("something wrong");
        }
        return BaseRespVo.ok(goodsCount);
    }
}

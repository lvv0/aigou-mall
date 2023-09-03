package com.cskaoyan.controller;

import com.cskaoyan.model.bean.Address;
import com.cskaoyan.model.bean.Region;
import com.cskaoyan.model.vo.BaseRespVo;
import com.cskaoyan.model.vo.WxAddressDetailVo;
import com.cskaoyan.model.vo.WxAddressVo;
import com.cskaoyan.service.WxAddressService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author ：lww
 * @description：
 * @date ：2021/8/14 14:08
 */
@RestController
@RequestMapping("wx")
public class WxAddressController {
    @Autowired
    WxAddressService wxAddressService;

    @RequestMapping("address/list")
    public BaseRespVo getUserAddressList(){
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        List<WxAddressVo> addressVoList = wxAddressService.getWxAddress(username);
        return BaseRespVo.ok(addressVoList);
    }

    @RequestMapping("address/detail")
    public BaseRespVo getAddressDetail(Integer id){
        WxAddressDetailVo wxAddressDetail = wxAddressService.getWxAddressDetail(id);
        return BaseRespVo.ok(wxAddressDetail);
    }

    @RequestMapping("address/save")
    public BaseRespVo updateAddressDetail(@RequestBody Address address){
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        if(address.getId()==0){
            int id = wxAddressService.updateWxAddressDetail(address,username);
            return BaseRespVo.ok(id);
        }
        wxAddressService.updateWxAddressDetail(address,username);
        return BaseRespVo.ok(address.getId());
    }

    @RequestMapping("address/delete")
    public BaseRespVo deleteAddress(@RequestBody Map map){
        Integer id = (Integer) map.get("id");
        int i = wxAddressService.deleteAddress(id);
        return BaseRespVo.ok();
    }

    @RequestMapping("region/list")
    public BaseRespVo regionList(Integer pid){
        List<Region> regions = wxAddressService.regionList(pid);
        if (regions != null){
            return BaseRespVo.ok(regions);
        }else return BaseRespVo.fail("服务器繁忙!");
    }


}

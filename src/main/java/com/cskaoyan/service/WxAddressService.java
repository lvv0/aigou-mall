package com.cskaoyan.service;

import com.cskaoyan.model.bean.Address;
import com.cskaoyan.model.bean.Region;
import com.cskaoyan.model.vo.WxAddressDetailVo;
import com.cskaoyan.model.vo.WxAddressVo;

import java.util.List;

/**
 * @author ：lww
 * @description：
 * @date ：2021/8/14 14:14
 */
public interface WxAddressService {
    List<WxAddressVo> getWxAddress(String username);
    WxAddressDetailVo getWxAddressDetail(Integer id);
    int updateWxAddressDetail(Address address,String username);
    int deleteAddress(Integer id);

    List<Region> regionList(Integer pid);
}

package com.cskaoyan.service;

import com.cskaoyan.mapper.AddressMapper;
import com.cskaoyan.mapper.RegionMapper;
import com.cskaoyan.mapper.UserMapper;
import com.cskaoyan.model.bean.Address;
import com.cskaoyan.model.bean.AddressExample;
import com.cskaoyan.model.bean.Region;
import com.cskaoyan.model.bean.RegionExample;
import com.cskaoyan.model.vo.WxAddressDetailVo;
import com.cskaoyan.model.vo.WxAddressVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author ：lww
 * @description：
 * @date ：2021/8/14 14:15
 */
@Service
@Transactional
public class WxAddressServiceImpl implements WxAddressService {
    @Autowired
    AddressMapper addressMapper;
    @Autowired
    RegionMapper regionMapper;
    @Autowired
    UserMapper userMapper;

    /**
     * 用户的收获地址
     * @param username
     * @return
     */
    @Override
    public List<WxAddressVo> getWxAddress(String username) {
        int userId = userMapper.getIdByName(username);
        List<WxAddressDetailVo> wxAddressDetailVos = addressMapper.selectWxAddressInfo(userId);
        List<WxAddressVo> wxAddressVos=new ArrayList<>();
        for (WxAddressDetailVo addressDetailVo : wxAddressDetailVos) {
            String provinceName = regionMapper.selectNameById(addressDetailVo.getProvinceId());
            String cityName = regionMapper.selectNameById(addressDetailVo.getCityId());
            String areaName = regionMapper.selectNameById(addressDetailVo.getAreaId());
            String detailedAddress=provinceName+cityName+areaName+" "+addressDetailVo.getAddress();
            WxAddressVo wxAddressVo = new WxAddressVo(addressDetailVo.getIsDefault(), detailedAddress, addressDetailVo.getName(), addressDetailVo.getMobile(), addressDetailVo.getId());
            wxAddressVos.add(wxAddressVo);
        }
        for (int i = 0; i < wxAddressVos.size(); i++) {
            if(wxAddressVos.get(i).getIsDefault()==true){
                Collections.swap(wxAddressVos,i,0);
                break;
            }
        }
        return wxAddressVos;
    }

    /**
     * 地址详情
     * @param id
     * @return
     */
    @Override
    public WxAddressDetailVo getWxAddressDetail(Integer id) {
        WxAddressDetailVo addressDetailVo = addressMapper.selectWxAddressDetail(id);
        String provinceName = regionMapper.selectNameById(addressDetailVo.getProvinceId());
        String cityName = regionMapper.selectNameById(addressDetailVo.getCityId());
        String areaName = regionMapper.selectNameById(addressDetailVo.getAreaId());
        addressDetailVo.setProvinceName(provinceName);
        addressDetailVo.setCityName(cityName);
        addressDetailVo.setAreaName(areaName);
        return addressDetailVo;
    }

    /**
     * 保存地址信息
     * @param address
     * @return
     */
    @Override
    public int updateWxAddressDetail(Address address,String username) {
        int userId = userMapper.getIdByName(username);
        AddressExample addressExample = new AddressExample();
        AddressExample.Criteria addCriteria = addressExample.createCriteria();
        Date date = new Date();
        if(address.getIsDefault()==true){
            Address addr = new Address();
            addr.setIsDefault(false);
            addr.setUserId(address.getUserId());
            addCriteria.andIsDefaultEqualTo(true).andDeletedEqualTo(false);
            addressMapper.updateByExampleSelective(addr,addressExample);
        }

        addressExample.clear();
        AddressExample.Criteria criteria = addressExample.createCriteria();

        address.setUpdateTime(date);
        if(address.getId()==0){
            address.setId(null);
            address.setAddTime(date);
            address.setDeleted(false);
            address.setUserId(userId);
            addressMapper.insert(address);
            return address.getId();
        }
        criteria.andIdEqualTo(address.getId());
        int i = addressMapper.updateByExampleSelective(address, addressExample);
        return i;
    }

    /**
     * 删除地址
     * @param id
     * @return
     */
    @Override
    public int deleteAddress(Integer id) {
        Address address = new Address();
        address.setId(id);
        address.setUpdateTime(new Date());
        address.setDeleted(true);
        AddressExample addressExample = new AddressExample();
        AddressExample.Criteria criteria = addressExample.createCriteria();
        criteria.andIdEqualTo(id);
        int i = addressMapper.updateByExampleSelective(address, addressExample);
        return i;
    }

    @Override
    public List<Region> regionList(Integer pid) {
        RegionExample regionExample = new RegionExample();
        RegionExample.Criteria criteria = regionExample.createCriteria();
        criteria.andPidEqualTo(pid);
        List<Region> regions = regionMapper.selectByExample(regionExample);
        if (regions != null){
            return regions;
        }
        return null;
    }
}

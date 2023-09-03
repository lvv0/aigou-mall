package com.cskaoyan.mapper;

import com.cskaoyan.model.bean.Address;
import com.cskaoyan.model.bean.AddressExample;
import com.cskaoyan.model.bo.BaseParamBo;
import com.cskaoyan.model.vo.UserAddressVo;
import com.cskaoyan.model.vo.WxAddressDetailVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AddressMapper {
    long countByExample(AddressExample example);

    int deleteByExample(AddressExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Address record);

    int insertSelective(Address record);

    List<Address> selectByExample(AddressExample example);

    Address selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Address record, @Param("example") AddressExample example);

    int updateByExample(@Param("record") Address record, @Param("example") AddressExample example);

    int updateByPrimaryKeySelective(Address record);

    int updateByPrimaryKey(Address record);


    List<UserAddressVo> selectAddressInfo(@Param("userId") Integer userId, @Param("username") String username, @Param("paramBo") BaseParamBo paramBo);

    List<WxAddressDetailVo> selectWxAddressInfo(@Param("userId") Integer userId);

    WxAddressDetailVo selectWxAddressDetail(@Param("id") Integer id);

}
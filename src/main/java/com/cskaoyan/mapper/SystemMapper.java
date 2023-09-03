package com.cskaoyan.mapper;

import com.cskaoyan.model.bean.System;
import com.cskaoyan.model.bean.SystemExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SystemMapper {
    long countByExample(SystemExample example);

    int deleteByExample(SystemExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(System record);

    int insertSelective(System record);

    List<System> selectByExample(SystemExample example);

    System selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") System record, @Param("example") SystemExample example);

    int updateByExample(@Param("record") System record, @Param("example") SystemExample example);

    int updateByPrimaryKeySelective(System record);

    int updateByPrimaryKey(System record);

    String getValueByName(String keyName);

    int updateValueByName(@Param("keyName") String keyName, @Param("keyValue") String keyValue, @Param("updateTime") String updateTime);


    List<String> getConfigLikeName(@Param("keyName") String keyName);

}
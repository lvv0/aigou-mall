package com.cskaoyan.mapper;

import com.cskaoyan.model.bean.Admin;
import com.cskaoyan.model.bean.AdminExample;
import com.cskaoyan.model.bo.BaseParamBo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminMapper {
    long countByExample(AdminExample example);

    int deleteByExample(AdminExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Admin record);

    int insertSelective(Admin record);

    List<Admin> selectByExample(AdminExample example);

    Admin selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Admin record, @Param("example") AdminExample example);

    int updateByExample(@Param("record") Admin record, @Param("example") AdminExample example);

    int updateByPrimaryKeySelective(Admin record);

    int updateByPrimaryKey(Admin record);

    List<Admin> selectAdmin(@Param("baseParamBo") BaseParamBo baseParamBo, @Param("username") String username);

    String getCredentialByName(String username);

    Integer[] selectRoleIdByUserName(String username);
}
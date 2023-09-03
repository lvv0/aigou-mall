package com.cskaoyan.mapper;

import com.cskaoyan.model.bean.Brand;
import com.cskaoyan.model.bean.BrandExample;
import com.cskaoyan.model.bo.BrandListBo;
import com.cskaoyan.model.vo.WxBrandVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BrandMapper {
    long countByExample(BrandExample example);

    int deleteByExample(BrandExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Brand record);

    int insertSelective(Brand record);

    List<Brand> selectByExample(BrandExample example);

    Brand selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Brand record, @Param("example") BrandExample example);

    int updateByExample(@Param("record") Brand record, @Param("example") BrandExample example);

    int updateByPrimaryKeySelective(Brand record);

    int updateByPrimaryKey(Brand record);

    List<WxBrandVo> selectBrandVo();

    //----------------------------------- 后一个参数用于分页，1为分页，0为查询数量
//    List<Brand> selectBrandListByParam(@Param("bo") BrandListBo bo, @Param("isPage") int isPage);

}
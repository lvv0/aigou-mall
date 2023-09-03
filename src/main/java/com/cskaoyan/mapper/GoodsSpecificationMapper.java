package com.cskaoyan.mapper;

import com.cskaoyan.model.bean.GoodsSpecification;
import com.cskaoyan.model.bean.GoodsSpecificationExample;
import com.cskaoyan.model.vo.WxGoodsDetailQueryVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsSpecificationMapper {
    long countByExample(GoodsSpecificationExample example);

    int deleteByExample(GoodsSpecificationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(GoodsSpecification record);

    int insertSelective(GoodsSpecification record);

    List<GoodsSpecification> selectByExample(GoodsSpecificationExample example);

    GoodsSpecification selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") GoodsSpecification record, @Param("example") GoodsSpecificationExample example);

    int updateByExample(@Param("record") GoodsSpecification record, @Param("example") GoodsSpecificationExample example);

    int updateByPrimaryKeySelective(GoodsSpecification record);

    int updateByPrimaryKey(GoodsSpecification record);

    int updateDeleted(Integer id);

    List<WxGoodsDetailQueryVo> selectSpecificationIdByGoodsId(Integer id);

}
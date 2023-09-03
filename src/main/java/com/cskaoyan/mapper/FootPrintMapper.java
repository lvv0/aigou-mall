package com.cskaoyan.mapper;

import com.cskaoyan.model.bean.FootPrint;
import com.cskaoyan.model.bean.FootPrintExample;
import com.cskaoyan.model.vo.WxFootPrintListVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FootPrintMapper {
    long countByExample(FootPrintExample example);

    int deleteByExample(FootPrintExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(FootPrint record);

    int insertSelective(FootPrint record);

    List<FootPrint> selectByExample(FootPrintExample example);

    FootPrint selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") FootPrint record, @Param("example") FootPrintExample example);

    int updateByExample(@Param("record") FootPrint record, @Param("example") FootPrintExample example);

    int updateByPrimaryKeySelective(FootPrint record);

    int updateByPrimaryKey(FootPrint record);

    List<WxFootPrintListVo> selectGoodsIdAndAddTimeByUserId(Integer userId);
}
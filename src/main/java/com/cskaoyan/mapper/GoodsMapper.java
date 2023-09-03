package com.cskaoyan.mapper;

import com.cskaoyan.model.bean.Goods;
import com.cskaoyan.model.bean.GoodsExample;
import com.cskaoyan.model.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsMapper {
    long countByExample(GoodsExample example);

    int deleteByExample(GoodsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Goods record);

    int insertSelective(Goods record);

    List<Goods> selectByExampleWithBLOBs(GoodsExample example);

    List<Goods> selectByExample(GoodsExample example);

    Goods selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Goods record, @Param("example") GoodsExample example);

    int updateByExampleWithBLOBs(@Param("record") Goods record, @Param("example") GoodsExample example);

    int updateByExample(@Param("record") Goods record, @Param("example") GoodsExample example);

    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKeyWithBLOBs(Goods record);

    int updateByPrimaryKey(Goods record);

    List<CategoryListVo> selectCateGoryIdAndName();

    List<ChildrenVo> selectCateGoryIdAndNameByPid(Integer value);

    List<GoodsBrandListVo> selectBrandIdAndName();

    int insertStotage(@Param("goodsStotagePicVo") GoodsStotagePicVo goodsStotagePicVo);

    List<Integer> selectCateGoryIdByGoodsId(Integer id);

    int updateDeleted(Integer id);

    List<GoodsRowsVo> getGoodsStat();

    WxFootPrintListVo selectGoodsInfoByGoodsId(Integer goodsId);

    List<Goods> selectByBrandId(Integer brandId);

}
package com.cskaoyan.mapper;

import com.cskaoyan.model.bean.GoodsProduct;
import com.cskaoyan.model.bean.GoodsProductExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsProductMapper {
    long countByExample(GoodsProductExample example);

    int deleteByExample(GoodsProductExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(GoodsProduct record);

    int insertSelective(GoodsProduct record);

    List<GoodsProduct> selectByExample(GoodsProductExample example);

    GoodsProduct selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") GoodsProduct record, @Param("example") GoodsProductExample example);

    int updateByExample(@Param("record") GoodsProduct record, @Param("example") GoodsProductExample example);

    int updateByPrimaryKeySelective(GoodsProduct record);

    int updateByPrimaryKey(GoodsProduct record);

    int updateDeleted(Integer id);

    // 回滚商品数量
    int updateGoodsCountById(@Param("goodsId") Integer goodsId, @Param("number") Short number);

    List<GoodsProduct> selectGoodsProductByGoodsId(Integer goodsId);

    int updateDeletedByGoodsId(Integer goodsId);
}
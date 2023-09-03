package com.cskaoyan.mapper;

import com.cskaoyan.model.bean.Category;
import com.cskaoyan.model.bean.CategoryExample;
import com.cskaoyan.model.bo.CategoryBoChildren;
import com.cskaoyan.model.vo.CategoryL1;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CategoryMapper {
    long countByExample(CategoryExample example);

    int deleteByExample(CategoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    int insertSelective(Category record);

    List<Category> selectByExample(CategoryExample example);

    Category selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Category record, @Param("example") CategoryExample example);

    int updateByExample(@Param("record") Category record, @Param("example") CategoryExample example);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);

    // 查询所有L1的类目
    List<CategoryL1> selectL1();

    List<Integer> selectL2ByL1Id(int id);

    Integer selectL1ByL2Id(Integer categoryId);

}
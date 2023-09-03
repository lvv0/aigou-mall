package com.cskaoyan.service;

import com.cskaoyan.mapper.CategoryMapper;
import com.cskaoyan.model.bean.Category;
import com.cskaoyan.model.bean.CategoryExample;
import com.cskaoyan.model.bean.SearchHistory;
import com.cskaoyan.model.bean.SearchHistoryExample;
import com.cskaoyan.model.vo.WxCategoryVo;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：lww
 * @description：
 * @date ：2021/8/14 20:32
 */
@Service
public class WxCategoryServiceImpl implements WxCategoryService {
    @Autowired
    CategoryMapper categoryMapper;

    /**
     * 商品分类
     * @return
     */
    @Override
    public WxCategoryVo getCategoryIndex() {
        CategoryExample categoryExample = new CategoryExample();
        CategoryExample.Criteria criteria = categoryExample.createCriteria();
        criteria.andPidEqualTo(0);
        criteria.andDeletedEqualTo(false);
        //全部分类
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);
        Category currentCategory = new Category();
        int id=-1;
        if(categoryList.size()>0){
            //当前分类
            currentCategory = categoryList.get(0);
            id=currentCategory.getId();
        }
        categoryExample.clear();
        CategoryExample.Criteria subCriteria=categoryExample.createCriteria();

        subCriteria.andDeletedEqualTo(false);
        subCriteria.andPidEqualTo(id);

        List<Category> subCategoryList=categoryMapper.selectByExample(categoryExample);
        WxCategoryVo wxCategoryVo = new WxCategoryVo(categoryList, currentCategory, subCategoryList);


        return wxCategoryVo;
    }

    /**
     * 当前分类信息
     * @param id
     * @return
     */
    @Override
    public WxCategoryVo getCurrentCategory(Integer id) {
        CategoryExample categoryExample = new CategoryExample();
        CategoryExample.Criteria criteria = categoryExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        Category category = categoryMapper.selectByPrimaryKey(id);
        categoryExample.clear();
        CategoryExample.Criteria subCriteria=categoryExample.createCriteria();
        subCriteria.andDeletedEqualTo(false);
        subCriteria.andPidEqualTo(category.getId());
        List<Category> categories = categoryMapper.selectByExample(categoryExample);
        WxCategoryVo wxCategoryVo = new WxCategoryVo(null, category, categories);
        return wxCategoryVo;
    }


}

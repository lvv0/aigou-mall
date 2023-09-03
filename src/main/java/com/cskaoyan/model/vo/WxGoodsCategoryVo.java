package com.cskaoyan.model.vo;

import com.cskaoyan.model.bean.Category;
import lombok.Data;


import java.util.Date;
import java.util.List;

/**
 * @Name : WxGoodsCategoryVo.java
 * @Time : 2021/8/15 23:07
 * @Author : Ashe
 * @Software : IntelliJ IDEA
 */
@Data
public class WxGoodsCategoryVo {

    private Category currentCategory;

    private Category parentCategory;

    private List<Category> brotherCategory;

}

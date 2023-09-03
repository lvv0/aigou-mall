package com.cskaoyan.model.vo;

import com.cskaoyan.model.bean.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author ：lww
 * @description：TODO
 * @date ：2021/8/14 20:22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WxCategoryVo {
    private List<Category> categoryList;
    private Category currentCategory;
    private List<Category> currentSubCategory;

}

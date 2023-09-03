package com.cskaoyan.service;

import com.cskaoyan.model.bean.Brand;
import com.cskaoyan.model.bean.Issue;
import com.cskaoyan.model.bean.Keyword;
import com.cskaoyan.model.bo.*;
import com.cskaoyan.model.bo.BrandListBo;
import com.cskaoyan.model.bo.CategoryBo;
import com.cskaoyan.model.bo.CategoryBoChildren;
import com.cskaoyan.model.bo.OrderListBo;
import com.cskaoyan.model.vo.*;

import java.util.List;

/**
 * @author huxudong
 * @version 1.0
 * @date 2021/8/11 19:46
 */
public interface MarketService {
    List<RegionVo.DataBean> regionList();

    BrandListVo brandList(BrandListBo bo);

    int deleteBrandById(Integer id);

    int updateBrandById(Brand brands);

    AddBrandVo createBrand(Brand brand);

    List<CategoryBo> categoryList();

    List<CategoryL1> categoryL1List();

    CreateCategoryVo createCategory(CategoryBoChildren children);

    boolean deleteCategoryById(Integer id);

    MarketKeyWordListVo list(MarketKeyWordListBo marketKeyWordListBo);

    Keyword create(MarketKeyWordCreateBo marketKeyWordCreateBo);

    Keyword update(Keyword keyword);

    boolean delete(Keyword keyword);

    boolean updateCategory(CategoryBo bo);

    OrderListVo orderList(OrderListBo orderListBo);

    OrderDetailVo selectOrderDetailById(Integer id);

    boolean orderShip(OrderShipBo bo);

    IssueListVo issueList(Integer page, Integer limit, String question, String sort, String order);

    boolean issueDelete(Integer id);

    Issue issueCreate(IssueCreateBo bo);

    Issue issueUpdate(Issue issue);
}

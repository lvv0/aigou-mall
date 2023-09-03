package com.cskaoyan.service;

import com.cskaoyan.mapper.*;
import com.cskaoyan.model.bean.*;
import com.cskaoyan.model.bo.WxGoodsParamBo;
import com.cskaoyan.model.vo.*;
import com.cskaoyan.utils.UserInfoUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Name : WxGoodsServiceImpl.java
 * @Time : 2021/8/14 22:49
 * @Author : Ashe
 * @Software : IntelliJ IDEA
 */
@Service
public class WxGoodsServiceImpl implements WxGoodsService {

    @Autowired
    UserInfoUtils userInfoUtils;

    @Autowired
    GoodsMapper goodsMapper;

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    BrandMapper brandMapper;

    @Autowired
    CollectMapper collectMapper;

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    GoodsAttributeMapper goodsAttributeMapper;

    @Autowired
    GoodsProductMapper goodsProductMapper;

    @Autowired
    GoodsSpecificationMapper goodsSpecificationMapper;

    @Autowired
    IssueMapper issueMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    SearchHistoryMapper searchHistoryMapper;

    @Autowired
    FootPrintMapper footPrintMapper;

    @Override
    public WxGoodsCountVo count() {

        GoodsExample goodsExample = new GoodsExample();

        GoodsExample.Criteria criteria = goodsExample.createCriteria();

        criteria.andDeletedEqualTo(false);

        long count = goodsMapper.countByExample(goodsExample);

        WxGoodsCountVo wxGoodsCountVo = new WxGoodsCountVo();

        wxGoodsCountVo.setGoodsCount(count);

        return wxGoodsCountVo;
    }

    @Override
    public WxGoodsListVo list(String keyword, WxGoodsParamBo param, Integer categoryId, Integer brandId, Boolean isNew, Boolean isHot) {

        WxGoodsListVo wxGoodsListVo = new WxGoodsListVo();

        if (keyword != null) {

            if (userInfoUtils.getUsername() != null) {

                Integer userId = userInfoUtils.getUserId();

                SearchHistoryExample searchHistoryExample = new SearchHistoryExample();

                SearchHistoryExample.Criteria searchHistoryCriteria = searchHistoryExample.createCriteria();

                searchHistoryCriteria.andDeletedEqualTo(false).andUserIdEqualTo(userId).andKeywordEqualTo(keyword);

                List<SearchHistory> searchHistories = searchHistoryMapper.selectByExample(searchHistoryExample);

                if (searchHistories.size() != 0) {

                    SearchHistory searchHistory = new SearchHistory();

                    Date date = new Date();

                    searchHistory.setUpdateTime(date);

                    searchHistory.setId(searchHistories.get(0).getId());

                    searchHistoryMapper.updateByPrimaryKeySelective(searchHistory);

                } else {

                    SearchHistory searchHistory = new SearchHistory();

                    Date date = new Date();

                    searchHistory.setAddTime(date);

                    searchHistory.setUpdateTime(date);

                    searchHistory.setUserId(userId);

                    searchHistory.setFrom("pc");

                    searchHistory.setKeyword(keyword);

                    searchHistoryMapper.insertSelective(searchHistory);

                }

            }

//            WxGoodsListVo wxGoodsListVo = new WxGoodsListVo();

            PageHelper.startPage(param.getPage(), param.getSize());

            GoodsExample goodsExample = new GoodsExample();

            goodsExample.setOrderByClause(param.getSort() + " " + param.getOrder());

            GoodsExample.Criteria criteria = goodsExample.createCriteria();

            criteria.andDeletedEqualTo(false);

            if (keyword != null && !"".equals(keyword)) {
                criteria.andNameLike("%" + keyword + "%");
            }

            List<Goods> goods = goodsMapper.selectByExample(goodsExample);

            List<WxGoodsListVo.GoodsListBean> goodsList = new ArrayList<>();

            ArrayList<Integer> list = new ArrayList<>();

            List<WxGoodsListVo.FilterCategoryListBean> filterCategoryList = new ArrayList<>();

            for (Goods good : goods) {

                if (!categoryId.equals(0)) {
                    if (categoryId.equals(good.getCategoryId())) {
                        addGoodsList(goodsList, good);
                    }
                } else {
                    addGoodsList(goodsList, good);
                }

                if (!list.contains(good.getCategoryId())) {

                    list.add(good.getCategoryId());

                    WxGoodsListVo.FilterCategoryListBean filterCategoryListBean = new WxGoodsListVo.FilterCategoryListBean();

                    Category category = categoryMapper.selectByPrimaryKey(good.getCategoryId());

                    filterCategoryListBean.setId(category.getId());

                    filterCategoryListBean.setName(category.getName());

                    filterCategoryListBean.setKeywords(category.getKeywords());

                    filterCategoryListBean.setDesc(category.getDesc());

                    filterCategoryListBean.setPid(category.getPid());

                    filterCategoryListBean.setIconUrl(category.getIconUrl());

                    filterCategoryListBean.setPicUrl(category.getPicUrl());

                    filterCategoryListBean.setLevel(category.getLevel());

                    if (category.getSortOrder() != null) {
                        filterCategoryListBean.setSortOrder(category.getSortOrder());
                    }

                    filterCategoryListBean.setAddTime(category.getAddTime());

                    filterCategoryListBean.setUpdateTime(category.getUpdateTime());

                    filterCategoryListBean.setDeleted(category.getDeleted());

                    filterCategoryList.add(filterCategoryListBean);
                }

            }

            PageInfo<WxGoodsListVo.GoodsListBean> pageInfo = new PageInfo<>(goodsList);

            Long count = pageInfo.getTotal();

            wxGoodsListVo.setGoodsList(goodsList);

            wxGoodsListVo.setCount(count);

            wxGoodsListVo.setFilterCategoryList(filterCategoryList);

            return wxGoodsListVo;

        } else if (isNew != null) {

            PageHelper.startPage(param.getPage(), param.getSize());

            GoodsExample goodsExample = new GoodsExample();

            goodsExample.setOrderByClause(param.getSort() + " " + param.getOrder());

            GoodsExample.Criteria criteria = goodsExample.createCriteria();

            criteria.andDeletedEqualTo(false).andIsNewEqualTo(true);

            List<Goods> goods = goodsMapper.selectByExample(goodsExample);

            List<WxGoodsListVo.GoodsListBean> goodsList = new ArrayList<>();

            ArrayList<Integer> list = new ArrayList<>();

            List<WxGoodsListVo.FilterCategoryListBean> filterCategoryList = new ArrayList<>();

            for (Goods good : goods) {

                if (!categoryId.equals(0)) {
                    if (categoryId.equals(good.getCategoryId())) {
                        addGoodsList(goodsList, good);
                    }
                } else {
                    addGoodsList(goodsList, good);
                }

                if (!list.contains(good.getCategoryId())) {

                    list.add(good.getCategoryId());

                    WxGoodsListVo.FilterCategoryListBean filterCategoryListBean = new WxGoodsListVo.FilterCategoryListBean();

                    Category category = categoryMapper.selectByPrimaryKey(good.getCategoryId());

                    filterCategoryListBean.setId(category.getId());

                    filterCategoryListBean.setName(category.getName());

                    filterCategoryListBean.setKeywords(category.getKeywords());

                    filterCategoryListBean.setDesc(category.getDesc());

                    filterCategoryListBean.setPid(category.getPid());

                    filterCategoryListBean.setIconUrl(category.getIconUrl());

                    filterCategoryListBean.setPicUrl(category.getPicUrl());

                    filterCategoryListBean.setLevel(category.getLevel());

                    if (category.getSortOrder() != null) {
                        filterCategoryListBean.setSortOrder(category.getSortOrder());
                    }

                    filterCategoryListBean.setAddTime(category.getAddTime());

                    filterCategoryListBean.setUpdateTime(category.getUpdateTime());

                    filterCategoryListBean.setDeleted(category.getDeleted());

                    filterCategoryList.add(filterCategoryListBean);
                }

            }

            PageInfo<WxGoodsListVo.GoodsListBean> pageInfo = new PageInfo<>(goodsList);

            Long count = pageInfo.getTotal();

            wxGoodsListVo.setGoodsList(goodsList);

            wxGoodsListVo.setCount(count);

            wxGoodsListVo.setFilterCategoryList(filterCategoryList);

            return wxGoodsListVo;

        } else if (isHot != null) {

            PageHelper.startPage(param.getPage(), param.getSize());

            GoodsExample goodsExample = new GoodsExample();

            goodsExample.setOrderByClause(param.getSort() + " " + param.getOrder());

            GoodsExample.Criteria criteria = goodsExample.createCriteria();

            criteria.andDeletedEqualTo(false).andIsHotEqualTo(true);

            List<Goods> goods = goodsMapper.selectByExample(goodsExample);

            List<WxGoodsListVo.GoodsListBean> goodsList = new ArrayList<>();

            ArrayList<Integer> list = new ArrayList<>();

            List<WxGoodsListVo.FilterCategoryListBean> filterCategoryList = new ArrayList<>();

            for (Goods good : goods) {

                if (!categoryId.equals(0)) {
                    if (categoryId.equals(good.getCategoryId())) {
                        addGoodsList(goodsList, good);
                    }
                } else {
                    addGoodsList(goodsList, good);
                }

                if (!list.contains(good.getCategoryId())) {

                    list.add(good.getCategoryId());

                    WxGoodsListVo.FilterCategoryListBean filterCategoryListBean = new WxGoodsListVo.FilterCategoryListBean();

                    Category category = categoryMapper.selectByPrimaryKey(good.getCategoryId());

                    filterCategoryListBean.setId(category.getId());

                    filterCategoryListBean.setName(category.getName());

                    filterCategoryListBean.setKeywords(category.getKeywords());

                    filterCategoryListBean.setDesc(category.getDesc());

                    filterCategoryListBean.setPid(category.getPid());

                    filterCategoryListBean.setIconUrl(category.getIconUrl());

                    filterCategoryListBean.setPicUrl(category.getPicUrl());

                    filterCategoryListBean.setLevel(category.getLevel());

                    if (category.getSortOrder() != null) {
                        filterCategoryListBean.setSortOrder(category.getSortOrder());
                    }

                    filterCategoryListBean.setAddTime(category.getAddTime());

                    filterCategoryListBean.setUpdateTime(category.getUpdateTime());

                    filterCategoryListBean.setDeleted(category.getDeleted());

                    filterCategoryList.add(filterCategoryListBean);
                }

            }

            PageInfo<WxGoodsListVo.GoodsListBean> pageInfo = new PageInfo<>(goodsList);

            Long count = pageInfo.getTotal();

            wxGoodsListVo.setGoodsList(goodsList);

            wxGoodsListVo.setCount(count);

            wxGoodsListVo.setFilterCategoryList(filterCategoryList);

            return wxGoodsListVo;

        } else if (categoryId != null) {

//            WxGoodsListVo wxGoodsListVo = new WxGoodsListVo();

            PageHelper.startPage(param.getPage(), param.getSize());

            GoodsExample goodsExample = new GoodsExample();

            GoodsExample.Criteria criteria = goodsExample.createCriteria();

            criteria.andDeletedEqualTo(false).andCategoryIdEqualTo(categoryId);

            List<Goods> goodsList = goodsMapper.selectByExample(goodsExample);

            List<WxGoodsListVo.GoodsListBean> beans = new ArrayList<>();

            for (Goods goods : goodsList) {
                addGoodsList(beans, goods);
            }

            wxGoodsListVo.setGoodsList(beans);

            PageInfo<WxGoodsListVo.GoodsListBean> pageInfo = new PageInfo<>(beans);

            Long count = pageInfo.getTotal();

            wxGoodsListVo.setCount(count);

            Integer pid = categoryMapper.selectL1ByL2Id(categoryId);

            CategoryExample categoryExample = new CategoryExample();

            CategoryExample.Criteria categoryCriteria = categoryExample.createCriteria();

            categoryCriteria.andDeletedEqualTo(false).andPidEqualTo(pid);

            List<Category> categories = categoryMapper.selectByExample(categoryExample);

            List<WxGoodsListVo.FilterCategoryListBean> filterCategoryList = new ArrayList<>();

            for (Category category : categories) {

                WxGoodsListVo.FilterCategoryListBean filterCategoryListBean = new WxGoodsListVo.FilterCategoryListBean();

                filterCategoryListBean.setId(category.getId());

                filterCategoryListBean.setName(category.getName());

                filterCategoryListBean.setKeywords(category.getKeywords());

                filterCategoryListBean.setDesc(category.getDesc());

                filterCategoryListBean.setPid(category.getPid());

                filterCategoryListBean.setIconUrl(category.getIconUrl());

                filterCategoryListBean.setPicUrl(category.getPicUrl());

                filterCategoryListBean.setLevel(category.getLevel());

                if (category.getSortOrder() != null) {
                    filterCategoryListBean.setSortOrder(category.getSortOrder());
                }

                filterCategoryListBean.setAddTime(category.getAddTime());

                filterCategoryListBean.setUpdateTime(category.getUpdateTime());

                filterCategoryListBean.setDeleted(category.getDeleted());

                filterCategoryList.add(filterCategoryListBean);
            }

            wxGoodsListVo.setFilterCategoryList(filterCategoryList);

            return wxGoodsListVo;
        } else if (brandId != null) {

//            WxGoodsListVo wxGoodsListVo = new WxGoodsListVo();

            PageHelper.startPage(param.getPage(), param.getSize());

            GoodsExample goodsExample = new GoodsExample();

            GoodsExample.Criteria criteria = goodsExample.createCriteria();

            criteria.andDeletedEqualTo(false).andBrandIdEqualTo(brandId);

            List<Goods> goodsList = goodsMapper.selectByBrandId(brandId);

            List<WxGoodsListVo.GoodsListBean> beans = new ArrayList<>();

            List<WxGoodsListVo.FilterCategoryListBean> filterCategoryList = new ArrayList<>();

            for (Goods goods : goodsList) {

                addGoodsList(beans, goods);

                Category category = categoryMapper.selectByPrimaryKey(goods.getCategoryId());

                WxGoodsListVo.FilterCategoryListBean filterCategoryListBean = new WxGoodsListVo.FilterCategoryListBean();

                filterCategoryListBean.setId(category.getId());

                filterCategoryListBean.setName(category.getName());

                filterCategoryListBean.setKeywords(category.getKeywords());

                filterCategoryListBean.setDesc(category.getDesc());

                filterCategoryListBean.setPid(category.getPid());

                filterCategoryListBean.setIconUrl(category.getIconUrl());

                filterCategoryListBean.setPicUrl(category.getPicUrl());

                filterCategoryListBean.setLevel(category.getLevel());

                if (category.getSortOrder() != null) {
                    filterCategoryListBean.setSortOrder(category.getSortOrder());
                }

                filterCategoryListBean.setAddTime(category.getAddTime());

                filterCategoryListBean.setUpdateTime(category.getUpdateTime());

                filterCategoryListBean.setDeleted(category.getDeleted());

                filterCategoryList.add(filterCategoryListBean);
            }

            wxGoodsListVo.setGoodsList(beans);

            PageInfo<WxGoodsListVo.GoodsListBean> pageInfo = new PageInfo<>(beans);

            Long count = pageInfo.getTotal();

            wxGoodsListVo.setCount(count);

            wxGoodsListVo.setFilterCategoryList(filterCategoryList);

            return wxGoodsListVo;
        }

        return null;
    }

    private void addGoodsList(List<WxGoodsListVo.GoodsListBean> goodsList, Goods good) {

        WxGoodsListVo.GoodsListBean bean = new WxGoodsListVo.GoodsListBean();

        bean.setId(good.getId());

        bean.setName(good.getName());

        bean.setBrief(good.getBrief());

        bean.setPicUrl(good.getPicUrl());

        bean.setNew(good.getIsNew());

        bean.setHot(good.getIsHot());

        bean.setCounterPrice(good.getCounterPrice());

        bean.setRetailPrice(good.getRetailPrice());

        goodsList.add(bean);
    }

    @Override
    public WxGoodsDetailVo detail(Integer id) {

        if (userInfoUtils.getUsername() != null) {

            Integer userId = userInfoUtils.getUserId();

            FootPrint footPrint = new FootPrint();

            Date date = new Date();

            footPrint.setAddTime(date);

            footPrint.setUpdateTime(date);

            footPrint.setUserId(userId);

            footPrint.setGoodsId(id);

            footPrintMapper.insertSelective(footPrint);

        }

        WxGoodsDetailVo wxGoodsDetailVo = new WxGoodsDetailVo();

        CollectExample collectExample = new CollectExample();

        CollectExample.Criteria collectCriteria = collectExample.createCriteria();

        collectCriteria.andDeletedEqualTo(false).andValueIdEqualTo(id);

        long collectCount = collectMapper.countByExample(collectExample);

        wxGoodsDetailVo.setUserHasCollect(collectCount);

        CommentExample commentExample = new CommentExample();

        CommentExample.Criteria commentCriteria = commentExample.createCriteria();

        commentCriteria.andDeletedEqualTo(false).andValueIdEqualTo(id);

        List<Comment> comments = commentMapper.selectByExample(commentExample);

        long commentCount = commentMapper.countByExample(commentExample);

        WxGoodsDetailVo.CommentBean commentBean = new WxGoodsDetailVo.CommentBean();

        commentBean.setCount(commentCount);

        List<WxGoodsDetailVo.DataBean> dataBeanList = new ArrayList<>();

        for (Comment comment : comments) {

            WxGoodsDetailVo.DataBean dataBean = new WxGoodsDetailVo.DataBean();

            dataBean.setId(comment.getId());

            dataBean.setAddTime(comment.getAddTime());

            dataBean.setContent(comment.getContent());

            dataBean.setPicList(comment.getPicUrls());

            User user = userMapper.selectByPrimaryKey(comment.getUserId());

            dataBean.setAvatar(user.getAvatar());

            dataBean.setNickname(user.getNickname());

            dataBeanList.add(dataBean);
        }

        commentBean.setData(dataBeanList);

        wxGoodsDetailVo.setComment(commentBean);

        Goods goods = goodsMapper.selectByPrimaryKey(id);

        wxGoodsDetailVo.setInfo(goods);

        Brand brand = brandMapper.selectByPrimaryKey(goods.getBrandId());

        wxGoodsDetailVo.setBrand(brand);

        GoodsProductExample goodsProductExample = new GoodsProductExample();

        GoodsProductExample.Criteria goodsProductCriteria = goodsProductExample.createCriteria();

        goodsProductCriteria.andDeletedEqualTo(false).andGoodsIdEqualTo(id);

        List<GoodsProduct> goodsProducts = goodsProductMapper.selectByExample(goodsProductExample);

        wxGoodsDetailVo.setProductList(goodsProducts);

        GoodsAttributeExample goodsAttributeExample = new GoodsAttributeExample();

        GoodsAttributeExample.Criteria goodsAttributeCriteria = goodsAttributeExample.createCriteria();

        goodsAttributeCriteria.andDeletedEqualTo(false).andGoodsIdEqualTo(id);

        List<GoodsAttribute> goodsAttributes = goodsAttributeMapper.selectByExample(goodsAttributeExample);

        wxGoodsDetailVo.setAttribute(goodsAttributes);

        IssueExample issueExample = new IssueExample();

        IssueExample.Criteria issueCriteria = issueExample.createCriteria();

        issueCriteria.andDeletedEqualTo(false);

        List<Issue> issues = issueMapper.selectByExample(issueExample);

        wxGoodsDetailVo.setIssue(issues);

        List<WxGoodsDetailVo.SpecificationListBean> specificationList = new ArrayList<>();

        List<WxGoodsDetailQueryVo> wxGoodsDetailQueryVoList = goodsSpecificationMapper.selectSpecificationIdByGoodsId(id);

        for (WxGoodsDetailQueryVo wxGoodsDetailQueryVo : wxGoodsDetailQueryVoList) {

            WxGoodsDetailVo.SpecificationListBean bean = new WxGoodsDetailVo.SpecificationListBean();

            bean.setName(wxGoodsDetailQueryVo.getSpecification());

            String[] specificationId = wxGoodsDetailQueryVo.getId().split(",");

            List<GoodsSpecification> goodsSpecificationList = new ArrayList<>();

            for (String s : specificationId) {

                int index = Integer.parseInt(s);

                GoodsSpecification goodsSpecification = goodsSpecificationMapper.selectByPrimaryKey(index);

                goodsSpecificationList.add(goodsSpecification);
            }

            bean.setValueList(goodsSpecificationList);

            specificationList.add(bean);
        }

        wxGoodsDetailVo.setSpecificationList(specificationList);

        return wxGoodsDetailVo;
    }

    @Override
    public WxGoodsRelatedVo related(Integer id) {

        WxGoodsRelatedVo wxGoodsRelatedVo = new WxGoodsRelatedVo();

        Goods goods = goodsMapper.selectByPrimaryKey(id);

        GoodsExample goodsExample = new GoodsExample();

        GoodsExample.Criteria criteria = goodsExample.createCriteria();

        criteria.andDeletedEqualTo(false).andCategoryIdEqualTo(goods.getCategoryId());

        List<Goods> goodsList = goodsMapper.selectByExample(goodsExample);

        List<WxGoodsRelatedVo.GoodsListBean> goodsListBean = new ArrayList<>();

        for (Goods good : goodsList) {

            WxGoodsRelatedVo.GoodsListBean bean = new WxGoodsRelatedVo.GoodsListBean();

            bean.setId(good.getId());

            bean.setName(good.getName());

            bean.setBrief(good.getBrief());

            bean.setPicUrl(good.getPicUrl());

            bean.setNew(good.getIsNew());

            bean.setHot(good.getIsHot());

            bean.setCounterPrice(good.getCounterPrice());

            bean.setRetailPrice(good.getRetailPrice());

            goodsListBean.add(bean);
        }

        wxGoodsRelatedVo.setGoodsList(goodsListBean);

        return wxGoodsRelatedVo;
    }

    @Override
    public WxGoodsCategoryVo category(Integer id) {

        WxGoodsCategoryVo wxGoodsCategoryVo = new WxGoodsCategoryVo();

        Category currentCategory = categoryMapper.selectByPrimaryKey(id);

        if (!currentCategory.getPid().equals(0)) {

            wxGoodsCategoryVo.setCurrentCategory(currentCategory);

            Category parentCategory = categoryMapper.selectByPrimaryKey(currentCategory.getPid());

            wxGoodsCategoryVo.setParentCategory(parentCategory);

            CategoryExample categoryExample = new CategoryExample();

            CategoryExample.Criteria criteria = categoryExample.createCriteria();

            criteria.andDeletedEqualTo(false).andPidEqualTo(currentCategory.getPid());

            List<Category> categories = categoryMapper.selectByExample(categoryExample);

            int index = 0;

            int size = categories.size();

            for (int i = 0; i < size; i++) {

                GoodsExample goodsExample = new GoodsExample();

                GoodsExample.Criteria criteria1 = goodsExample.createCriteria();

                criteria1.andDeletedEqualTo(false).andCategoryIdEqualTo(categories.get(index).getId());

                long count = goodsMapper.countByExample(goodsExample);

                if (count == 0) {
                    Category remove = categories.remove(index);
                    categories.add(remove);
                    continue;
                }
                index++;
            }
            wxGoodsCategoryVo.setBrotherCategory(categories);

        } else {

            CategoryExample categoryExample = new CategoryExample();

            CategoryExample.Criteria criteria = categoryExample.createCriteria();

            criteria.andDeletedEqualTo(false).andPidEqualTo(id);

            List<Category> categories = categoryMapper.selectByExample(categoryExample);

            if (categories.size() != 0) {

                wxGoodsCategoryVo.setParentCategory(currentCategory);

                int index = 0;

                int size = categories.size();

                for (int i = 0; i < size; i++) {

                    GoodsExample goodsExample = new GoodsExample();

                    GoodsExample.Criteria criteria1 = goodsExample.createCriteria();

                    criteria1.andDeletedEqualTo(false).andCategoryIdEqualTo(categories.get(index).getId());

                    long count = goodsMapper.countByExample(goodsExample);

                    if (count == 0) {
                        Category remove = categories.remove(index);
                        categories.add(remove);
                        continue;
                    }
                    index++;
                }
                wxGoodsCategoryVo.setCurrentCategory(categories.get(0));
                wxGoodsCategoryVo.setBrotherCategory(categories);
            } else {
                wxGoodsCategoryVo.setParentCategory(currentCategory);
                wxGoodsCategoryVo.setCurrentCategory(currentCategory);
            }
        }
        return wxGoodsCategoryVo;
    }

}

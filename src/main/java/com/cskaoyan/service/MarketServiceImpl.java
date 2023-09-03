package com.cskaoyan.service;

import com.alibaba.druid.util.StringUtils;
import com.cskaoyan.converter.StringToDateConverter;
import com.cskaoyan.mapper.BrandMapper;
import com.cskaoyan.mapper.CategoryMapper;
import com.cskaoyan.mapper.KeywordMapper;
import com.cskaoyan.mapper.RegionMapper;
import com.cskaoyan.model.bean.*;
import com.cskaoyan.model.bo.*;
import com.cskaoyan.model.vo.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.cskaoyan.mapper.*;
import com.cskaoyan.model.bean.*;
import com.cskaoyan.model.bo.BrandListBo;
import com.cskaoyan.model.bo.CategoryBo;
import com.cskaoyan.model.bo.CategoryBoChildren;
import com.cskaoyan.model.bo.OrderListBo;
import com.cskaoyan.model.vo.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author huxudong
 * @version 1.0
 * @date 2021/8/11 19:46
 */
@Service
public class MarketServiceImpl implements MarketService {

    @Autowired
    RegionMapper regionMapper;
    @Autowired
    BrandMapper brandMapper;
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    KeywordMapper keywordMapper;
    @Autowired
    StringToDateConverter stringToDateConverter;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    OrderGoodsMapper orderGoodsMapper;
    @Autowired
    IssueMapper issueMapper;

    @Override
    public List<RegionVo.DataBean> regionList() {
        List<Region> regions = regionMapper.selectByExample(new RegionExample());
        List<RegionVo.DataBean> list = new ArrayList<>();
        List<RegionVo.DataBean.ChildrenBeanX> listX = new ArrayList<>();
        for (Region region : regions) {
            if (region.getType() == 1) {
                // 直辖市或省
                RegionVo.DataBean dataBean = new RegionVo.DataBean();
                dataBean.setId(region.getId());
                dataBean.setName(region.getName());
                dataBean.setCode(region.getCode());
                dataBean.setType(region.getType());
                list.add(dataBean);
            } else if (region.getType() == 2) {
                // 地级市
                RegionVo.DataBean.ChildrenBeanX childrenBeanX = new RegionVo.DataBean.ChildrenBeanX();
                childrenBeanX.setId(region.getId());
                childrenBeanX.setName(region.getName());
                childrenBeanX.setCode(region.getCode());
                childrenBeanX.setType(region.getType());
                for (RegionVo.DataBean dataBean : list) {
                    if (region.getPid() == dataBean.getId()) {
                        if (dataBean.getChildren() == null) {
                            dataBean.setChildren(new ArrayList<>());
                        }
                        dataBean.getChildren().add(childrenBeanX);
                    }
                }
                listX.add(childrenBeanX);
            } else if (region.getType() == 3) {
                // 县/区
                RegionVo.DataBean.ChildrenBeanX.ChildrenBean childrenBean = new RegionVo.DataBean.ChildrenBeanX.ChildrenBean();
                childrenBean.setId(region.getId());
                childrenBean.setName(region.getName());
                childrenBean.setCode(region.getCode());
                childrenBean.setType(region.getType());
                for (RegionVo.DataBean.ChildrenBeanX childrenBeanX : listX) {
                    if (region.getPid() == childrenBeanX.getId()) {
                        if (childrenBeanX.getChildren() == null) {
                            childrenBeanX.setChildren(new ArrayList<>());
                        }
                        childrenBeanX.getChildren().add(childrenBean);
                    }
                }
            }
        }

        return list;
    }

    @Override
    public BrandListVo brandList(BrandListBo bo) {
        PageHelper.startPage(bo.getPage(), bo.getLimit());

        BrandExample brandExample = new BrandExample();
        brandExample.setOrderByClause(bo.getSort() + " " + bo.getOrder());

        BrandExample.Criteria criteria = brandExample.createCriteria();
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(bo.getId())) {
            int x = 0;
            int id = -1;
            try {
                id = Integer.parseInt(bo.getId());
            }
            catch (Exception e) {
                criteria.andIdEqualTo(-1);
                x = 1;
            }
            if (x==0){
                criteria.andIdEqualTo(id);
            }
        }
        if (!StringUtils.isEmpty(bo.getName())) {
            criteria.andNameLike("%" + bo.getName() + "%");
        }
        BrandListVo brandListVo = new BrandListVo();

        List<Brand> brands = brandMapper.selectByExample(brandExample);

        PageInfo<Brand> pageInfo = new PageInfo<>(brands);

        brandListVo.setItems(brands);
        brandListVo.setTotal(pageInfo.getTotal());

        return brandListVo;
    }

    @Override
    public int deleteBrandById(Integer id) {
        Brand record = new Brand();
        record.setId(id);
        record.setDeleted(true);
        record.setUpdateTime(new Date());
        return brandMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateBrandById(Brand brand) {
        brand.setUpdateTime(new Date());
        return brandMapper.updateByPrimaryKey(brand);
    }

    @Override
    public AddBrandVo createBrand(Brand brand) {
        brand.setDeleted(false);
        brand.setAddTime(new Date());
        brand.setUpdateTime(new Date());
        AddBrandVo addBrandVo = new AddBrandVo();
        addBrandVo.setAddTime(brand.getAddTime());
        addBrandVo.setDesc(brand.getDesc());
        addBrandVo.setFloorPrice(brand.getFloorPrice());
        addBrandVo.setName(brand.getName());
        addBrandVo.setPicUrl(brand.getPicUrl() == null ? "" : brand.getPicUrl());
        addBrandVo.setUpdateTime(brand.getUpdateTime());
        brandMapper.insertSelective(brand);
        addBrandVo.setId(brand.getId());
        return addBrandVo;
    }

    @Override
    public List<CategoryBo> categoryList() {
        CategoryExample categoryExample = new CategoryExample();
        CategoryExample.Criteria criteria = categoryExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        List<Category> categories = categoryMapper.selectByExample(categoryExample);
        List<CategoryBo> categoryBoList = new ArrayList<>();
        for (Category category : categories) {
            // 大类型，包含children
            if ("L1".equals(category.getLevel())) {
                CategoryBo categoryBo = new CategoryBo();
                categoryBo.setDesc(category.getDesc());
                categoryBo.setIconUrl(category.getIconUrl());
                categoryBo.setId(category.getId());
                categoryBo.setKeywords(category.getKeywords());
                categoryBo.setLevel(category.getLevel());
                categoryBo.setName(category.getName());
                categoryBo.setPicUrl(category.getPicUrl());
                categoryBo.setChildren(new ArrayList<>());
                categoryBoList.add(categoryBo);
            }
        }
        for (Category category : categories) {
            // 具体类型，没有下一级
            if ("L2".equals(category.getLevel())) {
                CategoryBoChildren children = new CategoryBoChildren();
                children.setDesc(category.getDesc());
                children.setIconUrl(category.getIconUrl());
                children.setId(category.getId());
                children.setLevel(category.getLevel());
                children.setKeywords(category.getKeywords());
                children.setName(category.getName());
                children.setPicUrl(category.getPicUrl());

                for (CategoryBo categoryBo : categoryBoList) {
                    if (categoryBo.getId().equals(category.getPid())) {
                        // 找到对应的大类，加入到其中
                        categoryBo.getChildren().add(children);
                        break;
                    }
                }
            }

        }

        return categoryBoList;
    }

    @Override
    public List<CategoryL1> categoryL1List() {
        List<CategoryL1> list = categoryMapper.selectL1();
        return list;
    }

    @Override
    public CreateCategoryVo createCategory(CategoryBoChildren children) {
        Category category = new Category();
        category.setDesc(children.getDesc());
        category.setAddTime(new Date());
        category.setDeleted(false);
        category.setIconUrl(children.getIconUrl());
        category.setKeywords(children.getKeywords());
        category.setLevel(children.getLevel());
        category.setPid(children.getPid());
        category.setPicUrl(children.getPicUrl());
        category.setName(children.getName());
        category.setUpdateTime(new Date());
        categoryMapper.insert(category);
        CreateCategoryVo createCategoryVo = new CreateCategoryVo(
                category.getId(), category.getName(), category.getKeywords(), category.getDesc(),
                category.getPid(), category.getIconUrl(), category.getPicUrl(), category.getLevel(),
                category.getAddTime(), category.getUpdateTime()
        );

        return createCategoryVo;
    }

    @Override
    public boolean deleteCategoryById(Integer id) {
        Category record = new Category();
        record.setId(id);
        record.setDeleted(true);
        int x = categoryMapper.updateByPrimaryKeySelective(record);
        return x > 0;
    }

    @Override
    public boolean updateCategory(CategoryBo bo) {
        Category category = new Category();
        category.setId(bo.getId());
        category.setDeleted(false);
        category.setUpdateTime(new Date());
        category.setName(bo.getName());
        category.setPicUrl(bo.getPicUrl());
        category.setLevel(bo.getLevel());
        category.setPid(bo.getPid());
        category.setKeywords(bo.getKeywords());
        category.setDesc(bo.getDesc());
        category.setIconUrl(bo.getIconUrl());
        int i = categoryMapper.updateByPrimaryKeySelective(category);
        return i > 0;
    }

    @Override
    public OrderListVo orderList(OrderListBo orderListBo) {
        PageHelper.startPage(orderListBo.getPage(), orderListBo.getLimit());

        OrderExample orderExample = new OrderExample();
        // 构造排序
        orderExample.setOrderByClause(orderListBo.getSort() + " " + orderListBo.getOrder());
        // 拼接条件
        OrderExample.Criteria criteria = orderExample.createCriteria();

        // 状态条件
        if (orderListBo.getOrderStatusArray() != null) {
            criteria.andOrderStatusEqualTo(orderListBo.getOrderStatusArray());
        }
        // 用户ID
        if (!StringUtils.isEmpty(orderListBo.getUserId())) {
            int x = 0;
            int id = -1;
            try {
                id = Integer.parseInt(orderListBo.getUserId());
            }
            catch (Exception e) {
                criteria.andUserIdEqualTo(-1);
                x = 1;
            }
            if (x==0){
                criteria.andUserIdEqualTo(id);
            }
        }
        // 订单号
        if (!StringUtils.isEmpty(orderListBo.getOrderSn())) {
            criteria.andOrderSnEqualTo(orderListBo.getOrderSn());
        }
        List<Order> orders = orderMapper.selectByExample(orderExample);
        List<OrderListVo.InnerOrderVo> list = new ArrayList<>();
        for (Order order : orders) {
            OrderListVo.InnerOrderVo inner = new OrderListVo.InnerOrderVo();
            BeanUtils.copyProperties(order, inner);
            list.add(inner);
        }
        PageInfo<Order> pageInfo = new PageInfo<>(orders);
        long total = pageInfo.getTotal();

        OrderListVo orderListVo = new OrderListVo();
        orderListVo.setItems(list);
        orderListVo.setTotal(total);

        return orderListVo;
    }

    @Override
    public OrderDetailVo selectOrderDetailById(Integer id) {
        OrderDetailVo orderDetailVo = new OrderDetailVo();

        Order order = orderMapper.selectByPrimaryKey(id);
        OrderListVo.InnerOrderVo innerOrderVo = new OrderListVo.InnerOrderVo();
        OrderDetailVo.UserBean userBean = new OrderDetailVo.UserBean();
        List<OrderDetailVo.OrderGoodsBean> list = new ArrayList<>();

        // 将订单表的部分封装到Vo的内部类中
        BeanUtils.copyProperties(order, innerOrderVo);

        int userId = order.getUserId();
        // 根据userId去user表中查询nickname，avatar
        User user = userMapper.selectByPrimaryKey(userId);
        // 再封装nickname和avatar
        BeanUtils.copyProperties(user, userBean);

        // 根据orderId去order_goods表中查询订单中的所有商品
        Integer orderId = order.getId();
        OrderGoodsExample orderGoodsExample = new OrderGoodsExample();
        OrderGoodsExample.Criteria criteria = orderGoodsExample.createCriteria();
        criteria.andOrderIdEqualTo(orderId);
        List<OrderGoods> orderGoods = orderGoodsMapper.selectByExample(orderGoodsExample);
        for (OrderGoods orderGood : orderGoods) {
            OrderDetailVo.OrderGoodsBean orderGoodsBean = new OrderDetailVo.OrderGoodsBean();
            BeanUtils.copyProperties(orderGood, orderGoodsBean);
            list.add(orderGoodsBean);
        }

        orderDetailVo.setOrder(innerOrderVo);
        orderDetailVo.setUser(userBean);
        orderDetailVo.setOrderGoods(list);
        return orderDetailVo;
    }

    @Override
    public boolean orderShip(OrderShipBo bo) {
        Order order = new Order();
        order.setId(bo.getOrderId());
        order.setShipChannel(bo.getShipChannel());
        order.setShipSn(bo.getShipSn());
        order.setUpdateTime(new Date());
        order.setShipTime(new Date());
        order.setOrderStatus((short) 301);
        int x = orderMapper.updateByPrimaryKeySelective(order);
        return x > 0;
    }

    @Override
    public IssueListVo issueList(Integer page, Integer limit, String question, String sort, String order) {
        PageHelper.startPage(page, limit);

        IssueExample issueExample = new IssueExample();
        issueExample.setOrderByClause(sort + " " + order);

        IssueExample.Criteria criteria = issueExample.createCriteria();
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(question)) {
            criteria.andQuestionLike("%" + question + "%");
        }
        List<Issue> issues = issueMapper.selectByExample(issueExample);
        PageInfo<Issue> pageInfo = new PageInfo<>(issues);

        Long total = pageInfo.getTotal();
        IssueListVo issueListVo = new IssueListVo();
        issueListVo.setItems(issues);
        issueListVo.setTotal(total);
        return issueListVo;
    }

    @Override
    public boolean issueDelete(Integer id) {
        Issue issue = new Issue();
        issue.setId(id);
        issue.setDeleted(true);
        issue.setUpdateTime(new Date());
        int x = issueMapper.updateByPrimaryKeySelective(issue);
        return x > 0;
    }

    @Override
    public Issue issueCreate(IssueCreateBo bo) {
        Issue issue = new Issue();
        issue.setUpdateTime(new Date());
        issue.setAddTime(new Date());
        issue.setAnswer(bo.getAnswer());
        issue.setQuestion(bo.getQuestion());
        issue.setDeleted(false);
        issueMapper.insertSelective(issue);
        return issue;
    }

    @Override
    public Issue issueUpdate(Issue issue) {
        issue.setUpdateTime(new Date());
        issueMapper.updateByPrimaryKeySelective(issue);
        return issue;
    }

    @Override
    public MarketKeyWordListVo list(MarketKeyWordListBo marketKeyWordListBo) {
        PageHelper.startPage(marketKeyWordListBo.getPage(), marketKeyWordListBo.getLimit());
        KeywordExample keywordExample = new KeywordExample();
        keywordExample.setOrderByClause(marketKeyWordListBo.getSort() + " " + marketKeyWordListBo.getOrder());
        KeywordExample.Criteria criteria = keywordExample.createCriteria();
        if (marketKeyWordListBo.getKeyword() != null && marketKeyWordListBo.getKeyword().length() != 0) {
            criteria.andKeywordLike("%" + marketKeyWordListBo.getKeyword() + "%");
        }
        if (marketKeyWordListBo.getUrl() != null && marketKeyWordListBo.getUrl().length() != 0) {
            criteria.andUrlLike("%" + marketKeyWordListBo.getUrl() + "%");
        }
        criteria.andDeletedEqualTo(false);
        List<Keyword> keywords = keywordMapper.selectByExample(keywordExample);
        PageInfo<Keyword> keywordPageInfo = new PageInfo<>(keywords);
        long total = keywordPageInfo.getTotal();
        MarketKeyWordListVo marketKeyWordListVo = new MarketKeyWordListVo();
        marketKeyWordListVo.setTotal(total);
        marketKeyWordListVo.setItems(keywords);
        return marketKeyWordListVo;
    }

    @Override
    public Keyword create(MarketKeyWordCreateBo marketKeyWordCreateBo) {
        Keyword keyword = new Keyword();
        keyword.setKeyword(marketKeyWordCreateBo.getKeyword());
        keyword.setUrl(marketKeyWordCreateBo.getUrl());
        keyword.setIsHot(marketKeyWordCreateBo.isIsHot());
        keyword.setIsDefault(marketKeyWordCreateBo.isIsDefault());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = simpleDateFormat.format(new Date());
        Date convert = stringToDateConverter.convert(time);
        keyword.setAddTime(convert);
        keyword.setUpdateTime(convert);
        int result = keywordMapper.insertSelective(keyword);
        if (result != 1) {
            return null;
        } else return keyword;
    }

    @Override
    public Keyword update(Keyword keyword) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = simpleDateFormat.format(new Date());
        Date convert = stringToDateConverter.convert(time);
        keyword.setUpdateTime(convert);
        int result = keywordMapper.updateByPrimaryKeySelective(keyword);
        if (result < 1) {
            return null;
        }
        return keyword;
    }

    @Override
    public boolean delete(Keyword keyword) {
        Keyword kw = new Keyword();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = simpleDateFormat.format(new Date());
        Date convert = stringToDateConverter.convert(time);
        keyword.setUpdateTime(convert);
        kw.setUpdateTime(convert);
        kw.setDeleted(true);
        kw.setId(keyword.getId());
        int result = keywordMapper.updateByPrimaryKeySelective(kw);
        return result >= 1;
    }


}

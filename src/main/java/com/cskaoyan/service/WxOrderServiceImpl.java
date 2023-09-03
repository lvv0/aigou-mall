package com.cskaoyan.service;

import com.cskaoyan.mapper.*;
import com.cskaoyan.model.bean.*;
import com.cskaoyan.model.bo.WxFeedbackSubmitBo;
import com.cskaoyan.model.bo.WxOrderCommentBo;
import com.cskaoyan.model.bo.WxOrderSubmitBo;
import com.cskaoyan.model.bo.WxReturnGoodsBo;
import com.cskaoyan.model.vo.WxCartCheckOutVo;
import com.cskaoyan.model.vo.WxOrderDetailVo;
import com.cskaoyan.model.vo.WxOrderHandleOptionVo;
import com.cskaoyan.model.vo.WxOrderListVo;
import com.cskaoyan.utils.OrderStatusUtils;
import com.cskaoyan.utils.TimeOutUpdateOrder;
import com.cskaoyan.utils.UserInfoUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.System;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author huxudong
 * @version 1.0
 * @date 2021/8/14 11:29
 */
@Service
public class WxOrderServiceImpl implements WxOrderService {

    @Autowired
    OrderMapper orderMapper;
    @Autowired
    OrderGoodsMapper orderGoodsMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    GrouponMapper grouponMapper;
    @Autowired
    GoodsProductMapper goodsProductMapper;
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    CartMapper cartMapper;
    @Autowired
    AddressMapper addressMapper;
    @Autowired
    WxCartService wxCartService;
    @Autowired
    FeedbackMapper feedbackMapper;
    @Autowired
    SystemMapper systemMapper;
    @Autowired
    CouponMapper couponMapper;
    @Autowired
    CouponUserMapper couponUserMapper;


    @Override
    public WxOrderListVo orderList(Short showType, Integer page, Integer size) {

        // 更新超时的订单
        TimeOutUpdateOrder.cancerOrder(orderMapper,this);
        TimeOutUpdateOrder.updateStatus(orderMapper,orderGoodsMapper);

        // 根据username查询userId
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
//        System.out.println(username);
        int userId = userMapper.queryUserIdByUsername(username);

        // 根据userId和page、size分页查询
        PageHelper.startPage(page, size);
        OrderExample example = new OrderExample();
        example.setOrderByClause("update_time desc");

        OrderExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        criteria.andDeletedEqualTo(false);
        Short statusCode;
        if (showType == 1) {
            statusCode = 101;
            criteria.andOrderStatusEqualTo(statusCode);
        } else if (showType == 2) {
            statusCode = 201;
            criteria.andOrderStatusEqualTo(statusCode);
        } else if (showType == 3) {
            statusCode = 301;
            criteria.andOrderStatusEqualTo(statusCode);
        } else if (showType == 4) {
            statusCode = 401;
            criteria.andOrderStatusEqualTo(statusCode).andPayIdIsNull();
        }
        List<Order> orders = orderMapper.selectByExample(example);
        PageInfo<Order> pageInfo = new PageInfo<>(orders);

        // 根据订单id查询对应的商品、查询是否为团购、设置handleOption
        List<List<OrderGoods>> orderGoodsList = new ArrayList<>();
        List<Boolean> isGroupins = new ArrayList<>();
        List<WxOrderHandleOptionVo> handleOptions = new ArrayList<>();
        for (Order order : orders) {
            // 查询订单对应的商品
            OrderGoodsExample orderGoodsExample = new OrderGoodsExample();
            orderGoodsExample.setOrderByClause("add_time desc");
            OrderGoodsExample.Criteria criteria2 = orderGoodsExample.createCriteria();
            criteria2.andOrderIdEqualTo(order.getId());
            List<OrderGoods> orderGoods = orderGoodsMapper.selectByExample(orderGoodsExample);
            orderGoodsList.add(orderGoods);

            // 查询订单对应的是否团购
            GrouponExample grouponExample = new GrouponExample();
            GrouponExample.Criteria criteria3 = grouponExample.createCriteria();
            criteria3.andOrderIdEqualTo(order.getId());
            long count1 = grouponMapper.countByExample(grouponExample);
            isGroupins.add(count1 == 0);

            // 查询订单的handleOption
//            WxOrderListVo.DataBean.HandleOptionBean handleOptionBean = new WxOrderListVo.DataBean.HandleOptionBean();
//            handleOptionBean.setCancel(order.getOrderStatus()==101);
        }

        // 封装数据
        WxOrderListVo vo = new WxOrderListVo();
        Long count = pageInfo.getTotal();
        vo.setCount(count);
        vo.setTotalPages(count % size == 0 ? count / size : count / size + 1);
        List<WxOrderListVo.DataBean> list = new ArrayList<>();
        for (int i = 0; i < orders.size(); i++) {
            WxOrderListVo.DataBean dataBean = new WxOrderListVo.DataBean();
            dataBean.setActualPrice(orders.get(i).getActualPrice());
            dataBean.setId(orders.get(i).getId());
            dataBean.setOrderSn(orders.get(i).getOrderSn());
            dataBean.setOrderStatusText(OrderStatusUtils.getStatus(orders.get(i).getOrderStatus(),orders.get(i).getPayId()));
            // 设置订单的商品List
            List<OrderGoods> orderGoods = orderGoodsList.get(i);
            List<WxOrderListVo.DataBean.GoodsListBean> goodsListBeans = new ArrayList<>();
            for (OrderGoods goods : orderGoods) {
                WxOrderListVo.DataBean.GoodsListBean goodsListBean = new WxOrderListVo.DataBean.GoodsListBean();
                // 转换成要返回的类型
                BeanUtils.copyProperties(goods, goodsListBean);
                goodsListBeans.add(goodsListBean);
                goodsListBean.setNumber(goods.getNumber());
            }
            dataBean.setGoodsList(goodsListBeans);
            // 设置是否是团购
            dataBean.setIsGroupin(isGroupins.get(i));
            // 设置handleOption
            dataBean.setHandleOption(OrderStatusUtils.getWxOrderHandleOptionVo(orders.get(i).getOrderStatus(), orders.get(i).getPayId()==null));

            list.add(dataBean);
        }
        vo.setData(list);
        return vo;
    }

    @Override
    public WxOrderDetailVo orderDetail(Integer orderId) {
        WxOrderDetailVo wxOrderDetailVo = new WxOrderDetailVo();
        WxOrderDetailVo.OrderInfoBean orderInfoBean = new WxOrderDetailVo.OrderInfoBean();
        Order order = orderMapper.selectByPrimaryKey(orderId);
        BeanUtils.copyProperties(order, orderInfoBean);
        orderInfoBean.setOrderStatusText(OrderStatusUtils.getStatus(order.getOrderStatus(),order.getPayId()));
        orderInfoBean.setHandleOption(OrderStatusUtils.getWxOrderHandleOptionVo(order.getOrderStatus(),order.getPayId()==null));
        OrderGoodsExample orderGoodsExample = new OrderGoodsExample();
        OrderGoodsExample.Criteria criteria = orderGoodsExample.createCriteria();
        orderGoodsExample.setOrderByClause("add_time desc");
        criteria.andOrderIdEqualTo(order.getId());
        List<OrderGoods> orderGoods = orderGoodsMapper.selectByExample(orderGoodsExample);

        // 封装数据
        wxOrderDetailVo.setOrderGoods(orderGoods);
        wxOrderDetailVo.setOrderInfo(orderInfoBean);

        // 添加物流信息
        WxOrderDetailVo.ExpressInfo expressInfo = new WxOrderDetailVo.ExpressInfo();
        expressInfo.setLogisticCode(order.getShipSn());
        expressInfo.setShipperName(order.getShipChannel());
        wxOrderDetailVo.setExpressInfo(expressInfo);

        return wxOrderDetailVo;
    }

    @Override
    public boolean orderCancel(Integer orderId) {
        Order order = new Order();
        order.setId(orderId);
        order.setUpdateTime(new Date());
        Short s = 102;
        order.setOrderStatus(s);
        returnGoodsNumber(orderId);
        return orderMapper.updateByPrimaryKeySelective(order) > 0;
    }

    @Override
    public boolean orderConfirm(Integer orderId) {
        Order order = new Order();
        order.setId(orderId);
        order.setUpdateTime(new Date());
        order.setConfirmTime(new Date());
        order.setEndTime(new Date());
        Short s = 401;
        order.setOrderStatus(s);
        return orderMapper.updateByPrimaryKeySelective(order) > 0;
    }

    @Override
    public boolean orderDelete(Integer orderId) {
        Order order = new Order();
        order.setId(orderId);
        order.setUpdateTime(new Date());
        order.setDeleted(true);
        int x = orderMapper.updateByPrimaryKeySelective(order);
        return x > 0;
    }

    @Override
    public boolean orderRefund(Integer orderId) {
        Order order = new Order();
        order.setId(orderId);
        order.setUpdateTime(new Date());
        order.setEndTime(new Date());
        Short s = 202;
        order.setOrderStatus(s);
        int x = orderMapper.updateByPrimaryKeySelective(order);
        returnGoodsNumber(orderId);

        return x > 0;
    }

    // 回滚商品数量
    private void returnGoodsNumber(Integer orderId) {
        OrderGoodsExample orderGoodsExample = new OrderGoodsExample();
        OrderGoodsExample.Criteria criteria = orderGoodsExample.createCriteria();
        criteria.andOrderIdEqualTo(orderId);

        List<OrderGoods> orderGoods = orderGoodsMapper.selectByExample(orderGoodsExample);
        for (OrderGoods goods : orderGoods) {
            goodsProductMapper.updateGoodsCountById(goods.getGoodsId(), goods.getNumber());
        }
    }

    @Override
    public OrderGoods orderGoods(Integer orderId, Integer goodsId) {
        OrderGoodsExample orderGoodsExample = new OrderGoodsExample();
        OrderGoodsExample.Criteria criteria = orderGoodsExample.createCriteria();
        criteria.andOrderIdEqualTo(orderId);
        criteria.andGoodsIdEqualTo(goodsId);
        // 唯一
        List<OrderGoods> orderGoods = orderGoodsMapper.selectByExample(orderGoodsExample);
        return orderGoods.get(0);
    }

    @Override
    public boolean orderComment(WxOrderCommentBo wxOrderCommentBo) {
        // 根据username查询userId
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        int userId = userMapper.queryUserIdByUsername(username);
        Comment comment = new Comment();
        comment.setUserId(userId);
        comment.setAddTime(new Date());
        BeanUtils.copyProperties(wxOrderCommentBo, comment);
        Byte b = 3;
        comment.setType(b);
        comment.setDeleted(false);
        Integer goodsId = orderGoodsMapper.selectGoodsIdById(wxOrderCommentBo.getOrderGoodsId());

        comment.setValueId(goodsId);
        comment.setUpdateTime(new Date());
        int x = commentMapper.insertSelective(comment);

        // 还需要修改订单的comment属性
        Integer orderGoodsId = wxOrderCommentBo.getOrderGoodsId();
        Integer commentId = comment.getId();
        OrderGoods orderGoods = new OrderGoods();
        orderGoods.setId(orderGoodsId);
        orderGoods.setComment(commentId);
        orderGoods.setUpdateTime(new Date());
        orderGoodsMapper.updateByPrimaryKeySelective(orderGoods);

        // 查询当前订单的其他商品是否评价，如果都评价了就修改订单表中的status
        Integer orderId = orderGoodsMapper.selectOrderIdByGoodsId(orderGoodsId);

        OrderGoodsExample orderGoodsExample = new OrderGoodsExample();
        OrderGoodsExample.Criteria criteria = orderGoodsExample.createCriteria();
        criteria.andOrderIdEqualTo(orderId);
        criteria.andCommentEqualTo(0);
        long count = orderGoodsMapper.countByExample(orderGoodsExample);
        // 如果为0，则修改订单表
        if (count == 0) {
            Order order = new Order();
            order.setId(orderId);
            order.setUpdateTime(new Date());
            // 评论后就修改payId，这样就可以不修改orderStatus了
            order.setPayId("0");
            orderMapper.updateByPrimaryKeySelective(order);
        }
        // 否则修改订单表中的updateTime
        else {
            Order order = new Order();
            order.setId(orderId);
            order.setUpdateTime(new Date());
            orderMapper.updateByPrimaryKeySelective(order);
        }
        return x > 0;
    }

    @Override
    public int orderSubmit(WxOrderSubmitBo bo) {
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        int userId = userMapper.queryUserIdByUsername(username);

        // 准备信息，生成订单
        Order order = new Order();
        WxCartCheckOutVo wxCartCheckOutVo = wxCartService.checkOut(0, bo.getAddressId(), bo.getCouponId(), 0, username);
        Address address = wxCartCheckOutVo.getCheckedAddress();

        // 查询数量是否充足
        List<Cart> carts1 = wxCartCheckOutVo.getCheckedGoodsList();
        List<Cart> carts = new ArrayList<>();
        BigDecimal charge = BigDecimal.ZERO;
        for (Cart cart : carts1) {
            Integer productId = cart.getProductId();
            Integer number = cart.getNumber();
            GoodsProduct goodsProduct = goodsProductMapper.selectByPrimaryKey(productId);
            if (number <= goodsProduct.getNumber()) {
                carts.add(cart);
                charge = charge.add(cart.getPrice()).multiply(new BigDecimal(cart.getNumber()));
            }
        }
        // 库存不足
        if (carts.size() == 0) {
            return -1;
        }

        // 如果出现某个商品缺货，才会重新计算价格
        if(carts.size()!= carts1.size()) {

            // 重新计算现在的价格是否免运费
            String keyName = systemMapper.getValueByName("cskaoyan_mall_express_freight_min");
            BigDecimal minPrice = new BigDecimal(keyName);
            if (charge.compareTo(minPrice) < 0) {
                // 不能免运费了
                wxCartCheckOutVo.setFreightPrice(new BigDecimal(systemMapper.getValueByName("cskaoyan_mall_express_freight_value")));
            }

            // 查询优惠券
            CouponUserExample couponUserExample = new CouponUserExample();
            CouponUserExample.Criteria criteria1 = couponUserExample.createCriteria();
            criteria1.andDeletedEqualTo(false);
            criteria1.andUserIdEqualTo(userId);
            criteria1.andStartTimeLessThan(new Date());
            criteria1.andEndTimeGreaterThan(new Date());
            List<CouponUser> couponUsers = couponUserMapper.selectByExample(couponUserExample);

            Coupon coupon = null;
            if (bo.getCouponId() > 0) {
                CouponExample couponExample = new CouponExample();
                CouponExample.Criteria criteria = couponExample.createCriteria();
                criteria.andDeletedEqualTo(false);
                criteria.andIdEqualTo(bo.getCouponId());
                criteria.andMinLessThan(charge);
                criteria.andStartTimeLessThan(new Date());
                criteria.andEndTimeGreaterThan(new Date());
                List<Coupon> coupons = couponMapper.selectByExample(couponExample);
                if (coupons.size() > 0) {
                    coupon = coupons.get(0);
                }
            }
            List<Coupon> coupons1 = new ArrayList<>();
            for (CouponUser couponUser : couponUsers) {
                CouponExample couponExample = new CouponExample();
                CouponExample.Criteria criteria = couponExample.createCriteria();
                criteria.andDeletedEqualTo(false);
                criteria.andIdEqualTo(couponUser.getCouponId());
                criteria.andMinLessThan(charge);
                //criteria.andStartTimeLessThan(new Date());
                //criteria.andEndTimeGreaterThan(new Date());
                List<Coupon> coupons = couponMapper.selectByExample(couponExample);
                if (coupons.size() > 0) {
                    coupons1.add(coupons.get(0));
                }
            }
            if (coupon == null && coupons1.size() > 0) {
                BigDecimal maxCouponPrice = BigDecimal.ZERO;
                for (Coupon coupon1 : coupons1) {
                    if (maxCouponPrice.compareTo(coupon1.getDiscount()) == -1) {
                        maxCouponPrice = coupon1.getDiscount();
                        coupon = coupon1;
                    }
                }
            }
            // 选择优惠券
            if (coupon == null) {
                wxCartCheckOutVo.setCouponId(0);
                wxCartCheckOutVo.setCouponPrice(BigDecimal.ZERO);
            } else {
                wxCartCheckOutVo.setCouponId(coupon.getId());
                wxCartCheckOutVo.setCouponPrice(coupon.getDiscount());
            }
        }

        order.setUserId(userId);
        String format = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        format += (long) ((Math.random() * 9 + 1) * 100000) + "";
        order.setOrderSn(format);
        order.setOrderStatus((short) 101);
        order.setConsignee(address.getName());
        order.setUserId(userId);
        order.setMobile(address.getMobile());
        order.setAddress(address.getAddress());
        order.setMessage(bo.getMessage());

        // 设置价格
        order.setGoodsPrice(charge);
        order.setFreightPrice(wxCartCheckOutVo.getFreightPrice());
        order.setCouponPrice(wxCartCheckOutVo.getCouponPrice());
        order.setGrouponPrice(wxCartCheckOutVo.getGrouponPrice());
        order.setIntegralPrice(BigDecimal.ZERO);
        order.setOrderPrice((charge.subtract(wxCartCheckOutVo.getCouponPrice())).add(wxCartCheckOutVo.getFreightPrice()));
        order.setActualPrice((charge.subtract(wxCartCheckOutVo.getCouponPrice())).add(wxCartCheckOutVo.getFreightPrice()));
        order.setAddTime(new Date());
        order.setUpdateTime(new Date());
        order.setDeleted(false);
        // 插入数据
        orderMapper.insertSelective(order);

        // 修改order_goods表
        for (Cart cart : carts) {
            OrderGoods orderGoods = new OrderGoods();
            BeanUtils.copyProperties(cart, orderGoods);
            orderGoods.setOrderId(order.getId());
            orderGoods.setAddTime(new Date());
            orderGoods.setUpdateTime(new Date());
            orderGoods.setDeleted(false);
            orderGoods.setId(null);
            orderGoods.setComment(0);
            String[] specifications = cart.getSpecifications();
            String s = Arrays.toString(specifications);
            orderGoods.setSpecifications(s);
            int number = cart.getNumber();
            orderGoods.setNumber((short) number);
            orderGoodsMapper.insert(orderGoods);
        }

        // 删除购物车已付款的商品
        for (Cart cart : carts) {
            cart.setUpdateTime(new Date());
            cart.setDeleted(true);
            cartMapper.updateByPrimaryKeySelective(cart);
        }

        // 修改库存
        for (Cart cart : carts) {
            GoodsProduct goodsProduct = new GoodsProduct();
            GoodsProduct goodsProduct1 = goodsProductMapper.selectByPrimaryKey(cart.getProductId());
            goodsProduct.setId(cart.getProductId());
            goodsProduct.setNumber(goodsProduct1.getNumber() - cart.getNumber());
            goodsProduct.setUpdateTime(new Date());
            goodsProductMapper.updateByPrimaryKeySelective(goodsProduct);
        }

        // 修改优惠券
        if (wxCartCheckOutVo.getCouponId()!=0){
            Coupon coupon = new Coupon();

            // 根据优惠券查询数量
            Coupon coupon1 = couponMapper.selectByPrimaryKey(wxCartCheckOutVo.getCouponId());
            Integer total = coupon1.getTotal();
            total--;
            if (total==0){
                coupon.setDeleted(true);
            }
            coupon.setId(wxCartCheckOutVo.getCouponId());
            coupon.setUpdateTime(new Date());
            coupon.setStatus((short)1);
            coupon.setTotal(total);
            couponMapper.updateByPrimaryKeySelective(coupon);

            // 修改用户优惠券
            couponUserMapper.updateByUserIdAndCouponId(userId,wxCartCheckOutVo.getCouponId(),new Date(),false,new Date(),order.getId());

        }


        return order.getId();
    }

    @Override
    public boolean feedbackSubmit(WxFeedbackSubmitBo bo) {
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        if(username==null){
            return false;
        }

        int userId = userMapper.queryUserIdByUsername(username);
        Feedback feedback = new Feedback();
        feedback.setAddTime(new Date());
        feedback.setContent(bo.getContent());
        feedback.setDeleted(false);
        feedback.setFeedType(bo.getFeedType());
        feedback.setHasPicture(bo.getHasPicture());
        feedback.setMobile(bo.getMobile());
        feedback.setPicUrls(bo.getPicUrls());
        feedback.setUpdateTime(new Date());
        feedback.setUserId(userId);
        feedback.setUsername(username);
        feedback.setStatus(1);
        int insert = feedbackMapper.insert(feedback);
        return insert > 0;
    }

    @Override
    public boolean orderReturns(WxReturnGoodsBo bo, Integer orderId) {
        Order order = new Order();
        order.setId(orderId);
        order.setMessage(bo.getFeedType());
        order.setShipChannel(bo.getCompany());
        order.setShipSn(bo.getNumber());
        order.setPayId("1");
        order.setUpdateTime(new Date());
        order.setEndTime(new Date());
        order.setShipTime(new Date());
        int i = orderMapper.updateByPrimaryKeySelective(order);
        returnGoodsNumber(orderId);

        return i>0;
    }

}







package com.cskaoyan.utils;

import com.cskaoyan.mapper.OrderGoodsMapper;
import com.cskaoyan.mapper.OrderMapper;
import com.cskaoyan.model.bean.Order;
import com.cskaoyan.model.bean.OrderExample;
import com.cskaoyan.model.bean.OrderGoods;
import com.cskaoyan.service.WxOrderService;

import java.util.Date;
import java.util.List;

/**
 * @author huxudong
 * @version 1.0
 * @date 2021/8/15 12:16
 */
public class TimeOutUpdateOrder {

    public static void updateStatus(OrderMapper orderMapper, OrderGoodsMapper orderGoodsMapper) {
        OrderExample orderExample = new OrderExample();
        OrderExample.Criteria criteria = orderExample.createCriteria();
        Short s = 401;
        criteria.andOrderStatusEqualTo(s);
        criteria.andDeletedEqualTo(false);
        criteria.andPayIdIsNull();
        List<Order> orders = orderMapper.selectByExample(orderExample);
        for (Order order : orders) {
            Date updateTime = order.getUpdateTime();
            long time = updateTime.getTime();
            long now = System.currentTimeMillis();
            // 超过三分钟就修改订单状态以及订单商品表的状态
            if (now - time > 1000 * 60 * 60 * 24) {
                // 修改订单表
                Integer orderId = order.getId();
                Order order1 = new Order();
                order1.setId(orderId);
                order1.setPayId("0");
                order1.setUpdateTime(new Date());
                order1.setEndTime(new Date());
                orderMapper.updateByPrimaryKeySelective(order1);
                // 修改订单商品表
                OrderGoods orderGoods = new OrderGoods();
                orderGoods.setOrderId(orderId);
                orderGoods.setUpdateTime(new Date());
                orderGoods.setComment(-1);
                orderGoodsMapper.updateByPrimaryKeySelective(orderGoods);
            }
        }
    }

    public static void cancerOrder(OrderMapper orderMapper, WxOrderService service) {
        OrderExample orderExample = new OrderExample();
        OrderExample.Criteria criteria = orderExample.createCriteria();
        criteria.andOrderStatusEqualTo((short) 101).andDeletedEqualTo(false);
        List<Order> orders = orderMapper.selectByExample(orderExample);
        for (Order order : orders) {
            Date addTime = order.getAddTime();
            long time = addTime.getTime();
            long now = System.currentTimeMillis();
            // 超过十分钟就取消订单
            if (now - time > 1000 * 60 * 60 * 24 ) {
                order.setEndTime(new Date());
                service.orderCancel(order.getId());
            }
        }
    }

}

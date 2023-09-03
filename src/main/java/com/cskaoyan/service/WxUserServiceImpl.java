package com.cskaoyan.service;

import com.cskaoyan.mapper.OrderGoodsMapper;
import com.cskaoyan.mapper.OrderMapper;
import com.cskaoyan.model.bean.OrderExample;
import com.cskaoyan.model.vo.WxUserIndexVo;
import com.cskaoyan.utils.TimeOutUpdateOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Name : WxUserServiceImpl.java
 * @Time : 2021/8/15 12:52
 * @Author : Ashe
 * @Software : IntelliJ IDEA
 */
@Service
public class WxUserServiceImpl implements WxUserService{

    @Autowired
    OrderMapper orderMapper;
    @Autowired
    OrderGoodsMapper orderGoodsMapper;
    @Autowired
    WxOrderService wxOrderService;

    @Override
    public WxUserIndexVo index(Integer userId) {

        // 超时未评论
        TimeOutUpdateOrder.updateStatus(orderMapper,orderGoodsMapper);
        // 超时未支付
        TimeOutUpdateOrder.cancerOrder(orderMapper,wxOrderService);

        WxUserIndexVo wxUserIndexVo = new WxUserIndexVo();

        WxUserIndexVo.OrderBean bean = new WxUserIndexVo.OrderBean();


        OrderExample orderExample1 = new OrderExample();

        OrderExample.Criteria criteria1 = orderExample1.createCriteria();

        criteria1.andDeletedEqualTo(false).andUserIdEqualTo(userId).andOrderStatusEqualTo((short) 101);

        bean.setUnpaid(orderMapper.countByExample(orderExample1));


        OrderExample orderExample2 = new OrderExample();

        OrderExample.Criteria criteria2 = orderExample2.createCriteria();

        criteria2.andDeletedEqualTo(false).andUserIdEqualTo(userId).andOrderStatusEqualTo((short) 201);

        bean.setUnship(orderMapper.countByExample(orderExample2));


        OrderExample orderExample3 = new OrderExample();

        OrderExample.Criteria criteria3 = orderExample3.createCriteria();

        criteria3.andDeletedEqualTo(false).andUserIdEqualTo(userId).andOrderStatusEqualTo((short) 301);

        bean.setUnrecv(orderMapper.countByExample(orderExample3));


        OrderExample orderExample4 = new OrderExample();

        OrderExample.Criteria criteria4 = orderExample4.createCriteria();

        criteria4.andDeletedEqualTo(false).andUserIdEqualTo(userId).andOrderStatusEqualTo((short) 401).andPayIdIsNull();

        bean.setUncomment(orderMapper.countByExample(orderExample4));


        wxUserIndexVo.setOrder(bean);

        return wxUserIndexVo;
    }
}

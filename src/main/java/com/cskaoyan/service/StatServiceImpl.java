package com.cskaoyan.service;

import com.cskaoyan.mapper.GoodsMapper;
import com.cskaoyan.mapper.OrderMapper;
import com.cskaoyan.mapper.UserMapper;
import com.cskaoyan.model.vo.GoodsRowsVo;
import com.cskaoyan.model.vo.OrderRowsVo;
import com.cskaoyan.model.vo.UserRowsVo;
import com.cskaoyan.model.vo.StatRespVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ：lww
 * @description：
 * @date ：2021/8/12 19:47
 */
@Service
public class StatServiceImpl implements StatService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    GoodsMapper goodsMapper;

    /**
     * 用户统计
     * @return
     */
    @Override
    public StatRespVo getUserStat() {

        List<String> columns=new ArrayList<>();
        columns.add("day");
        columns.add("users");
        List<UserRowsVo> userRowsVos = userMapper.getUserStat();
        StatRespVo<UserRowsVo> userRespVo = new StatRespVo<>(columns, userRowsVos);
        return userRespVo;
    }

    /**
     * 订单统计
     * @return
     */
    @Override
    public StatRespVo getOrderStat() {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        List<OrderRowsVo> orderRowsVoList = orderMapper.getOrderStat();
        for (OrderRowsVo orderRowsVo : orderRowsVoList) {
            Double d=(orderRowsVo.getAmount()/orderRowsVo.getCustomers());
            Double pcr = Double.parseDouble(decimalFormat.format(d));
            orderRowsVo.setPcr(pcr);
        }
        List<String> columns=new ArrayList<>();
        columns.add("day");
        columns.add("orders");
        columns.add("customers");
        columns.add("amount");
        columns.add("pcr");
        StatRespVo<OrderRowsVo> orderStatRespVo = new StatRespVo<>(columns, orderRowsVoList);
        return orderStatRespVo;
    }

    /**
     * 商品统计
     * @return
     */
    @Override
    public StatRespVo getGoodsStat() {
        List<GoodsRowsVo> goodsRowsVos = goodsMapper.getGoodsStat();
        List<String> columns=new ArrayList<>();
        columns.add("day");
        columns.add("orders");
        columns.add("products");
        columns.add("amount");
        StatRespVo<GoodsRowsVo> goodsRespVo = new StatRespVo<>(columns, goodsRowsVos);
        return goodsRespVo;
    }
}

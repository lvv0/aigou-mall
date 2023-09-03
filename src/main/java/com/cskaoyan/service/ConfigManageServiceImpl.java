package com.cskaoyan.service;

import com.alibaba.druid.util.StringUtils;
import com.cskaoyan.mapper.SystemMapper;
import com.cskaoyan.model.bean.System;
import com.cskaoyan.model.bo.ExpressConfigBo;
import com.cskaoyan.model.bo.MallConfigBo;
import com.cskaoyan.model.bo.OrderConfigBo;
import com.cskaoyan.model.bo.WxConfigBo;
import com.cskaoyan.model.vo.ExpressConfigVo;
import com.cskaoyan.model.vo.MallConfigVo;
import com.cskaoyan.model.vo.OrderConfigVo;
import com.cskaoyan.model.vo.WxConfigVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author ：lww
 * @description：
 * @date ：2021/8/12 14:59
 */
@Service
@Transactional
public class ConfigManageServiceImpl implements ConfigManageService {
    @Autowired
    SystemMapper systemMapper;

    /**
     * 获得商场信息
     * @return
     */
    @Override
    public MallConfigVo getMallInfo() {
        String path="cskaoyan_mall_mall_";
        List<String> configLikeName = systemMapper.getConfigLikeName(path);
        MallConfigVo mallConfigVo = MallConfigVo.createMallConfigVo(configLikeName);
        return mallConfigVo;
    }

    /**
     * 更新商场信息
     * @param mallConfigBo
     * @return
     */
    @Override
    public int updateMallInfo(MallConfigBo mallConfigBo) {
        String path="cskaoyan_mall_mall_";
        String updateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        systemMapper.updateValueByName(path+"address", mallConfigBo.getCskaoyanmall_mall_address(),updateTime);
        systemMapper.updateValueByName(path+"name", mallConfigBo.getCskaoyanmall_mall_name(),updateTime);
        systemMapper.updateValueByName(path+"phone", mallConfigBo.getCskaoyanmall_mall_phone(),updateTime);
        systemMapper.updateValueByName(path+"qq", mallConfigBo.getCskaoyanmall_mall_qq(),updateTime);
        return 0;
    }

    /**
     * 运费配置
     * @return
     */
    @Override
    public ExpressConfigVo getExpressInfo() {
        String path="cskaoyan_mall_express_";
        List<String> configLikeName = systemMapper.getConfigLikeName(path);
        ExpressConfigVo expressConfigVo = ExpressConfigVo.createExpressConfigVo(configLikeName);
        return expressConfigVo;
    }

    @Override
    public int updateExpressInfo(ExpressConfigBo expressConfigBo) {
        if(!StringUtils.isNumber(expressConfigBo.getCskaoyanmall_express_freight_min())||!StringUtils.isNumber(expressConfigBo.getCskaoyanmall_express_freight_value())){
            return 500;
        }
        String path="cskaoyan_mall_express_";
        String updateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        systemMapper.updateValueByName(path+"freight_min", expressConfigBo.getCskaoyanmall_express_freight_min(),updateTime);
        systemMapper.updateValueByName(path+"freight_value", expressConfigBo.getCskaoyanmall_express_freight_value(),updateTime);
        return 0;
    }

    /**
     * 订单配置
     * @return
     */
    @Override
    public OrderConfigVo getOrderConfig() {
        String path="cskaoyan_mall_order_";
        List<String> configLikeName = systemMapper.getConfigLikeName(path);
        OrderConfigVo orderConfigVo = OrderConfigVo.createOrderConfigVo(configLikeName);
        return orderConfigVo;
    }

    @Override
    public int updateOrderConfig(OrderConfigBo orderConfigBo) {
        String path="cskaoyan_mall_order_";
        String updateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        systemMapper.updateValueByName(path+"unconfirm",orderConfigBo.getCskaoyanmall_order_unconfirm(),updateTime);
        systemMapper.updateValueByName(path+"unpaid",orderConfigBo.getCskaoyanmall_order_unpaid(),updateTime);
        systemMapper.updateValueByName(path+"comment",orderConfigBo.getCskaoyanmall_order_comment(),updateTime);
        return 0;
    }

    /**
     * 微信配置
     * @return
     */
    @Override
    public WxConfigVo getWxConfig() {
        String path="cskaoyan_mall_wx_";
        List<String> configLikeName = systemMapper.getConfigLikeName(path);
        WxConfigVo wxConfigVo = WxConfigVo.createWxConfigVo(configLikeName);
        return wxConfigVo;
    }

    @Override
    public int updateWxConfig(WxConfigBo wxConfigBo) {
        String path="cskaoyan_mall_wx_";
        String updateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        systemMapper.updateValueByName(path+"catlog_goods",wxConfigBo.getCskaoyanmall_wx_catlog_goods(),updateTime);
        systemMapper.updateValueByName(path+"catlog_list",wxConfigBo.getCskaoyanmall_wx_catlog_list(),updateTime);
        systemMapper.updateValueByName(path+"index_brand",wxConfigBo.getCskaoyanmall_wx_index_brand(),updateTime);
        systemMapper.updateValueByName(path+"index_hot",wxConfigBo.getCskaoyanmall_wx_index_hot(),updateTime);
        systemMapper.updateValueByName(path+"index_new",wxConfigBo.getCskaoyanmall_wx_index_new(),updateTime);
        systemMapper.updateValueByName(path+"index_topic",wxConfigBo.getCskaoyanmall_wx_index_topic(),updateTime);
        systemMapper.updateValueByName(path+"catlog_goods",wxConfigBo.getCskaoyanmall_wx_catlog_goods(),updateTime);
        return 0;
    }


}

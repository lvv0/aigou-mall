package com.cskaoyan.service;

import com.cskaoyan.model.vo.StatRespVo;

/**
 * @author ：lww
 * @description：
 * @date ：2021/8/12 19:47
 */
public interface StatService {

    StatRespVo getUserStat();
    StatRespVo getOrderStat();
    StatRespVo getGoodsStat();
}

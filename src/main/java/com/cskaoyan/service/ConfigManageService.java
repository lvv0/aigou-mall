package com.cskaoyan.service;

import com.cskaoyan.model.bo.ExpressConfigBo;
import com.cskaoyan.model.bo.MallConfigBo;
import com.cskaoyan.model.bo.OrderConfigBo;
import com.cskaoyan.model.bo.WxConfigBo;
import com.cskaoyan.model.vo.ExpressConfigVo;
import com.cskaoyan.model.vo.MallConfigVo;
import com.cskaoyan.model.vo.OrderConfigVo;
import com.cskaoyan.model.vo.WxConfigVo;

public interface ConfigManageService {
    MallConfigVo getMallInfo();
    int updateMallInfo(MallConfigBo mallConfigBo);
    ExpressConfigVo getExpressInfo();
    int updateExpressInfo(ExpressConfigBo expressConfigBo);
    OrderConfigVo getOrderConfig();
    int updateOrderConfig(OrderConfigBo orderConfigBo);
    WxConfigVo getWxConfig();
    int updateWxConfig(WxConfigBo wxConfigBo);


}

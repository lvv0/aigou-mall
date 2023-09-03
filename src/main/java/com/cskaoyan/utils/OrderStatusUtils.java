package com.cskaoyan.utils;

import com.cskaoyan.model.vo.WxOrderHandleOptionVo;

/**
 * @author huxudong
 * @version 1.0
 * @date 2021/8/14 11:33
 */
public class OrderStatusUtils {
    public static String getStatus(Short statusId,String comment){
        String status;
        switch (statusId){
            case 101:
                status = "未付款";
                break;
            case 102:
                status = "已取消";
                break;
            case 103:
                status = "已取消(系统)";
                break;
            case 201:
                status = "待发货";
                break;
            case 202:
                status = "订单取消，退款中";
                break;
            case 203:
                status = "已退款";
                break;
            case 301:
                status = "待收货";
                break;
            case 401:
                if (comment==null)
                    status = "待评价";
                else
                    if ("0".equals(comment))
                        status = "已完成";
                    else
                        status = "已退货";
                break;
            case 402:
                status = "已收货(系统)";
                break;
            default:
                status = "状态码不正确";
        }
        return status;
    }

    public static WxOrderHandleOptionVo getWxOrderHandleOptionVo(Short statusId,boolean comment){
        WxOrderHandleOptionVo wxOrderHandleOptionVo = new WxOrderHandleOptionVo();
        wxOrderHandleOptionVo.setCancel(statusId==101);
        wxOrderHandleOptionVo.setDelete(statusId==401);
        wxOrderHandleOptionVo.setPay(statusId==101);
        wxOrderHandleOptionVo.setComment(statusId==401&&comment);
        wxOrderHandleOptionVo.setConfirm(statusId==301);
        wxOrderHandleOptionVo.setRefund(statusId==201);
        wxOrderHandleOptionVo.setRebuy(statusId==401);
        wxOrderHandleOptionVo.setReturns(statusId==401&&comment);
        return wxOrderHandleOptionVo;
    }


}

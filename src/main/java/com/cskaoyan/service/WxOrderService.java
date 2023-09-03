package com.cskaoyan.service;

import com.cskaoyan.model.bean.OrderGoods;
import com.cskaoyan.model.bo.WxFeedbackSubmitBo;
import com.cskaoyan.model.bo.WxOrderCommentBo;
import com.cskaoyan.model.bo.WxOrderSubmitBo;
import com.cskaoyan.model.bo.WxReturnGoodsBo;
import com.cskaoyan.model.vo.WxOrderDetailVo;
import com.cskaoyan.model.vo.WxOrderListVo;

/**
 * @author huxudong
 * @version 1.0
 * @date 2021/8/14 11:28
 */
public interface WxOrderService {
    WxOrderListVo orderList(Short showType, Integer page, Integer size);

    WxOrderDetailVo orderDetail(Integer orderId);

    boolean orderCancel(Integer orderId);

    boolean orderConfirm(Integer orderId);

    boolean orderDelete(Integer orderId);

    boolean orderRefund(Integer orderId);

    OrderGoods orderGoods(Integer orderId, Integer goodsId);

    boolean orderComment(WxOrderCommentBo wxOrderCommentBo);

    int orderSubmit(WxOrderSubmitBo bo);

    boolean feedbackSubmit(WxFeedbackSubmitBo bo);

    boolean orderReturns(WxReturnGoodsBo bo, Integer orderId);
}

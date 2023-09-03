package com.cskaoyan.controller;

import com.cskaoyan.model.bean.OrderGoods;
import com.cskaoyan.model.bo.WxFeedbackSubmitBo;
import com.cskaoyan.model.bo.WxOrderCommentBo;
import com.cskaoyan.model.bo.WxOrderSubmitBo;
import com.cskaoyan.model.bo.WxReturnGoodsBo;
import com.cskaoyan.model.vo.BaseRespVo;
import com.cskaoyan.model.vo.WxOrderDetailVo;
import com.cskaoyan.model.vo.WxOrderIdVo;
import com.cskaoyan.model.vo.WxOrderListVo;
import com.cskaoyan.service.WxOrderService;
import com.cskaoyan.utils.UserInfoUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author huxudong
 * @version 1.0
 * @date 2021/8/14 10:39
 */
@RestController
@RequestMapping("wx")
public class WxOrderController {

    @Autowired
    WxOrderService wxOrderService;

    @RequestMapping("/order/list")
    public BaseRespVo orderList(Short showType, Integer page, Integer size) {
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        if(username==null){
            return BaseRespVo.fail("请先登录");
        }

        WxOrderListVo vo = wxOrderService.orderList(showType, page, size);
        return BaseRespVo.ok(vo);
    }

    @RequestMapping("/order/detail")
    public BaseRespVo orderDetail(Integer orderId) {
        WxOrderDetailVo vo = wxOrderService.orderDetail(orderId);
        return BaseRespVo.ok(vo);
    }

    @RequestMapping("/order/submit")
    public BaseRespVo orderSubmit(@RequestBody WxOrderSubmitBo bo) {
        int orderId = wxOrderService.orderSubmit(bo);
        if (orderId == -1) {
            return BaseRespVo.fail("库存不足");
        }
        WxOrderIdVo wxOrderIdVo = new WxOrderIdVo();
        wxOrderIdVo.setOrderId(orderId);
        return BaseRespVo.ok(wxOrderIdVo);
    }

    // TODO 支付订单
    @RequestMapping("/order/prepay")
    public BaseRespVo orderPrepay(@RequestBody WxOrderIdVo bo) {
        Integer orderId = bo.getOrderId();
        return BaseRespVo.fail4("订单不能支付");
    }

    @RequestMapping("/order/cancel")
    public BaseRespVo orderCancel(@RequestBody HashMap map) {
        Integer orderId = (Integer) map.get("orderId");
        boolean b = wxOrderService.orderCancel(orderId);
        if (b)
            return BaseRespVo.ok();
        else
            return BaseRespVo.fail("取消失败");
    }

    @RequestMapping("/order/confirm")
    public BaseRespVo orderConfirm(@RequestBody HashMap map) {
        Integer orderId = (Integer) map.get("orderId");
        boolean b = wxOrderService.orderConfirm(orderId);
        if (b)
            return BaseRespVo.ok();
        else
            return BaseRespVo.fail("确认失败");
    }

    @RequestMapping("/order/delete")
    public BaseRespVo orderDelete(@RequestBody HashMap map) {
        Integer orderId = (Integer) map.get("orderId");
        boolean b = wxOrderService.orderDelete(orderId);
        if (b)
            return BaseRespVo.ok();
        else
            return BaseRespVo.fail("删除失败");
    }

    // 退款还需要回滚商品数量
    @RequestMapping("/order/refund")
    public BaseRespVo orderRefund(@RequestBody HashMap map) {
        Integer orderId = (Integer) map.get("orderId");
        boolean b = wxOrderService.orderRefund(orderId);
        if (b)
            return BaseRespVo.ok();
        else
            return BaseRespVo.fail("退款失败");
    }

    @RequestMapping("/order/goods")
    public BaseRespVo orderGoods(Integer orderId, Integer goodsId) {
        OrderGoods orderGoods = wxOrderService.orderGoods(orderId, goodsId);
        return BaseRespVo.ok(orderGoods);
    }

    @RequestMapping("/order/comment")
    public BaseRespVo orderComment(@RequestBody WxOrderCommentBo wxOrderCommentBo) {
        boolean b = wxOrderService.orderComment(wxOrderCommentBo);
        if (b)
            return BaseRespVo.ok();
        else
            return BaseRespVo.fail("评论失败");
    }

    @RequestMapping("/feedback/submit")
    public BaseRespVo submitFeedback(@RequestBody WxFeedbackSubmitBo bo) {

        boolean b = wxOrderService.feedbackSubmit(bo);
        if (b)
            return BaseRespVo.ok();
        else
            return BaseRespVo.fail("反馈失败，请先登录");
    }

    @RequestMapping("/order/returns")
    public BaseRespVo returns(@RequestBody HashMap map, HttpSession session) {
        Integer orderId = (Integer) map.get("orderId");
        session.setAttribute("orderId", orderId);
        return BaseRespVo.ok();
    }

    @RequestMapping("/order/return1")
    public BaseRespVo return1(@RequestBody WxReturnGoodsBo bo, HttpSession session) {
        Integer orderId = (Integer) session.getAttribute("orderId");
        boolean b = wxOrderService.orderReturns(bo, orderId);
        if (b)
            return BaseRespVo.ok();
        else
            return BaseRespVo.fail("退货失败");
    }
}




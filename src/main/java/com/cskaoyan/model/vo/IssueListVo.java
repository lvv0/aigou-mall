package com.cskaoyan.model.vo;

import com.cskaoyan.model.bean.Issue;
import com.cskaoyan.model.bean.Log;

import java.util.List;

/**
 * @author huxudong
 * @version 1.0
 * @date 2021/8/13 11:25
 */
public class IssueListVo {

    /**
     * total : 5
     * items : [{"id":10,"question":"11？","answer":"22","addTime":"2021-08-11 23:23:08","updateTime":"2021-08-11 23:23:08","deleted":false},{"id":1,"question":"购买运费如何收取？","answer":"单笔订单金额（不含运费）满88元免邮费；不满88元，每单收取10元运费。\n(港澳台地区需满1","addTime":"2018-02-01 00:00:00","updateTime":"2021-08-05 11:13:05","deleted":false},{"id":2,"question":"使用什么快递发货？","answer":"严选默认使用顺丰快递发货（个别商品使用其他快递），配送范围覆盖全国大部分地区（港澳台地区除","addTime":"2018-02-01 00:00:00","updateTime":"2018-02-01 00:00:00","deleted":false},{"id":3,"question":"如何申请退货？","answer":"1.自收到商品之日起30日内，顾客可申请无忧退货，退款将原路返还，不同的银行处理时间不同，","addTime":"2018-02-01 00:00:00","updateTime":"2018-02-01 00:00:00","deleted":false},{"id":4,"question":"如何开具1发票？","answer":"1.如需开具普通发票，请在下单时选择\u201c我要开发票\u201d并填写相关信息（APP仅限2.4.0及以","addTime":"2018-02-01 00:00:00","updateTime":"2021-08-05 08:02:28","deleted":false}]
     */

    private Long total;
    private List<Issue> items;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<Issue> getItems() {
        return items;
    }

    public void setItems(List<Issue> items) {
        this.items = items;
    }

}

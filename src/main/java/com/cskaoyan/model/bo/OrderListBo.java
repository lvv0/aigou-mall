package com.cskaoyan.model.bo;

import lombok.Data;

/**
 * @author huxudong
 * @version 1.0
 * @date 2021/8/12 21:50
 */
@Data
public class OrderListBo {
    private Integer page;
    private Integer limit;
    private Short orderStatusArray;
    private String sort;
    private String order;
    private String userId;
    private String orderSn;

}

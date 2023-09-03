package com.cskaoyan.model.bo;

import lombok.Data;

/**
 * @author huxudong
 * @version 1.0
 * @date 2021/8/15 14:17
 */
@Data
public class WxOrderSubmitBo {
    private Integer addressId;
    private Integer cartId;
    private Integer couponId;
    private Integer grouponLinkId;
    private Integer grouponRulesId;
    private String message;

}

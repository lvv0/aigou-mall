package com.cskaoyan.model.vo;

import lombok.Data;

/**
 * @author huxudong
 * @version 1.0
 * @date 2021/8/14 14:26
 */
@Data
public class WxOrderHandleOptionVo {
    private Boolean cancel;
    private Boolean delete;
    private Boolean pay;
    private Boolean comment;
    private Boolean confirm;
    private Boolean refund;
    private Boolean rebuy;
    private Boolean returns;
}

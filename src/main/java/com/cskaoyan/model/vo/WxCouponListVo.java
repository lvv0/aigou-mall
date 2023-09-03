package com.cskaoyan.model.vo;

import lombok.Data;

import java.util.List;

@Data
public class WxCouponListVo {
    private List<WxCouponListDataVo> data;
    private Long count;

}

package com.cskaoyan.model.vo;

import com.cskaoyan.model.bean.Cart;
import lombok.Data;

import java.util.List;

@Data
public class WxCartIndexVo {
    private List<Cart> cartList;
    private WxCartTotalVo cartTotal;
}

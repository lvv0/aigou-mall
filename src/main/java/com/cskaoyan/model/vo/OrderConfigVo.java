package com.cskaoyan.model.vo;

import com.cskaoyan.model.bo.OrderConfigBo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author ：lww
 * @description：TODO
 * @date ：2021/8/12 16:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderConfigVo {

    private String cskaoyanmall_order_comment;
    private String cskaoyanmall_order_unconfirm;
    private String cskaoyanmall_order_unpaid;

    public static OrderConfigVo createOrderConfigVo(List<String> list){
        return new OrderConfigVo(list.get(0),list.get(1),list.get(2));
    }
}

package com.cskaoyan.model.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

/**
 * @author ：lww
 * @description：TODO
 * @date ：2021/8/12 16:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderConfigBo {
    @Min(value = 0,message = "参数只能是数字")
    private String cskaoyanmall_order_unconfirm;
    @Min(value = 0,message = "参数只能是数字")
    private String cskaoyanmall_order_unpaid;
    @Min(value = 0,message = "参数只能是数字")
    private String cskaoyanmall_order_comment;

}

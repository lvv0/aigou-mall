package com.cskaoyan.model.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

/**
 * @author ：lww
 * @description：
 * @date ：2021/8/12 16:22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpressConfigBo {
    @Min(value = 0,message = "参数必须是数字")
    private String cskaoyanmall_express_freight_min;
    @Min(value = 0,message = "参数必须是数字")
    private String cskaoyanmall_express_freight_value;
}

package com.cskaoyan.model.bo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * @author huxudong
 * @version 1.0
 * @date 2021/8/13 11:00
 */
@Data
public class OrderShipBo {

    private Integer orderId;

    @NotBlank(message = "快递公司名称不能为空")
    private String shipChannel;

    @Length(min=6, message = "快递编号最小长度6位")
    @Min(value = 0, message = "快递编号必须为数字")
    private String shipSn;
}

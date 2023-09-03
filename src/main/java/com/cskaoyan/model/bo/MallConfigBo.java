package com.cskaoyan.model.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

/**
 * @author ：lww
 * @description：
 * @date ：2021/8/12 15:37
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MallConfigBo {
    @NotBlank(message = "商场名称不能为空")
    private String cskaoyanmall_mall_name;

    @Length(min=5,max = 12,message = "qq号长度为5-12")
    @Min(value = 0, message = "qq号必须为数字")
    private String cskaoyanmall_mall_qq;

    @Length(min=11,max=11,message = "手机号长度为11位")
    @Min(value = 0, message = "手机号必须为数字")
    private String cskaoyanmall_mall_phone;

    @NotBlank(message = "商场地址不能为空")
    private String cskaoyanmall_mall_address;

}

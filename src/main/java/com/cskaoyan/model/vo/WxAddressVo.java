package com.cskaoyan.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ：lww
 * @description：TODO
 * @date ：2021/8/14 14:22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WxAddressVo {

    private Boolean isDefault;
    private String detailedAddress;
    private String name;
    private String mobile;
    private Integer id;


}

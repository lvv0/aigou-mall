package com.cskaoyan.model.vo;

import lombok.Data;

/**
 * @author ：lww
 * @description：TODO
 * @date ：2021/8/11 21:03
 */
@Data
public class WxAddressDetailVo {
    private String areaName;
    private Boolean isDefault;
    private Integer areaId;
    private String address;
    private String provinceName;
    private String cityName;
    private String name;
    private String mobile;
    private Integer id;
    private Integer cityId;
    private Integer userId;
    private Integer provinceId;

}

package com.cskaoyan.model.vo;

import lombok.Data;

/**
 * @author ：lww
 * @description：TODO
 * @date ：2021/8/11 21:03
 */
@Data
public class UserAddressVo {
    private String area;
    private Boolean isDefault;
    private Integer areaId;
    private String address;
    private String province;
    private String city;
    private String name;
    private String mobile;
    private Integer id;
    private Integer cityId;
    private Integer userId;
    private Integer provinceId;

}

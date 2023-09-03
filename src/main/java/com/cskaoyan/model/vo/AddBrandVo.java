package com.cskaoyan.model.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author huxudong
 * @version 1.0
 * @date 2021/8/12 14:39
 */
@Data
public class AddBrandVo {
    private Integer id;

    private String name;

    private String desc;

    private String picUrl;

    private BigDecimal floorPrice;

    private Date addTime;

    private Date updateTime;

}

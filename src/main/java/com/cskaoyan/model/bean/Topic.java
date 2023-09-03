package com.cskaoyan.model.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Topic {
    private Integer id;

    private String title;

    private String subtitle;

    @Min(value = 0, message = "参数必须是数字")
    private String price;

    private String readCount;

    private String picUrl;

    private Integer sortOrder;

    private String[] goods;

    private Date addTime;

    private Date updateTime;

    private Boolean deleted;

    private String content;

}
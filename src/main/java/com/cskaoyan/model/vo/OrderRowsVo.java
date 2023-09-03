package com.cskaoyan.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author ：lww
 * @description：
 * @date ：2021/8/12 20:37
 */
@Data
public class OrderRowsVo {
    private Double amount;
    private Integer customers;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date day;
    private Integer orders;
    private Double pcr;
}

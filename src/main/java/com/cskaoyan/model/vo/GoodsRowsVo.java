package com.cskaoyan.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author ：lww
 * @description：
 * @date ：2021/8/12 22:33
 */
@Data
public class GoodsRowsVo {
    private Double amount;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date day;
    private Integer orders;
    private Integer products;
}

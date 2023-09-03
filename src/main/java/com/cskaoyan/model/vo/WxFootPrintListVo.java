package com.cskaoyan.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
public class WxFootPrintListVo {

    /**
     * brief : 设计师原款，精致绣花
     * picUrl : http://yanxuan.nosdn.127.net/8ab2d3287af0cefa2cc539e40600621d.png
     * addTime : 2021-08-14 21:49:41
     * goodsId : 1006002
     * name : 轻奢纯棉刺绣水洗四件套
     * id : 584
     * retailPrice : 899
     */
    private String brief;
    private String picUrl;
    private String addTime;
    private Integer goodsId;
    private String name;
    private Integer id;
    private BigDecimal retailPrice;

}

package com.cskaoyan.model.bo;

import lombok.Data;

@Data
public class GoodsListBo {
    private Integer page;
    private Integer limit;
    private String goodsSn;
    private String name;
    private String sort;
    private String order;
}

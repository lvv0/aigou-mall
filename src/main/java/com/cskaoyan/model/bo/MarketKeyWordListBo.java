package com.cskaoyan.model.bo;

import lombok.Data;

@Data
public class MarketKeyWordListBo {
    Integer page;
    Integer limit;
    String keyword;
    String url;
    String sort;
    String order;
}

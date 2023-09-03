package com.cskaoyan.model.bo;

import lombok.Data;

@Data
public class BaseParamBo {
    Integer page;
    Integer limit;
    String sort;
    String order;
}

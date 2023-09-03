package com.cskaoyan.model.bo;

import lombok.Data;

/**
 * @author huxudong
 * @version 1.0
 * @date 2021/8/11 21:34
 */
@Data
public class BrandListBo {
    private Integer page;
    private Integer limit;
    private String id;
    private String name;
    private String sort;
    private String order;

}

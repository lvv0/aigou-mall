package com.cskaoyan.model.vo;

import com.cskaoyan.model.bean.Brand;
import com.cskaoyan.model.bean.Log;
import lombok.Data;

import java.util.List;

/**
 * @author huxudong
 * @version 1.0
 * @date 2021/8/11 21:04
 */

public class BrandListVo {


    private Long total;
    private List<Brand> items;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<Brand> getItems() {
        return items;
    }

    public void setItems(List<Brand> items) {
        this.items = items;
    }

}

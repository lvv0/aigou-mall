package com.cskaoyan.model.bo;

import lombok.Data;

import java.util.List;

@Data
public class WxCartCheckedBo {

    /**
     * productIds : [16]
     * isChecked : 1
     */

    private Integer isChecked;
    private List<Integer> productIds;

}

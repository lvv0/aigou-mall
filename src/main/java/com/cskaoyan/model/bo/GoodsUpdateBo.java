package com.cskaoyan.model.bo;

import com.cskaoyan.model.bean.Goods;
import com.cskaoyan.model.bean.GoodsAttribute;
import com.cskaoyan.model.bean.GoodsProduct;
import com.cskaoyan.model.bean.GoodsSpecification;
import lombok.Data;

import java.util.List;

@Data
public class GoodsUpdateBo {
    private Goods goods;
    private List<GoodsAttribute> attributes;
    private List<GoodsSpecification> specifications;
    private List<GoodsProduct> products;
}

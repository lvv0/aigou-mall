package com.cskaoyan.model.bo;

import java.util.List;

public class GoodsCreateBo {
    private GoodsBo goods;
    private List<AttributesBo> attributes;
    private List<SpecificationsBo> specifications;
    private List<ProductsBo> products;

    public void setGoods(GoodsBo goods) {
        this.goods = goods;
    }

    public void setAttributes(List<AttributesBo> attributes) {
        this.attributes = attributes;
    }

    public void setSpecifications(List<SpecificationsBo> specifications) {
        this.specifications = specifications;
    }

    public void setProducts(List<ProductsBo> products) {
        this.products = products;
    }

    public GoodsBo getGoods() {
        return goods;
    }

    public List<AttributesBo> getAttributes() {
        return attributes;
    }

    public List<SpecificationsBo> getSpecifications() {
        return specifications;
    }

    public List<ProductsBo> getProducts() {
        return products;
    }
}


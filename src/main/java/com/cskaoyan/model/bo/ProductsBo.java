package com.cskaoyan.model.bo;

import java.math.BigDecimal;
import java.util.List;

public class ProductsBo {
    private String number;
    private BigDecimal price;
    private Integer id;
    private String[] specifications;
    private String url;

    public void setNumber(String number) {
        this.number = number;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setSpecifications(String[] specifications) {
        this.specifications = specifications;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNumber() {
        return number;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getId() {
        return id;
    }

    public String[] getSpecifications() {
        return specifications;
    }

    public String getUrl() {
        return url;
    }
}

package com.cskaoyan.model.bo;

import java.math.BigDecimal;
import java.util.List;

public class GoodsBo {
    private String brief;
    private String keywords;
    private String goodsSn;
    private Boolean isNew;
    private String picUrl;
    private String unit;
    private Integer brandId;
    private String name;
    private BigDecimal counterPrice;
    private String detail;
    private Boolean isOnSale;
    private BigDecimal retailPrice;
    private String[] gallery;
    private Boolean isHot;
    private Integer categoryId;

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public void setGoodsSn(String goodsSn) {
        this.goodsSn = goodsSn;
    }

    public void setIsNew(Boolean isNew) {
        this.isNew = isNew;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCounterPrice(BigDecimal counterPrice) {
        this.counterPrice = counterPrice;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setIsOnSale(Boolean isOnSale) {
        this.isOnSale = isOnSale;
    }

    public void setRetailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
    }

    public void setGallery(String[] gallery) {
        this.gallery = gallery;
    }

    public void setIsHot(Boolean isHot) {
        this.isHot = isHot;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getBrief() {
        return brief;
    }

    public String getKeywords() {
        return keywords;
    }

    public String getGoodsSn() {
        return goodsSn;
    }

    public Boolean isIsNew() {
        return isNew;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public String getUnit() {
        return unit;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getCounterPrice() {
        return counterPrice;
    }

    public String getDetail() {
        return detail;
    }

    public Boolean isIsOnSale() {
        return isOnSale;
    }

    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    public String[] getGallery() {
        return gallery;
    }

    public Boolean isIsHot() {
        return isHot;
    }

    public Integer getCategoryId() {
        return categoryId;
    }
}

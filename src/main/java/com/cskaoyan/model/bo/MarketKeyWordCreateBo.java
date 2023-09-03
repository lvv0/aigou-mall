package com.cskaoyan.model.bo;

public class MarketKeyWordCreateBo {

    /**
     * isDefault : true
     * keyword : 12344444
     * url : baidi
     * isHot : true
     */
    private Boolean isDefault;
    private String keyword;
    private String url;
    private Boolean isHot;

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setIsHot(Boolean isHot) {
        this.isHot = isHot;
    }

    public Boolean isIsDefault() {
        return isDefault;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getUrl() {
        return url;
    }

    public Boolean isIsHot() {
        return isHot;
    }
}

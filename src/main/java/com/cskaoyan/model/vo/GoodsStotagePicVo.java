package com.cskaoyan.model.vo;

public class GoodsStotagePicVo {

    private Long size;
    private String addTime;
    private String name;
    private String updateTime;
    private Integer id;
    private String type;
    private String key;
    private String url;

    public void setSize(Long size) {
        this.size = size;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getSize() {
        return size;
    }

    public String getAddTime() {
        return addTime;
    }

    public String getName() {
        return name;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public Integer getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getKey() {
        return key;
    }

    public String getUrl() {
        return url;
    }
}

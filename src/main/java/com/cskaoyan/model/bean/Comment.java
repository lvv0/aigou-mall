package com.cskaoyan.model.bean;

import lombok.Data;

import java.util.Date;

@Data
public class Comment {
    private Integer id;

    private Integer valueId;

    private Byte type;

    private String content;

    private Integer userId;

    private Boolean hasPicture;

    private String[] picUrls;

    private Short star;

    private Date addTime;

    private Date updateTime;

    private Boolean deleted;

}
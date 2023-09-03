package com.cskaoyan.model.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Collect {
    private Integer id;

    private Integer userId;

    private Integer valueId;

    private Integer type;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date addTime;

    private Date updateTime;

    private Boolean deleted;
}
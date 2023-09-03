package com.cskaoyan.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class RoleVo {

    /**
     * id : 38
     * name : skrskr
     * desc : faafsaffa
     * addTime : 2021-08-12 20:58:08
     * updateTime : 2021-08-12 20:58:08
     */

    private Integer id;
    private String name;
    private String desc;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date addTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

}

package com.cskaoyan.model.vo;

import com.cskaoyan.model.bean.Admin;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class AdminVo {

    /**
     * id : 27
     * username : asdasw
     * password : $2a$10$2sceGqpi58MSiG6rr1KezO57yBwNOX5boKnkJOT6A6xzY6R9Fd0n6
     * avatar : http://182.92.235.201:8083/wx/storage/fetch/phhvwyhrver5jhhdo810.jpg
     * addTime : 2021-08-12 16:08:26
     * updateTime : 2021-08-12 16:08:26
     * roleIds : [3]
     */

    private Integer id;
    private String username;
    private String password;
    private String avatar;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date addTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    private Integer[] roleIds;

    public AdminVo() {
    }

    public AdminVo(Admin admin) {
        this.id = admin.getId();
        this.username = admin.getUsername();
        this.password = admin.getPassword();
        this.avatar = admin.getAvatar();
        this.addTime = admin.getAddTime();
        this.updateTime = admin.getUpdateTime();
        this.roleIds = admin.getRoleIds();
    }
}

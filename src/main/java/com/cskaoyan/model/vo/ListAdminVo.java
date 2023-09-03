package com.cskaoyan.model.vo;

import lombok.Data;

@Data
public class ListAdminVo {

    private int id;
    private String username;
    private String avatar;
    private Integer[] roleIds;
}

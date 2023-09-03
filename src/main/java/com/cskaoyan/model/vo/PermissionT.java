package com.cskaoyan.model.vo;

import lombok.Data;

@Data
public class PermissionT {
    private Integer id;
    private String label;
    private Integer fatherId;
    private String permission;
    private String api;
}

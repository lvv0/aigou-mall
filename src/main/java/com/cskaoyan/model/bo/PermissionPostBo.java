package com.cskaoyan.model.bo;

import lombok.Data;

import java.util.List;

@Data
public class PermissionPostBo {
    private List<String> permissions;
    private Integer roleId;
}

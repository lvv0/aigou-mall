package com.cskaoyan.model.vo;

import lombok.Data;

import java.util.List;

@Data
public class RolePermissionsVo {
    private List<SystemPermissions> systemPermissions;
    private List<String> assignedPermissions;
}

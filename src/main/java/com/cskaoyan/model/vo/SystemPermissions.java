package com.cskaoyan.model.vo;

import lombok.Data;

import java.util.List;

@Data
public class SystemPermissions {
    private String id;
    private String label;
    private List<Children> children;
}

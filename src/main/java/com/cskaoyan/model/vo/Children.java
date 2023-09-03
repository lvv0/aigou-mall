package com.cskaoyan.model.vo;

import lombok.Data;

import java.util.List;

@Data
public class Children {
    private String id;
    private String label;
    private List<Child> children;
}

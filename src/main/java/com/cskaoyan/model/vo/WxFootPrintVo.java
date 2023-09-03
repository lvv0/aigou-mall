package com.cskaoyan.model.vo;

import lombok.Data;

import java.util.List;

@Data
public class WxFootPrintVo {
    List<WxFootPrintListVo> footprintList;
    Long totalPages;
}

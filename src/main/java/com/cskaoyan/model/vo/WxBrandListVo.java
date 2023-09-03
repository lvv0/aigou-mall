package com.cskaoyan.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author ：lww
 * @description：TODO
 * @date ：2021/8/14 17:13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WxBrandListVo {
    List<WxBrandVo> brandList;
    Integer totalPages;
}

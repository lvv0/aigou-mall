package com.cskaoyan.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author ：lww
 * @description：
 * @date ：2021/8/12 19:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatRespVo<T> {

    private List<String> columns;
    private List<T> rows;

}

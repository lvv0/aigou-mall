package com.cskaoyan.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseRespData<T> {
    List<T> items;
    long total;

    public static <T> BaseRespData create(List<T> items, long total) {
        return new BaseRespData(items, total);
    }
}

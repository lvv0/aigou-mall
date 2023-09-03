package com.cskaoyan.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author ：lww
 * @description：TODO
 * @date ：2021/8/12 15:05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MallConfigVo {

    private String cskaoyanmall_mall_address;
    private String cskaoyanmall_mall_name;
    private String cskaoyanmall_mall_phone;
    private String cskaoyanmall_mall_qq;
    public static MallConfigVo createMallConfigVo(List<String> list){
        return new MallConfigVo(list.get(0),list.get(1),list.get(2),list.get(3));
    }

}

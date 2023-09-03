package com.cskaoyan.model.vo;

import com.alibaba.druid.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author ：lww
 * @description： 变量顺序排放的不可以动
 * @date ：2021/8/12 16:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpressConfigVo {
    private String cskaoyanmall_express_freight_min;
    private String cskaoyanmall_express_freight_value;
    public static ExpressConfigVo createExpressConfigVo(List<String> list){
        return new ExpressConfigVo(list.get(0),list.get(1));
    }
}

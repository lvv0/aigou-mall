package com.cskaoyan.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author ：lww
 * @description：
 * @date ：2021/8/12 17:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WxConfigVo {
    private String cskaoyanmall_wx_catlog_goods;
    private String cskaoyanmall_wx_catlog_list;
    private String cskaoyanmall_wx_index_brand;
    private String cskaoyanmall_wx_index_hot;
    private String cskaoyanmall_wx_index_new;
    private String cskaoyanmall_wx_index_topic;
    private String cskaoyanmall_wx_share;

    public static WxConfigVo createWxConfigVo(List<String> list){
        return new WxConfigVo(list.get(0),list.get(1),list.get(2),list.get(3),list.get(4),list.get(5),list.get(6));
    }


}

package com.cskaoyan.model.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.util.List;

/**
 * @author ：lww
 * @description：
 * @date ：2021/8/12 17:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WxConfigBo {
    @Min(value = 0,message = "首页配置参数必须是数字")
    private String cskaoyanmall_wx_catlog_goods;
    @Min(value = 0,message = "首页配置参数必须是数字")
    private String cskaoyanmall_wx_catlog_list;
    @Min(value = 0,message = "首页配置参数必须是数字")
    private String cskaoyanmall_wx_index_brand;
    @Min(value = 0,message = "首页配置参数必须是数字")
    private String cskaoyanmall_wx_index_hot;
    @Min(value = 0,message = "首页配置参数必须是数字")
    private String cskaoyanmall_wx_index_new;
    @Min(value = 0,message = "首页配置参数必须是数字")
    private String cskaoyanmall_wx_index_topic;

    private String cskaoyanmall_wx_share;


}

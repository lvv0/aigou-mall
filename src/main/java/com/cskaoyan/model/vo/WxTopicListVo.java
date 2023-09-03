package com.cskaoyan.model.vo;

import lombok.Data;

import java.util.List;

@Data
public class WxTopicListVo {

    List<WxTopicListDataVo> data;
    Long count;
}

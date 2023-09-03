package com.cskaoyan.model.vo;

import com.cskaoyan.model.bean.Goods;
import com.cskaoyan.model.bean.Topic;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class WxTopicDetailVo {
    Topic topic;
    List<Goods> goods;
}

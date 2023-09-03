package com.cskaoyan.service;

import com.cskaoyan.model.bean.Topic;
import com.cskaoyan.model.bo.BaseParamBo;
import com.cskaoyan.model.vo.BaseRespData;

/**
 * @Name : TopicService.java
 * @Time : 2021/8/12 23:40
 * @Author : Ashe
 * @Software : IntelliJ IDEA
 */
public interface TopicService {

    BaseRespData query(String title, String subtitle, BaseParamBo param);

    Topic update(Topic topic);

    void delete(Topic topic);

    Topic create(Topic topic);
}

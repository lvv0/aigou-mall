package com.cskaoyan.service;

import com.cskaoyan.mapper.TopicMapper;
import com.cskaoyan.model.bean.Coupon;
import com.cskaoyan.model.bean.CouponExample;
import com.cskaoyan.model.bean.Topic;
import com.cskaoyan.model.bean.TopicExample;
import com.cskaoyan.model.bo.BaseParamBo;
import com.cskaoyan.model.vo.BaseRespData;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Name : TopicServiceImpl.java
 * @Time : 2021/8/12 23:40
 * @Author : Ashe
 * @Software : IntelliJ IDEA
 */
@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    TopicMapper topicMapper;

    @Override
    public BaseRespData query(String title, String subtitle, BaseParamBo param) {

        PageHelper.startPage(param.getPage(), param.getLimit());

        TopicExample example = new TopicExample();

        example.setOrderByClause(param.getSort() + " " + param.getOrder());

        TopicExample.Criteria criteria = example.createCriteria();

        criteria.andDeletedEqualTo(false);

        if (title != null && !"".equals(title)) {
            criteria.andTitleLike("%" + title + "%");
        }

        if (subtitle != null && !"".equals(subtitle)) {
            criteria.andSubtitleLike("%" + subtitle + "%");
        }

        List<Topic> items = topicMapper.selectByExampleWithBLOBs(example);

        PageInfo<Topic> pageInfo = new PageInfo<>(items);

        long total = pageInfo.getTotal();

        return BaseRespData.create(items, total);

    }

    @Override
    public Topic update(Topic topic) {

        topicMapper.updateByPrimaryKeySelective(topic);

        Topic topicVo = topicMapper.selectByPrimaryKey(topic.getId());

        return topicVo;

    }

    @Override
    public void delete(Topic topic) {

        topicMapper.updateByPrimaryKeySelective(topic);

    }

    @Override
    public Topic create(Topic topic) {

        topicMapper.insertSelective(topic);

        return topic;

    }

}


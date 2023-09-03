package com.cskaoyan.service;

import com.cskaoyan.converter.StringToDateConverter;
import com.cskaoyan.mapper.CommentMapper;
import com.cskaoyan.mapper.TopicMapper;
import com.cskaoyan.mapper.UserMapper;
import com.cskaoyan.model.bean.Comment;
import com.cskaoyan.model.bean.Goods;
import com.cskaoyan.model.bean.Topic;
import com.cskaoyan.model.bean.TopicExample;
import com.cskaoyan.model.bo.WxTopicCommentBo;
import com.cskaoyan.model.vo.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class WxTopicServiceImpl implements WxTopicService {

    @Autowired
    TopicMapper topicMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    StringToDateConverter stringToDateConverter;

    @Override
    public WxTopicListVo list(Integer page, Integer size) {
        PageHelper.startPage(page,size);
        TopicExample topicExample = new TopicExample();
        TopicExample.Criteria criteria = topicExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        List<Topic> topics = topicMapper.selectByExampleWithBLOBs(topicExample);
        if (topics == null){
            return null;
        }
        PageInfo<Topic> topicPageInfo = new PageInfo<>(topics);
        long total = topicPageInfo.getTotal();
        ArrayList<WxTopicListDataVo> wxTopicListDataVos = new ArrayList<>();
        for (Topic topic : topics) {
            WxTopicListDataVo wxTopicListDataVo = new WxTopicListDataVo();
            wxTopicListDataVo.setId(topic.getId());
            wxTopicListDataVo.setPicUrl(topic.getPicUrl());
            wxTopicListDataVo.setPrice(topic.getPrice());
            wxTopicListDataVo.setReadCount(topic.getReadCount());
            wxTopicListDataVo.setSubtitle(topic.getSubtitle());
            wxTopicListDataVo.setTitle(topic.getTitle());
            wxTopicListDataVos.add(wxTopicListDataVo);
        }
        WxTopicListVo wxTopicListVo = new WxTopicListVo();
        wxTopicListVo.setData(wxTopicListDataVos);
        wxTopicListVo.setCount(total);
        return wxTopicListVo;
    }

    @Override
    public WxTopicDetailVo detail(Integer id) {
        Topic topic = topicMapper.selectByPrimaryKey(id);
        if (topic == null){
            return null;
        }
        ArrayList<Goods> goods = new ArrayList<>();
        WxTopicDetailVo wxTopicDetailVo = new WxTopicDetailVo(topic, goods);
        return wxTopicDetailVo;
    }

    @Override
    public List<Topic> related(Integer id) {
        PageHelper.startPage(1,4);
        TopicExample topicExample = new TopicExample();
        TopicExample.Criteria criteria = topicExample.createCriteria();
        criteria.andIdNotEqualTo(id);
        criteria.andDeletedEqualTo(false);
        List<Topic> topics = topicMapper.selectByExampleWithBLOBs(topicExample);
        return topics;
    }

    @Override
    public WxTopicCommentVo commentList(WxTopicCommentBo wxTopicCommentBo) {
        PageHelper.startPage(wxTopicCommentBo.getPage(),wxTopicCommentBo.getSize());
        WxTopicCommentVo wxTopicCommentVo = new WxTopicCommentVo();
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        WxTopicCommentUserInfoVo wxTopicCommentUserInfoVo1 = userMapper.selectNickNameAndAvatarByUserName(username);
        List<WxTopicCommentDataVo> wxTopicCommentDataVos = commentMapper.selectCommentAddTimeAndPicListAndContentByValueId(wxTopicCommentBo.getValueId());
        PageInfo<WxTopicCommentDataVo> wxTopicCommentDataVoPageInfo = new PageInfo<>(wxTopicCommentDataVos);
        long total = wxTopicCommentDataVoPageInfo.getTotal();
        wxTopicCommentVo.setData(wxTopicCommentDataVos);
        wxTopicCommentVo.setCount(total);
        wxTopicCommentVo.setCurrentPage(wxTopicCommentBo.getPage());
        return wxTopicCommentVo;
    }

    @Override
    public Comment commentPost(Comment comment) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = simpleDateFormat.format(new Date());
        Date convert = stringToDateConverter.convert(time);
        comment.setAddTime(convert);
        comment.setUpdateTime(convert);
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        Integer id = userMapper.selectUserIdByUsername(username);
        if (id != null){
            comment.setUserId(id);
        }else return null;
        int result = commentMapper.insertSelective(comment);
        if (result == 1){
            return comment;
        }
        return null;
    }
}

package com.cskaoyan.service;

import com.cskaoyan.model.bean.Comment;
import com.cskaoyan.model.bean.Topic;
import com.cskaoyan.model.bo.WxTopicCommentBo;
import com.cskaoyan.model.vo.WxTopicCommentVo;
import com.cskaoyan.model.vo.WxTopicDetailVo;
import com.cskaoyan.model.vo.WxTopicListVo;

import java.util.List;

public interface WxTopicService {
    WxTopicListVo list(Integer page, Integer size);

    WxTopicDetailVo detail(Integer id);

    List<Topic> related(Integer id);

    WxTopicCommentVo commentList(WxTopicCommentBo wxTopicCommentBo);

    Comment commentPost(Comment comment);
}

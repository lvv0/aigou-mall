package com.cskaoyan.controller;

import com.cskaoyan.model.bean.Comment;
import com.cskaoyan.model.bean.Topic;
import com.cskaoyan.model.bo.WxTopicCommentBo;
import com.cskaoyan.model.vo.BaseRespVo;
import com.cskaoyan.model.vo.WxTopicCommentVo;
import com.cskaoyan.model.vo.WxTopicDetailVo;
import com.cskaoyan.model.vo.WxTopicListVo;
import com.cskaoyan.service.WxTopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("wx")
public class WxTopicController {

    @Autowired
    WxTopicService wxTopicService;

    @RequestMapping("topic/list")
    public BaseRespVo list(Integer page,Integer size){
        WxTopicListVo wxTopicListVo = wxTopicService.list(page,size);
        if (wxTopicListVo != null){
            return BaseRespVo.ok(wxTopicListVo);
        }else return BaseRespVo.fail("服务器繁忙！");
    }

    @RequestMapping("topic/detail")
    public BaseRespVo detail(Integer id){
        WxTopicDetailVo wxTopicDetailVo = wxTopicService.detail(id);
        if (wxTopicDetailVo != null){
            return BaseRespVo.ok(wxTopicDetailVo);
        }else return BaseRespVo.fail("服务器繁忙！");
    }

    @RequestMapping("topic/related")
    public BaseRespVo related(Integer id){
        List<Topic> topics = wxTopicService.related(id);
        if (topics != null){
            return BaseRespVo.ok(topics);
        }else return BaseRespVo.fail("服务器繁忙！");
    }

    @RequestMapping("comment/list")
    public BaseRespVo commentList(WxTopicCommentBo wxTopicCommentBo){
        WxTopicCommentVo wxTopicCommentVo = wxTopicService.commentList(wxTopicCommentBo);
        if (wxTopicCommentVo != null){
            return BaseRespVo.ok(wxTopicCommentVo);
        }else return BaseRespVo.fail("服务器繁忙！");
    }

    @RequestMapping("comment/post")
    public BaseRespVo commentPost(@RequestBody Comment comment){
        Comment commentVo = wxTopicService.commentPost(comment);
        if (comment != null){
            return BaseRespVo.ok(commentVo);
        }else return BaseRespVo.fail("服务器繁忙！");
    }
}

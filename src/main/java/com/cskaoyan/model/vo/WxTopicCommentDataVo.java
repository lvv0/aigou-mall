package com.cskaoyan.model.vo;

import java.util.List;

public class WxTopicCommentDataVo {
    /**
     * userInfo : {"avatarUrl":"https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif?imageView2/1/w/80/h/80","nickName":"测试用户"}
     * addTime : 2021-08-14 18:51:34
     * picList : ["http://182.92.235.201:8083/wx/storage/fetch/foq98v3zszmnnepgwk41.jpg"]
     * content : 123
     */
    private WxTopicCommentUserInfoVo userInfo;
    private String addTime;
    private String[] picList;
    private String content;

    public void setUserInfo(WxTopicCommentUserInfoVo userInfo) {
        this.userInfo = userInfo;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public void setPicList(String[] picList) {
        this.picList = picList;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public WxTopicCommentUserInfoVo getUserInfo() {
        return userInfo;
    }

    public String getAddTime() {
        return addTime;
    }

    public String[] getPicList() {
        return picList;
    }

    public String getContent() {
        return content;
    }


}

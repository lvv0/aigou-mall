package com.cskaoyan.model.vo;

public class WxTopicCommentUserInfoVo {
    /**
     * avatarUrl : https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif?imageView2/1/w/80/h/80
     * nickName : 测试用户
     */
    private String avatarUrl;
    private String nickName;

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getNickName() {
        return nickName;
    }
}

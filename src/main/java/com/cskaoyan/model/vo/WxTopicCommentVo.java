package com.cskaoyan.model.vo;

import java.util.List;

public class WxTopicCommentVo {

    private List<WxTopicCommentDataVo> data;
    private Long count;
    private Integer currentPage;

    public void setData(List<WxTopicCommentDataVo> data) {
        this.data = data;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public List<WxTopicCommentDataVo> getData() {
        return data;
    }

    public Long getCount() {
        return count;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }


}


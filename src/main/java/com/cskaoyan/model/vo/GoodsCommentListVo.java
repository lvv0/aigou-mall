package com.cskaoyan.model.vo;

import com.cskaoyan.model.bean.Comment;

import java.util.List;

public class GoodsCommentListVo {

    private Long total;
    private List<Comment> items;

    public void setTotal(Long total) {
        this.total = total;
    }

    public void setItems(List<Comment> items) {
        this.items = items;
    }

    public Long getTotal() {
        return total;
    }

    public List<Comment> getItems() {
        return items;
    }

    
}

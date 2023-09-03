package com.cskaoyan.service;

import com.cskaoyan.model.bean.Comment;
import com.cskaoyan.model.bo.GoodsCommentListBo;
import com.cskaoyan.model.vo.GoodsCommentListVo;

public interface GoodsCommentService {
    GoodsCommentListVo list(GoodsCommentListBo goodsCommentListBo);

    boolean delete(Comment comment);
}

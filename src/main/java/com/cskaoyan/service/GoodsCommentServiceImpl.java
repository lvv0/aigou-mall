package com.cskaoyan.service;

import com.cskaoyan.mapper.CommentMapper;
import com.cskaoyan.mapper.GoodsCommentMapper;
import com.cskaoyan.mapper.StorageMapper;
import com.cskaoyan.model.bean.Comment;
import com.cskaoyan.model.bean.CommentExample;
import com.cskaoyan.model.bean.Storage;
import com.cskaoyan.model.bean.StorageExample;
import com.cskaoyan.model.bo.GoodsCommentListBo;
import com.cskaoyan.model.vo.GoodsCommentListVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GoodsCommentServiceImpl implements GoodsCommentService {

    @Autowired
    GoodsCommentMapper goodsCommentMapper;

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    StorageMapper storageMapper;

    @Override
    public GoodsCommentListVo list(GoodsCommentListBo goodsCommentListBo) {
        PageHelper.startPage(goodsCommentListBo.getPage(),goodsCommentListBo.getLimit());
        CommentExample commentExample = new CommentExample();
        commentExample.setOrderByClause(goodsCommentListBo.getSort() + " " + goodsCommentListBo.getOrder());
        CommentExample.Criteria criteria = commentExample.createCriteria();
        if (goodsCommentListBo.getUserId() != null){
            criteria.andUserIdEqualTo(goodsCommentListBo.getUserId());
        }
        if (goodsCommentListBo.getValueId() != null){
            criteria.andValueIdEqualTo(goodsCommentListBo.getValueId());
        }
        criteria.andDeletedEqualTo(false);
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        PageInfo<Comment> commentPageInfo = new PageInfo<>(comments);
        long total = commentPageInfo.getTotal();
        GoodsCommentListVo goodsCommentListVo = new GoodsCommentListVo();
        goodsCommentListVo.setTotal(total);
        goodsCommentListVo.setItems(comments);
        return goodsCommentListVo;
    }

    @Override
    public boolean delete(Comment comment) {
        if (comment.getHasPicture()){
            String[] picUrls = comment.getPicUrls();
            for (int i = 0; i < picUrls.length; i++) {
                StorageExample storageExample = new StorageExample();
                StorageExample.Criteria criteria = storageExample.createCriteria();
                criteria.andUrlEqualTo(picUrls[i]);
                Storage storage = new Storage();
                storage.setDeleted(true);
                int j = storageMapper.updateByExampleSelective(storage, storageExample);
                if (j < 1){
                    return false;
                }
            }
        }
        int result = commentMapper.updateDeleted(comment.getId());
        if (result < 1){
            return false;
        }
        return true;
    }
}

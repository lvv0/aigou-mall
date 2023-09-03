package com.cskaoyan.model.bo;

import lombok.Data;

import java.util.List;

/**
 * @author huxudong
 * @version 1.0
 * @date 2021/8/14 23:02
 */
@Data
public class WxOrderCommentBo {
    private String content;
    private Boolean hasPicture;
    private Integer orderGoodsId;
    private Short star;
    private String[] picUrls;
}

package com.cskaoyan.model.bo;

import lombok.Data;

/**
 * @author huxudong
 * @version 1.0
 * @date 2021/8/16 9:23
 */
@Data
public class WxFeedbackSubmitBo {
    private String content;
    private String feedType;
    private Boolean hasPicture;
    private String mobile;
    private String[] picUrls;
}

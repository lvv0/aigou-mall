package com.cskaoyan.model.bo;

import lombok.Data;

/**
 * @author huxudong
 * @version 1.0
 * @date 2021/8/12 15:22
 */
@Data
public class CategoryBoChildren {
    private Integer id;

    private String name;

    private String keywords;

    private String desc;

    private String iconUrl;

    private String picUrl;

    private String level;

    private Integer pid;
}

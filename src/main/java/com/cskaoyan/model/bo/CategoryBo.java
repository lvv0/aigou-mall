package com.cskaoyan.model.bo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author huxudong
 * @version 1.0
 * @date 2021/8/12 15:03
 */
@Data
public class CategoryBo {
    private Integer id;

    private String name;

    private String keywords;

    private String desc;

    private String iconUrl;

    private String picUrl;

    private String level;

    private Integer pid;

    private List<CategoryBoChildren> children;


}

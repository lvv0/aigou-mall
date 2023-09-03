package com.cskaoyan.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author huxudong
 * @version 1.0
 * @date 2021/8/12 17:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCategoryVo {
    private Integer id;

    private String name;

    private String keywords;

    private String desc;

    private Integer pid;

    private String iconUrl;

    private String picUrl;

    private String level;

    private Date addTime;

    private Date updateTime;
}

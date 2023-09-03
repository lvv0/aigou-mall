package com.cskaoyan.model.vo;

import com.cskaoyan.model.bean.CouponUser;
import com.cskaoyan.model.bean.Goods;
import com.cskaoyan.model.bean.Groupon;
import com.cskaoyan.model.bean.GrouponRules;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Name : GrouponListRecordVo.java
 * @Time : 2021/8/13 15:22
 * @Author : Ashe
 * @Software : IntelliJ IDEA
 */
@Data
public class GrouponListRecordVo implements Serializable {

    private Groupon groupon;

    private Goods goods;

    private GrouponRules rules;

    private List<?> subGroupons = new ArrayList<>();

}

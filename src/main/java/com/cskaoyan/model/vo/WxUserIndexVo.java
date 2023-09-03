package com.cskaoyan.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Name : WxUserIndexVo.java
 * @Time : 2021/8/15 12:53
 * @Author : Ashe
 * @Software : IntelliJ IDEA
 */
@Data
public class WxUserIndexVo {

    private OrderBean order;

    @Data
    public static class OrderBean {

        private Long unpaid;

        private Long unship;

        private Long unrecv;

        private Long uncomment;

    }

}

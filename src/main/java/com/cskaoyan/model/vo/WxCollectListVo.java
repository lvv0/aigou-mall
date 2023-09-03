package com.cskaoyan.model.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class WxCollectListVo {

    private Integer totalPages;
    private List<CollectListBean> collectList;

    @Data
    public static class CollectListBean {

        private String brief;
        private String picUrl;
        private Integer valueId;
        private String name;
        private Integer id;
        private Integer type;
        private BigDecimal retailPrice;

    }
}

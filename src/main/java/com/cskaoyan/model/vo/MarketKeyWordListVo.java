package com.cskaoyan.model.vo;

import com.cskaoyan.model.bean.Keyword;
import lombok.Data;

import java.util.List;

@Data
public class MarketKeyWordListVo {
    private Long total;
    private List<Keyword> items;

}

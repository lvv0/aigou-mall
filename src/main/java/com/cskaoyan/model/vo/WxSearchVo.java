package com.cskaoyan.model.vo;

import com.cskaoyan.model.bean.Keyword;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author ：lww
 * @description：
 * @date ：2021/8/14 21:40
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WxSearchVo {
    private Keyword defaultKeyword;
    private List<WxKeywordVo> historyKeywordList;
    private List<Keyword> hotKeywordList;

}

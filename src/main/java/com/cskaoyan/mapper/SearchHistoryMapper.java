package com.cskaoyan.mapper;

import com.cskaoyan.model.bean.SearchHistory;
import com.cskaoyan.model.bean.SearchHistoryExample;
import com.cskaoyan.model.bo.BaseParamBo;
import com.cskaoyan.model.vo.WxKeywordVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SearchHistoryMapper {
    long countByExample(SearchHistoryExample example);

    int deleteByExample(SearchHistoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SearchHistory record);

    int insertSelective(SearchHistory record);

    List<SearchHistory> selectByExample(SearchHistoryExample example);

    SearchHistory selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SearchHistory record, @Param("example") SearchHistoryExample example);

    int updateByExample(@Param("record") SearchHistory record, @Param("example") SearchHistoryExample example);

    int updateByPrimaryKeySelective(SearchHistory record);

    int updateByPrimaryKey(SearchHistory record);

    List<WxKeywordVo> selectHistoryKeywordList(Integer userId);

}
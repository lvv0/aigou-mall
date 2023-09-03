package com.cskaoyan.service;

import com.alibaba.druid.util.StringUtils;
import com.cskaoyan.mapper.KeywordMapper;
import com.cskaoyan.mapper.SearchHistoryMapper;
import com.cskaoyan.mapper.UserMapper;
import com.cskaoyan.model.bean.*;
import com.cskaoyan.model.vo.WxKeywordVo;
import com.cskaoyan.model.vo.WxSearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WxSearchServiceImpl implements WxSearchService {

    @Autowired
    KeywordMapper keywordMapper;
    @Autowired
    SearchHistoryMapper searchHistoryMapper;
    @Autowired
    UserMapper userMapper;

    /**
     * 搜索index
     * @return
     */
    @Override
    public WxSearchVo getSearchIndex(String username) {
        Integer userId=null;

        if(!StringUtils.isEmpty(username)){
             userId = userMapper.getIdByName(username);
        }

        KeywordExample keywordExample = new KeywordExample();
        KeywordExample.Criteria criteria = keywordExample.createCriteria();
        criteria.andIsDefaultEqualTo(true);
        criteria.andDeletedEqualTo(false);
        List<Keyword> keywords = keywordMapper.selectByExample(keywordExample);
        Keyword defaultKeyword = new Keyword();
        if(keywords.size()>0){
            defaultKeyword=keywords.get(0);
        }

        List<WxKeywordVo> historyKeywordList=new ArrayList<>();
        if(userId!=null){
             historyKeywordList = searchHistoryMapper.selectHistoryKeywordList(userId);

        }
        keywordExample.clear();
        KeywordExample.Criteria hotKeywordCriteria =keywordExample.createCriteria();
        hotKeywordCriteria.andIsHotEqualTo(true);
        hotKeywordCriteria.andDeletedEqualTo(false);
        List<Keyword> hotKeywords=keywordMapper.selectByExample(keywordExample);
        WxSearchVo wxSearchVo = new WxSearchVo(defaultKeyword, historyKeywordList, hotKeywords);

        return wxSearchVo;
    }

    /**
     * search help
     * @param keyword
     * @return
     */
    @Override
    public List<String> getKeyHelp(String keyword) {
        List<String> keywordList = keywordMapper.selectKeywordLikeKey(keyword);
        return keywordList;
    }

    /**
     * 清除搜索历史
     * @return
     */
    @Override
    public int clearSearchHistory(String username) {
        int userId = userMapper.getIdByName(username);
        SearchHistoryExample searchHistoryExample = new SearchHistoryExample();
        SearchHistoryExample.Criteria criteria = searchHistoryExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        criteria.andUserIdEqualTo(userId);
        SearchHistory searchHistory = new SearchHistory();
        searchHistory.setDeleted(true);
        int i = searchHistoryMapper.updateByExampleSelective(searchHistory, searchHistoryExample);
        return i;
    }
}

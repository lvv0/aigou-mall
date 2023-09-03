package com.cskaoyan.service;

import com.alibaba.druid.util.StringUtils;
import com.cskaoyan.mapper.*;
import com.cskaoyan.model.bean.*;
import com.cskaoyan.model.bo.BaseParamBo;
import com.cskaoyan.model.vo.BaseRespVo;
import com.cskaoyan.model.vo.UserAddressVo;
import com.cskaoyan.model.vo.UserManageListVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ：lww
 * @description：
 * @date ：2021/8/11 20:22
 */
@Service
@Transactional
public class UserManageServiceImpl implements UserManageService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    AddressMapper addressMapper;
    @Autowired
    RegionMapper regionMapper;
    @Autowired
    CollectMapper collectMapper;
    @Autowired
    FootPrintMapper footPrintMapper;
    @Autowired
    SearchHistoryMapper searchHistoryMapper;
    @Autowired
    FeedbackMapper feedbackMapper;

    /**
     * 会员管理
     * @param username
     * @param mobile
     * @param baseParamBo
     * @return
     */
    @Override
    public UserManageListVo getUserList(String username, String mobile, BaseParamBo baseParamBo) {
        PageHelper.startPage(baseParamBo.getPage(),baseParamBo.getLimit());
        UserExample userExample = new UserExample();

        userExample.setOrderByClause(baseParamBo.getSort()+" "+baseParamBo.getOrder());

        UserExample.Criteria criteria = userExample.createCriteria();

        if(!StringUtils.isEmpty(username)){
            criteria.andUsernameLike("%"+username+"%");
        }
        if(!StringUtils.isEmpty(mobile)){
            criteria.andMobileEqualTo(mobile);
        }
        List<User> items = userMapper.selectByExample(userExample);
        PageInfo<User> userPageInfo = new PageInfo<>(items);
        long total = userPageInfo.getTotal();
        UserManageListVo userManageListVo = new UserManageListVo<User>(items, total);
        return userManageListVo;
    }

    /**
     * 地址管理
     * @param userId
     * @param username
     * @param baseParamBo
     * @return
     */
    @Override
    public UserManageListVo<UserAddressVo> getAddressList(String userId, String username, BaseParamBo baseParamBo) {

        PageHelper.startPage(baseParamBo.getPage(),baseParamBo.getLimit());
        Integer id = getInteger(userId);
        List<UserAddressVo> userAddressVos = addressMapper.selectAddressInfo(id, username, baseParamBo);
        for (UserAddressVo userAddressVo : userAddressVos) {
            String province = regionMapper.selectNameById(userAddressVo.getProvinceId());
            String city = regionMapper.selectNameById(userAddressVo.getCityId());
            String area = regionMapper.selectNameById(userAddressVo.getAreaId());
            userAddressVo.setProvince(province);
            userAddressVo.setCity(city);
            userAddressVo.setArea(area);
        }
        PageInfo<UserAddressVo> addressVoPageInfo = new PageInfo<>(userAddressVos);
        long total = addressVoPageInfo.getTotal();
        UserManageListVo<UserAddressVo> addressListVo = new UserManageListVo<>(userAddressVos, total);
        return addressListVo;
    }

    /**
     * 对请求参数做判断
     * @param userId
     * @return
     */
    private Integer getInteger(String userId) {
        Integer id=null;
        if(!StringUtils.isEmpty(userId)&&!StringUtils.isNumber(userId)){
            id=-1;
        }else if(StringUtils.isEmpty(userId)){
            return null;
        }else {
            id=Integer.parseInt(userId);
        }
        return id;
    }

    /**
     * 会员收藏
     * @param userId
     * @param valueId
     * @param baseParamBo
     * @return
     */
    @Override
    public UserManageListVo getCollectList(String userId, String valueId, BaseParamBo baseParamBo) {
        PageHelper.startPage(baseParamBo.getPage(),baseParamBo.getLimit());
        CollectExample collectExample = new CollectExample();
        collectExample.setOrderByClause(baseParamBo.getSort()+" "+baseParamBo.getOrder());
        CollectExample.Criteria criteria = collectExample.createCriteria();

       if(!StringUtils.isEmpty(userId)){
           if(!StringUtils.isNumber(userId)){
               criteria.andUserIdEqualTo(-1);
           }else {
               criteria.andUserIdEqualTo(Integer.parseInt(userId));
           }
       }
        if(!StringUtils.isEmpty(valueId)){
            if(!StringUtils.isNumber(userId)){
                criteria.andValueIdEqualTo(-1);
            }else {
                criteria.andValueIdEqualTo(Integer.parseInt(userId));
            }
        }
        criteria.andDeletedEqualTo(false);
        List<Collect> collects = collectMapper.selectByExample(collectExample);
        PageInfo<Collect> collectPageInfo = new PageInfo<>(collects);
        long total = collectPageInfo.getTotal();
        UserManageListVo<Collect> collectListVo = new UserManageListVo<>(collects, total);
        return collectListVo;
    }

    /**
     * 用户足迹
     * @param userId
     * @param goodsId
     * @param baseParamBo
     * @return
     */
    @Override
    public UserManageListVo getFootprintList(String userId, String goodsId, BaseParamBo baseParamBo) {
        PageHelper.startPage(baseParamBo.getPage(),baseParamBo.getLimit());
        FootPrintExample footPrintExample = new FootPrintExample();
        footPrintExample.setOrderByClause(baseParamBo.getSort()+" "+baseParamBo.getOrder());
        FootPrintExample.Criteria criteria = footPrintExample.createCriteria();
        if(!StringUtils.isEmpty(userId)){
            if(!StringUtils.isNumber(userId)){
                criteria.andUserIdEqualTo(-1);
            }else {
                criteria.andUserIdEqualTo(Integer.parseInt(userId));
            }
        }
        if(!StringUtils.isEmpty(goodsId)){
            if(!StringUtils.isNumber(goodsId)){
                criteria.andGoodsIdEqualTo(-1);
            }else {
                criteria.andGoodsIdEqualTo(Integer.parseInt(goodsId));
            }
        }
        //criteria.andDeletedEqualTo(false);
        List<FootPrint> footPrints = footPrintMapper.selectByExample(footPrintExample);
        PageInfo<FootPrint> pageInfo = new PageInfo<>(footPrints);
        long total = pageInfo.getTotal();
        UserManageListVo<FootPrint> footPrintListVo = new UserManageListVo<>(footPrints, total);
        return footPrintListVo;
    }

    /**
     * 历史搜索
     * @param userId
     * @param keyword
     * @param baseParamBo
     * @return
     */
    @Override
    public UserManageListVo getSearchHistoryList(String userId, String keyword, BaseParamBo baseParamBo) {
        PageHelper.startPage(baseParamBo.getPage(),baseParamBo.getLimit());
        SearchHistoryExample searchHistoryExample = new SearchHistoryExample();
        searchHistoryExample.setOrderByClause(baseParamBo.getSort()+" "+baseParamBo.getOrder());
        SearchHistoryExample.Criteria criteria = searchHistoryExample.createCriteria();
        if(!StringUtils.isEmpty(userId)){
            if(!StringUtils.isNumber(userId)){
                criteria.andUserIdEqualTo(-1);
            }else {
                criteria.andUserIdEqualTo(Integer.parseInt(userId));
            }
        }
        if(!StringUtils.isEmpty(keyword)){

            criteria.andKeywordLike("%"+keyword+"%");
        }
        List<SearchHistory> historyList = searchHistoryMapper.selectByExample(searchHistoryExample);
        PageInfo<SearchHistory> pageInfo = new PageInfo<>(historyList);
        long total = pageInfo.getTotal();
        UserManageListVo<SearchHistory> historyListVo = new UserManageListVo<>(historyList, total);
        return historyListVo;
    }

    /**
     * 意见反馈
     * @param username
     * @param feedBackId
     * @param baseParamBo
     * @return
     */
    @Override
    public UserManageListVo getFeedBackList(String username, String feedBackId, BaseParamBo baseParamBo) {
        PageHelper.startPage(baseParamBo.getPage(),baseParamBo.getLimit());
        FeedbackExample feedbackExample = new FeedbackExample();
        feedbackExample.setOrderByClause(baseParamBo.getSort()+" "+baseParamBo.getOrder());
        FeedbackExample.Criteria criteria = feedbackExample.createCriteria();
        if(!StringUtils.isEmpty(feedBackId)){
            if(!StringUtils.isNumber(feedBackId)){
                criteria.andUserIdEqualTo(-1);
            }else {
                criteria.andIdEqualTo(Integer.parseInt(feedBackId));
            }
        }
        if(!StringUtils.isEmpty(username)){

            criteria.andUsernameLike("%"+username+"%");
        }
        criteria.andDeletedEqualTo(false);
        List<Feedback> feedbackList = feedbackMapper.selectByExample(feedbackExample);
        PageInfo<Feedback> pageInfo = new PageInfo<>(feedbackList);
        long total = pageInfo.getTotal();
        return new UserManageListVo(feedbackList,total);
    }
}

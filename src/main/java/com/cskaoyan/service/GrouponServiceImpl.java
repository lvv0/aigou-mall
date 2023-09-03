package com.cskaoyan.service;

import com.cskaoyan.mapper.GoodsMapper;
import com.cskaoyan.mapper.GrouponMapper;
import com.cskaoyan.mapper.GrouponRulesMapper;
import com.cskaoyan.model.bean.Goods;
import com.cskaoyan.model.bean.GrouponRules;
import com.cskaoyan.model.bean.GrouponRulesExample;
import com.cskaoyan.model.bo.BaseParamBo;
import com.cskaoyan.model.bo.GrouponRulesBo;
import com.cskaoyan.model.vo.BaseRespData;
import com.cskaoyan.model.vo.GrouponListRecordVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Name : GrouponServiceImpl.java
 * @Time : 2021/8/13 11:53
 * @Author : Ashe
 * @Software : IntelliJ IDEA
 */
@Service
public class GrouponServiceImpl implements GrouponService {

    @Autowired
    GrouponMapper grouponMapper;

    @Autowired
    GrouponRulesMapper grouponRulesMapper;

    @Autowired
    GoodsMapper goodsMapper;

    @Override
    public BaseRespData query(Integer goodsId, BaseParamBo param) {

        PageHelper.startPage(param.getPage(), param.getLimit());

        GrouponRulesExample example = new GrouponRulesExample();

        example.setOrderByClause(param.getSort() + " " + param.getOrder());

        GrouponRulesExample.Criteria criteria = example.createCriteria();

        criteria.andDeletedEqualTo(false);

        if (goodsId != null) {
            criteria.andGoodsIdEqualTo(goodsId);
        }

        List<GrouponRules> items = grouponRulesMapper.selectByExample(example);

        PageInfo<GrouponRules> pageInfo = new PageInfo<>(items);

        long total = pageInfo.getTotal();

        return BaseRespData.create(items, total);

    }

    @Override
    public Integer update(GrouponRulesBo grouponRulesBo) {

        GrouponRules grouponRules = new GrouponRules();

        grouponRules.setId(grouponRulesBo.getId());

        grouponRules.setGoodsId(Integer.parseInt(grouponRulesBo.getGoodsId()));

        grouponRules.setGoodsName(grouponRulesBo.getGoodsName());

        grouponRules.setPicUrl(grouponRulesBo.getPicUrl());

        grouponRules.setDiscount(new BigDecimal(grouponRulesBo.getDiscount()));

        grouponRules.setDiscountMember(Integer.parseInt(grouponRulesBo.getDiscountMember()));

        grouponRules.setAddTime(grouponRulesBo.getAddTime());

        grouponRules.setUpdateTime(grouponRulesBo.getUpdateTime());

        grouponRules.setExpireTime(grouponRulesBo.getExpireTime());

        grouponRules.setDeleted(grouponRulesBo.getDeleted());

        GrouponRules grouponRules1 = grouponRulesMapper.selectByPrimaryKey(grouponRules.getId());

        if (!grouponRules1.getGoodsId().equals(grouponRules.getGoodsId())){
            return 0;
        }

        int i = grouponRulesMapper.updateByPrimaryKeySelective(grouponRules);

        return i;
    }

    @Override
    public void delete(GrouponRules grouponRules) {

        grouponRulesMapper.updateByPrimaryKeySelective(grouponRules);

    }

    @Override
    public GrouponRules create(GrouponRulesBo grouponRulesBo) {

        GrouponRules grouponRules = new GrouponRules();

        grouponRules.setId(grouponRulesBo.getId());

        grouponRules.setGoodsId(Integer.parseInt(grouponRulesBo.getGoodsId()));

        grouponRules.setGoodsName(grouponRulesBo.getGoodsName());

        grouponRules.setPicUrl(grouponRulesBo.getPicUrl());

        grouponRules.setDiscount(new BigDecimal(grouponRulesBo.getDiscount()));

        grouponRules.setDiscountMember(Integer.parseInt(grouponRulesBo.getDiscountMember()));

        grouponRules.setAddTime(grouponRulesBo.getAddTime());

        grouponRules.setUpdateTime(grouponRulesBo.getUpdateTime());

        grouponRules.setExpireTime(grouponRulesBo.getExpireTime());

        grouponRules.setDeleted(grouponRulesBo.getDeleted());

        Goods goods = goodsMapper.selectByPrimaryKey(grouponRules.getGoodsId());

        if (goods == null) {
            return null;
        }

        grouponRules.setGoodsName(goods.getName());

        grouponRules.setPicUrl(goods.getPicUrl());

        grouponRulesMapper.insertSelective(grouponRules);

        return grouponRules;

    }

    @Override
    public BaseRespData listRecord(Integer goodsId, BaseParamBo param) {

        PageHelper.startPage(param.getPage(), param.getLimit());

        List<GrouponListRecordVo> items = grouponMapper.queryListRecord(goodsId);

        PageInfo<GrouponListRecordVo> pageInfo = new PageInfo<>(items);

        long total = pageInfo.getTotal();

        return BaseRespData.create(items, total);

    }

}

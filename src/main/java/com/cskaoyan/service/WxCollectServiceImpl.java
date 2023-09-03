package com.cskaoyan.service;

import com.cskaoyan.mapper.CollectMapper;
import com.cskaoyan.mapper.GoodsMapper;
import com.cskaoyan.model.bean.Collect;
import com.cskaoyan.model.bean.CollectExample;
import com.cskaoyan.model.bean.Goods;
import com.cskaoyan.model.vo.WxCollectListVo;
import com.cskaoyan.utils.UserInfoUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WxCollectServiceImpl implements WxCollectService {
    @Autowired
    CollectMapper collectMapper;

    @Autowired
    GoodsMapper goodsMapper;

    @Autowired
    UserInfoUtils userInfoUtils;

    @Override
    public Map<String, String> addOrDelete(Collect collect) {
        Integer userId = userInfoUtils.getUserId();
        if (collect.getType() != 0 && collect.getType() != 1) {
            return null;
        }
        CollectExample collectExample = new CollectExample();
        CollectExample.Criteria criteria = collectExample.createCriteria();
        criteria.andValueIdEqualTo(collect.getValueId());
        criteria.andTypeEqualTo((byte) collect.getType().intValue());
        criteria.andUserIdEqualTo(userId);
        List<Collect> collects = collectMapper.selectByExample(collectExample);
        Map<String, String> map = new HashMap<>();
        if (collects.size() > 0) {
            Collect collect1 = new Collect();
            collect1.setDeleted(!collects.get(0).getDeleted());
            collect1.setId(collects.get(0).getId());
            collect1.setUpdateTime(new Date());
            int i = collectMapper.updateByPrimaryKeySelective(collect1);
            if (i == 0) {
                return null;
            }
            if (collects.get(0).getDeleted()) {
                map.put("type", "delete");
                return map;
            }
            map.put("type", "add");
            return map;
        }

        Collect collect2 = new Collect();
        collect2.setUserId(userId);
        collect2.setValueId(collect.getValueId());
        collect2.setType(collect.getType());
        collect2.setAddTime(new Date());
        collect2.setUpdateTime(new Date());
        int i = collectMapper.insertSelective(collect2);
        if (i == 0) {
            return null;
        }
        map.put("type", "add");
        return map;
    }

    @Override
    public WxCollectListVo list(Integer type, Integer page, Integer size) {
        CollectExample collectExample = new CollectExample();
        CollectExample.Criteria criteria = collectExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        criteria.andUserIdEqualTo(userInfoUtils.getUserId());
        criteria.andTypeEqualTo((byte)type.intValue());
        collectExample.setOrderByClause("update_time desc");
        PageHelper.startPage(page, size);
        List<Collect> collectList = collectMapper.selectByExample(collectExample);
        PageInfo<Collect> collectPageInfo = new PageInfo<>(collectList);
        long total = collectPageInfo.getTotal();

        WxCollectListVo wxCollectListVo = new WxCollectListVo();
        List<WxCollectListVo.CollectListBean> collectListBeans = new ArrayList<>();
        if (type == 0) {
            for (Collect collect : collectList) {
                WxCollectListVo.CollectListBean collectListBean = new WxCollectListVo.CollectListBean();

                Goods goods = goodsMapper.selectByPrimaryKey(collect.getValueId());
                collectListBean.setId(collect.getId());
                collectListBean.setType(collect.getType());
                collectListBean.setValueId(collect.getValueId());
                collectListBean.setName(goods.getName());
                collectListBean.setRetailPrice(goods.getRetailPrice());
                collectListBean.setPicUrl(goods.getPicUrl());
                collectListBean.setBrief(goods.getBrief());

                collectListBeans.add(collectListBean);
            }
            wxCollectListVo.setCollectList(collectListBeans);
            wxCollectListVo.setTotalPages((int)(Math.ceil(1.0 * total / size)));
            return wxCollectListVo;
        }

        // todo 专题收藏
        return null;
    }
}

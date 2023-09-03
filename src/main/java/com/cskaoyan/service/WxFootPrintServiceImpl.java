package com.cskaoyan.service;

import com.cskaoyan.mapper.FootPrintMapper;
import com.cskaoyan.mapper.GoodsMapper;
import com.cskaoyan.mapper.UserMapper;
import com.cskaoyan.model.bean.FootPrint;
import com.cskaoyan.model.bean.FootPrintExample;
import com.cskaoyan.model.vo.WxFootPrintListVo;
import com.cskaoyan.model.vo.WxFootPrintVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class WxFootPrintServiceImpl implements WxFootPrintService {

    @Autowired
    FootPrintMapper footPrintMapper;

    @Autowired
    GoodsMapper goodsMapper;

    @Autowired
    UserMapper userMapper;

    @Override
    public WxFootPrintVo list(Integer page, Integer size) {
        PageHelper.startPage(page,size);
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        Integer userId = userMapper.selectUserIdByUsername(username);
        if (userId == null){
            return null;
        }
        WxFootPrintVo wxFootPrintVo = new WxFootPrintVo();
        wxFootPrintVo.setFootprintList(footPrintMapper.selectGoodsIdAndAddTimeByUserId(userId));
        List<WxFootPrintListVo> footprintList = wxFootPrintVo.getFootprintList();
        for (int i = 0; i < footprintList.size(); i++) {
            WxFootPrintListVo wxFootPrintListVo = goodsMapper.selectGoodsInfoByGoodsId(footprintList.get(i).getGoodsId());
            footprintList.get(i).setBrief(wxFootPrintListVo.getBrief());
            footprintList.get(i).setPicUrl(wxFootPrintListVo.getPicUrl());
            footprintList.get(i).setName(wxFootPrintListVo.getName());
            footprintList.get(i).setRetailPrice(wxFootPrintListVo.getRetailPrice());
        }
        PageInfo<WxFootPrintListVo> wxFootPrintListVoPageInfo = new PageInfo<>(footprintList);
        long total = wxFootPrintListVoPageInfo.getTotal();
        wxFootPrintVo.setTotalPages(total);
        return wxFootPrintVo;
    }

    @Override
    public boolean delete(Integer id) {
        FootPrintExample footPrintExample = new FootPrintExample();
        FootPrintExample.Criteria criteria = footPrintExample.createCriteria();
        criteria.andIdEqualTo(id);
        criteria.andDeletedEqualTo(false);
        FootPrint footPrint = new FootPrint();
        footPrint.setDeleted(true);
        int result = footPrintMapper.updateByExampleSelective(footPrint, footPrintExample);
        return result == 1;
    }
}

package com.cskaoyan.service;

import com.cskaoyan.mapper.AdMapper;
import com.cskaoyan.model.bean.Ad;
import com.cskaoyan.model.bean.AdExample;
import com.cskaoyan.model.bo.BaseParamBo;
import com.cskaoyan.model.vo.BaseRespData;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Name : AdServiceImpl.java
 * @Time : 2021/8/11 21:54
 * @Author : Ashe
 * @Software : IntelliJ IDEA
 */
@Service
public class AdServiceImpl implements AdService {

    @Autowired
    AdMapper adMapper;

    @Override
    public BaseRespData query(String name, String content, BaseParamBo param) {

        PageHelper.startPage(param.getPage(), param.getLimit());

        AdExample example = new AdExample();

        example.setOrderByClause(param.getSort() + " " + param.getOrder());

        AdExample.Criteria criteria = example.createCriteria();

        criteria.andDeletedEqualTo(false);

        if (name != null && !"".equals(name)) {
            criteria.andNameLike("%" + name + "%");
        }

        if (content != null && !"".equals(content)) {
            criteria.andContentLike("%" + content + "%");
        }

        List<Ad> items = adMapper.selectByExample(example);

        PageInfo<Ad> pageInfo = new PageInfo<>(items);

        long total = pageInfo.getTotal();

        return BaseRespData.create(items, total);

    }

    @Override
    public Ad update(Ad ad) {

        adMapper.updateByPrimaryKeySelective(ad);

        Ad adVo = adMapper.selectByPrimaryKey(ad.getId());

        return adVo;

    }

    @Override
    public Ad create(Ad ad) {

        adMapper.insertSelective(ad);

        return ad;

    }


    @Override
    public void delete(Ad ad) {

        adMapper.updateByPrimaryKeySelective(ad);

    }


}

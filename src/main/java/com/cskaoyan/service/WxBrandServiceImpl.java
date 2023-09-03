package com.cskaoyan.service;

import com.cskaoyan.mapper.BrandMapper;
import com.cskaoyan.model.bean.Brand;
import com.cskaoyan.model.bean.BrandExample;
import com.cskaoyan.model.vo.BrandListVo;
import com.cskaoyan.model.vo.WxBrandListVo;
import com.cskaoyan.model.vo.WxBrandVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：lww
 * @description：
 * @date ：2021/8/14 17:16
 */
@Service
public class WxBrandServiceImpl implements WxBrandService {

    @Autowired
    BrandMapper brandMapper;

    /**
     * 获得品牌种类
     * @param page
     * @param size
     * @return
     */
    @Override
    public WxBrandListVo getBrandList(Integer page,Integer size) {
        PageHelper.startPage(page,size);
        List<WxBrandVo> wxBrandVos = brandMapper.selectBrandVo();
        PageInfo<WxBrandVo> pageInfo = new PageInfo<>(wxBrandVos);
        int totalPages = pageInfo.getPages();
        WxBrandListVo wxBrandListVo = new WxBrandListVo(wxBrandVos, totalPages);
        return wxBrandListVo;
    }

    /**
     * 获得品牌详情
     * @param id
     * @return
     */
    @Override
    public Brand getBrandDetail(Integer id) {
        BrandExample brandExample = new BrandExample();
        BrandExample.Criteria criteria = brandExample.createCriteria();
        criteria.andIdEqualTo(id);
        criteria.andDeletedEqualTo(false);
        Brand brand = brandMapper.selectByPrimaryKey(id);
        return brand;
    }
}

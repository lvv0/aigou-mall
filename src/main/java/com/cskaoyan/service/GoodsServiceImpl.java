package com.cskaoyan.service;

import com.aliyun.oss.model.PutObjectResult;
import com.cskaoyan.config.AliyunComponent;
import com.cskaoyan.converter.StringToDateConverter;
import com.cskaoyan.mapper.*;
import com.cskaoyan.model.bean.*;
import com.cskaoyan.model.bo.*;
import com.cskaoyan.model.vo.*;
import com.cskaoyan.typehandler.StringArrayTypeHandler;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.System;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    GoodsMapper goodsMapper;

    @Autowired
    StringToDateConverter stringToDateConverter;

    @Autowired
    GoodsSpecificationMapper goodsSpecificationMapper;

    @Autowired
    GoodsAttributeMapper goodsAttributeMapper;

    @Autowired
    GoodsProductMapper goodsProductMapper;

    @Autowired
    StorageMapper storageMapper;

    @Autowired
    AliyunComponent aliyunComponent;

    @Override
    public BaseRespData list(GoodsListBo goodsListBo) {
        PageHelper.startPage(goodsListBo.getPage(), goodsListBo.getLimit());
        GoodsExample goodsExample = new GoodsExample();
        goodsExample.setOrderByClause(goodsListBo.getSort() + " " + goodsListBo.getOrder());
        GoodsExample.Criteria criteria = goodsExample.createCriteria();
        if (goodsListBo.getName() != null && goodsListBo.getName().length() != 0) {
            criteria.andNameLike("%" + goodsListBo.getName() + "%");
        }
        if (goodsListBo.getGoodsSn() != null && goodsListBo.getGoodsSn().length() != 0) {
            criteria.andGoodsSnEqualTo(goodsListBo.getGoodsSn());
        }
        criteria.andDeletedEqualTo(false);
        List<Goods> goods = goodsMapper.selectByExampleWithBLOBs(goodsExample);
        PageInfo<Goods> goodsPageInfo = new PageInfo<>(goods);
        long total = goodsPageInfo.getTotal();
        return BaseRespData.create(goods, total);
    }

    @Override
    public GoodsCatAndBrandVo catAndBrand() {
        GoodsCatAndBrandVo goodsCatAndBrandVo = new GoodsCatAndBrandVo();
        goodsCatAndBrandVo.setCategoryList(goodsMapper.selectCateGoryIdAndName());
        List<CategoryListVo> categoryList = goodsCatAndBrandVo.getCategoryList();
        for (int i = 0; i < categoryList.size(); i++) {
            categoryList.get(i).setChildren(goodsMapper.selectCateGoryIdAndNameByPid(categoryList.get(i).getValue()));
        }
        goodsCatAndBrandVo.setBrandList(goodsMapper.selectBrandIdAndName());
        return goodsCatAndBrandVo;
    }

    @Override
    public GoodsStotagePicVo create(MultipartFile myfile, String netPath) {
        GoodsStotagePicVo goodsStotagePicVo = new GoodsStotagePicVo();
        String name = myfile.getOriginalFilename();
        name = name.substring(name.length() - 4);
        String uuid = UUID.randomUUID().toString();
        String key = uuid + name;
        System.out.println(key.length());
        String url = netPath + "/wx/storage/fetch/" + key;
        String type = myfile.getContentType();
        long size = myfile.getSize();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        String time = simpleDateFormat.format(new Date());
        goodsStotagePicVo.setKey(key);
        goodsStotagePicVo.setName(name);
        goodsStotagePicVo.setType(type);
        goodsStotagePicVo.setSize(size);
        goodsStotagePicVo.setUrl(url);
        goodsStotagePicVo.setAddTime(time);
        goodsStotagePicVo.setUpdateTime(time);
        int result = goodsMapper.insertStotage(goodsStotagePicVo);
        System.out.println(goodsStotagePicVo);
        if (result != 1) {
            return null;
        }
        InputStream inputStream = null;
        try {
            inputStream = myfile.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        aliyunComponent.fileUpload(key, inputStream);
        return goodsStotagePicVo;
    }

    @Override
    public boolean createGoods(GoodsCreateBo goodsCreateBo) {
        Goods goods = new Goods();
        goods.setGoodsSn(goodsCreateBo.getGoods().getGoodsSn());
        goods.setName(goodsCreateBo.getGoods().getName());
        goods.setCategoryId(goodsCreateBo.getGoods().getCategoryId());
        goods.setBrandId(goodsCreateBo.getGoods().getBrandId());
        goods.setGallery(goodsCreateBo.getGoods().getGallery());
        goods.setKeywords(goodsCreateBo.getGoods().getKeywords());
        goods.setBrief(goodsCreateBo.getGoods().getBrief());
        goods.setIsOnSale(goodsCreateBo.getGoods().isIsOnSale());
        goods.setSortOrder((short) 1);
        goods.setPicUrl(goodsCreateBo.getGoods().getPicUrl());
//        goods.setShareUrl();
        goods.setIsNew(goodsCreateBo.getGoods().isIsNew());
        goods.setIsHot(goodsCreateBo.getGoods().isIsHot());
        goods.setUnit(goodsCreateBo.getGoods().getUnit());
        goods.setCounterPrice(goodsCreateBo.getGoods().getCounterPrice());
        goods.setRetailPrice(goodsCreateBo.getGoods().getRetailPrice());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = simpleDateFormat.format(new Date());
        Date convert = stringToDateConverter.convert(time);
        goods.setAddTime(convert);
        goods.setUpdateTime(convert);
//        goods.setDeleted(false);
        goods.setDetail(goodsCreateBo.getGoods().getDetail());
        int result1 = goodsMapper.insertSelective(goods);
        if (result1 != 1) {
            return false;
        }
        List<SpecificationsBo> specifications = goodsCreateBo.getSpecifications();
        for (SpecificationsBo specification : specifications) {
            GoodsSpecification goodsSpecification = new GoodsSpecification();
            goodsSpecification.setSpecification(specification.getSpecification());
            goodsSpecification.setValue(specification.getValue());
            goodsSpecification.setPicUrl(specification.getPicUrl());
            goodsSpecification.setAddTime(convert);
            goodsSpecification.setUpdateTime(convert);
            goodsSpecification.setGoodsId(goods.getId());
            int result2 = goodsSpecificationMapper.insertSelective(goodsSpecification);
            if (result2 != 1) {
                return false;
            }
        }
        List<AttributesBo> attributes = goodsCreateBo.getAttributes();
        for (AttributesBo attribute : attributes) {
            GoodsAttribute goodsAttribute = new GoodsAttribute();
            goodsAttribute.setAttribute(attribute.getAttribute());
            goodsAttribute.setValue(attribute.getValue());
            goodsAttribute.setAddTime(convert);
            goodsAttribute.setUpdateTime(convert);
            goodsAttribute.setGoodsId(goods.getId());
            goodsAttribute.setDeleted(false);
            int result3 = goodsAttributeMapper.insertSelective(goodsAttribute);
            if (result3 != 1) {
                return false;
            }
        }
        List<ProductsBo> products = goodsCreateBo.getProducts();
        for (ProductsBo product : products) {
            GoodsProduct goodsProduct = new GoodsProduct();
            goodsProduct.setId(null);
            goodsProduct.setSpecifications(product.getSpecifications());
            goodsProduct.setPrice(product.getPrice());
            goodsProduct.setNumber(Integer.parseInt(product.getNumber()));
            goodsProduct.setUrl(product.getUrl());
            goodsProduct.setAddTime(convert);
            goodsProduct.setUpdateTime(convert);
            goodsProduct.setGoodsId(goods.getId());
            int result4 = goodsProductMapper.insertSelective(goodsProduct);
            if (result4 != 1) {
                return false;
            }
        }
        return true;
    }

    @Override
    public GoodsDetailVo detail(Integer id) {
        GoodsDetailVo goodsDetailVo = new GoodsDetailVo();
        goodsDetailVo.setGoods(goodsMapper.selectByPrimaryKey(id));
        GoodsAttributeExample goodsAttributeExample = new GoodsAttributeExample();
        GoodsAttributeExample.Criteria criteria = goodsAttributeExample.createCriteria();
        criteria.andGoodsIdEqualTo(id);
        criteria.andDeletedEqualTo(false);
        goodsDetailVo.setAttributes(goodsAttributeMapper.selectByExample(goodsAttributeExample));
        GoodsProductExample goodsProductExample = new GoodsProductExample();
        GoodsProductExample.Criteria criteria1 = goodsProductExample.createCriteria();
        criteria1.andGoodsIdEqualTo(id);
        criteria1.andDeletedEqualTo(false);
        goodsDetailVo.setProducts(goodsProductMapper.selectByExample(goodsProductExample));
        GoodsSpecificationExample goodsSpecificationExample = new GoodsSpecificationExample();
        GoodsSpecificationExample.Criteria criteria2 = goodsSpecificationExample.createCriteria();
        criteria2.andGoodsIdEqualTo(id);
        criteria2.andDeletedEqualTo(false);
        goodsDetailVo.setSpecifications(goodsSpecificationMapper.selectByExample(goodsSpecificationExample));
        goodsDetailVo.setCategoryIds(goodsMapper.selectCateGoryIdByGoodsId(goodsDetailVo.getGoods().getCategoryId()));
        if (goodsDetailVo != null) {
            return goodsDetailVo;
        } else return null;
    }

    @Override
    public boolean update(GoodsUpdateBo goodsUpdateBo) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = simpleDateFormat.format(new Date());
        Date convert = stringToDateConverter.convert(time);
        goodsUpdateBo.getGoods().setUpdateTime(convert);
        int result1 = goodsMapper.updateByPrimaryKeySelective(goodsUpdateBo.getGoods());
        if (result1 < 1) {
            return false;
        }
        GoodsSpecificationExample goodsSpecificationExample = new GoodsSpecificationExample();
        GoodsSpecificationExample.Criteria criteria1 = goodsSpecificationExample.createCriteria();
        criteria1.andGoodsIdEqualTo(goodsUpdateBo.getGoods().getId());
        criteria1.andDeletedEqualTo(false);
        List<GoodsSpecification> goodsSpecifications = goodsSpecificationMapper.selectByExample(goodsSpecificationExample);
        List<GoodsSpecification> specifications = goodsUpdateBo.getSpecifications();
        int result2;
        for (GoodsSpecification specification : specifications) {
            specification.setUpdateTime(convert);
            if (specification.getId() != null) {
                result2 = goodsSpecificationMapper.updateByPrimaryKeySelective(specification);
            } else {
                specification.setGoodsId(goodsUpdateBo.getGoods().getId());
                specification.setAddTime(convert);
                result2 = goodsSpecificationMapper.insertSelective(specification);
            }
            if (result2 < 1) {
                return false;
            }
        }
        Boolean flag1;
        for (GoodsSpecification goodsSpecification : goodsSpecifications) {
            flag1 = false;
            for (GoodsSpecification specification : specifications) {
                if (goodsSpecification.getId().equals(specification.getId())) {
                    flag1 = true;
                    break;
                }
            }
            if (!flag1) {
                Integer goodsId = goodsSpecification.getGoodsId();
                String value1 = goodsSpecification.getValue();
                List<GoodsProduct> goodsProducts = goodsProductMapper.selectGoodsProductByGoodsId(goodsId);
                String[] specifications2 = goodsProducts.get(0).getSpecifications();
                if (specifications2.length != 0 && specifications2 != null){
                    String[] strings = new String[specifications2.length - 1];
                    int k = 0;
                    for (String s : specifications2) {
                        if (!s.equals(value1)){
                            strings[k++] = s;
                        }
                    }
                    if (strings.length == 0){
                        int result5 =  goodsProductMapper.updateDeletedByGoodsId(goodsId);
                        if (result5 < 1){
                            return false;
                        }
                    }else {
                        GoodsProductExample goodsProductExample = new GoodsProductExample();
                        GoodsProductExample.Criteria criteria = goodsProductExample.createCriteria();
                        criteria.andGoodsIdEqualTo(goodsId);
                        criteria.andDeletedEqualTo(false);
                        GoodsProduct goodsProduct = new GoodsProduct();
                        goodsProduct.setSpecifications(strings);
                        int result6 = goodsProductMapper.updateByExampleSelective(goodsProduct,goodsProductExample);
                        if (result6 < 1){
                            return false;
                        }
                    }
                }else {
                    int result7 =  goodsProductMapper.updateDeletedByGoodsId(goodsId);
                    if (result7 < 1){
                        return false;
                    }
                }
                int i = goodsSpecificationMapper.updateDeleted(goodsSpecification.getId());
                if (i < 1){
                    return false;
                }
            }
        }
//        List<GoodsProduct> products = goodsUpdateBo.getProducts();
//        int result3;
//        for (GoodsProduct product : products) {
//            product.setUpdateTime(convert);
//            GoodsProductExample goodsProductExample = new GoodsProductExample();
//            GoodsProductExample.Criteria criteria = goodsProductExample.createCriteria();
//            criteria.andGoodsIdEqualTo(goodsUpdateBo.getGoods().getId());
//            product.setGoodsId(null);
//            product.setId(null);
//            product.setAddTime(null);
//            result3 = goodsProductMapper.updateByExampleSelective(product, goodsProductExample);
//            if (result3 < 1) {
//                return false;
//            }
//        }
        GoodsAttributeExample goodsAttributeExample = new GoodsAttributeExample();
        GoodsAttributeExample.Criteria criteria = goodsAttributeExample.createCriteria();
        criteria.andGoodsIdEqualTo(goodsUpdateBo.getGoods().getId());
        criteria.andDeletedEqualTo(false);
        List<GoodsAttribute> goodsAttributes = goodsAttributeMapper.selectByExample(goodsAttributeExample);
        List<GoodsAttribute> attributes = goodsUpdateBo.getAttributes();
        int result4;
        for (GoodsAttribute attribute : attributes) {
            attribute.setUpdateTime(convert);
            if (attribute.getId() != null) {
                result4 = goodsAttributeMapper.updateByPrimaryKeySelective(attribute);
            } else {
                attribute.setGoodsId(goodsUpdateBo.getGoods().getId());
                attribute.setAddTime(convert);
                result4 = goodsAttributeMapper.insertSelective(attribute);
            }
            if (result4 < 1) {
                return false;
            }
        }
        Boolean flag2;
        for (GoodsAttribute goodsAttribute : goodsAttributes) {
            flag2 = false;
            for (GoodsAttribute attribute : attributes) {
                if (goodsAttribute.getId().equals(attribute.getId())) {
                    flag2 = true;
                    break;
                }
            }
            if (!flag2) {
                int i = goodsAttributeMapper.updateDeleted(goodsAttribute.getId());
                if (i < 1) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean delete(Goods goods) {
        GoodsAttributeExample goodsAttributeExample = new GoodsAttributeExample();
        GoodsAttributeExample.Criteria criteria3 = goodsAttributeExample.createCriteria();
        criteria3.andGoodsIdEqualTo(goods.getId());
        criteria3.andDeletedEqualTo(false);
        List<GoodsAttribute> goodsAttributes = goodsAttributeMapper.selectByExample(goodsAttributeExample);
        if (goodsAttributes != null && goodsAttributes.size() != 0){
            int result1 = goodsAttributeMapper.updateDeletedByGoodsId(goods.getId());
            if (result1 < 1) {
                return false;
            }
        }
        GoodsProductExample goodsProductExample = new GoodsProductExample();
        GoodsProductExample.Criteria criteria = goodsProductExample.createCriteria();
        criteria.andGoodsIdEqualTo(goods.getId());
        criteria.andDeletedEqualTo(false);
        List<GoodsProduct> goodsProducts = goodsProductMapper.selectByExample(goodsProductExample);
        for (GoodsProduct goodsProduct : goodsProducts) {
            if (goodsProduct.getUrl() != null && goodsProduct.getUrl().length() != 0) {
                StorageExample storageExample = new StorageExample();
                StorageExample.Criteria criteria1 = storageExample.createCriteria();
                criteria1.andUrlEqualTo(goodsProduct.getUrl());
                Storage storage = new Storage();
                storage.setDeleted(true);
                int i = storageMapper.updateByExampleSelective(storage, storageExample);
                if (i < 1) {
                    return false;
                }
            }
            int j = goodsProductMapper.updateDeleted(goodsProduct.getId());
            if (j < 1) {
                return false;
            }
        }
        GoodsSpecificationExample goodsSpecificationExample = new GoodsSpecificationExample();
        GoodsSpecificationExample.Criteria criteria1 = goodsSpecificationExample.createCriteria();
        criteria1.andGoodsIdEqualTo(goods.getId());
        criteria1.andDeletedEqualTo(false);
        List<GoodsSpecification> goodsSpecifications = goodsSpecificationMapper.selectByExample(goodsSpecificationExample);
        if (goodsSpecifications != null && goodsSpecifications.size() != 0){
            for (GoodsSpecification goodsSpecification : goodsSpecifications) {
                if (goodsSpecification.getPicUrl() != null && goodsSpecification.getPicUrl().length() != 0) {
                    StorageExample storageExample = new StorageExample();
                    StorageExample.Criteria criteria2 = storageExample.createCriteria();
                    criteria2.andUrlEqualTo(goodsSpecification.getPicUrl());
                    Storage storage = new Storage();
                    storage.setDeleted(true);
                    int i = storageMapper.updateByExampleSelective(storage, storageExample);
                    if (i < 1) {
                        return false;
                    }
                }
                int j = goodsSpecificationMapper.updateDeleted(goodsSpecification.getId());
                if (j < 1) {
                    return false;
                }
            }
        }
        String[] gallery = goods.getGallery();
        if (gallery != null && gallery.length != 0) {
            for (int i = 0; i < gallery.length; i++) {
                StorageExample storageExample = new StorageExample();
                StorageExample.Criteria criteria2 = storageExample.createCriteria();
                criteria2.andUrlEqualTo(gallery[i]);
                Storage storage = new Storage();
                storage.setDeleted(true);
                int j = storageMapper.updateByExampleSelective(storage, storageExample);
                if (j < 1) {
                    return false;
                }
            }
        }
        int result2 = goodsMapper.updateDeleted(goods.getId());
        if (result2 < 1) {
            return false;
        }
        return true;
    }
}

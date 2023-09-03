package com.cskaoyan.service;

import com.cskaoyan.model.bean.Goods;
import com.cskaoyan.model.bo.GoodsCreateBo;
import com.cskaoyan.model.bo.GoodsListBo;
import com.cskaoyan.model.bo.GoodsUpdateBo;
import com.cskaoyan.model.vo.BaseRespData;
import com.cskaoyan.model.vo.GoodsCatAndBrandVo;
import com.cskaoyan.model.vo.GoodsDetailVo;
import com.cskaoyan.model.vo.GoodsStotagePicVo;
import org.springframework.web.multipart.MultipartFile;

public interface GoodsService {
    BaseRespData list(GoodsListBo goodsListBo);

    GoodsCatAndBrandVo catAndBrand();

    GoodsStotagePicVo create(MultipartFile myfile,String netPath);

    boolean createGoods(GoodsCreateBo goodsCreateBo);

    GoodsDetailVo detail(Integer id);

    boolean update(GoodsUpdateBo goodsUpdateBo);

    boolean delete(Goods goods);
}

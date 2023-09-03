package com.cskaoyan.service;

import com.cskaoyan.mapper.*;
import com.cskaoyan.model.bean.*;
import com.cskaoyan.model.bean.System;
import com.cskaoyan.model.bo.WxCartAddBo;
import com.cskaoyan.model.bo.WxCartCheckedBo;
import com.cskaoyan.model.bo.WxCartUpdateBo;
import com.cskaoyan.model.vo.WxCartCheckOutVo;
import com.cskaoyan.model.vo.WxCartIndexVo;
import com.cskaoyan.model.vo.WxCartTotalVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.dsig.keyinfo.KeyName;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class WxCartServiceImpl implements WxCartService {
    @Autowired
    CartMapper cartMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    GoodsProductMapper goodsProductMapper;

    @Autowired
    GoodsMapper goodsMapper;

    @Autowired
    CouponMapper couponMapper;

    @Autowired
    CouponUserMapper couponUserMapper;

    @Autowired
    AddressMapper addressMapper;

    @Autowired
    SystemMapper systemMapper;

    private Integer getUserId(String username) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<User> users = userMapper.selectByExample(userExample);
        return users.get(0).getId();
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public WxCartIndexVo index(String username, Integer userId) {
        Integer userId1;
        if (username != null) {
            userId1 = getUserId(username);
        } else {
            userId1 = userId;
        }

        CartExample cartExample = new CartExample();
        CartExample.Criteria criteria = cartExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        criteria.andUserIdEqualTo(userId1);
        cartExample.setOrderByClause("update_time desc");
        List<Cart> cartList = cartMapper.selectByExample(cartExample);
        WxCartIndexVo wxCartIndexVo = new WxCartIndexVo();
        wxCartIndexVo.setCartList(cartList);
        BigDecimal goodsAmount = BigDecimal.ZERO;
        Integer goodsCount = 0;
        for (Cart cart : cartList) {
            goodsAmount = goodsAmount.add(cart.getPrice().multiply(BigDecimal.valueOf(cart.getNumber())));
            goodsCount += cart.getNumber();
        }

        CartExample cartExample1 = new CartExample();
        CartExample.Criteria criteria1 = cartExample1.createCriteria();
        criteria1.andDeletedEqualTo(false);
        criteria1.andCheckedEqualTo(true);
        criteria1.andUserIdEqualTo(userId1);
        List<Cart> cartList1 = cartMapper.selectByExample(cartExample1);
        BigDecimal checkedGoodsAmount = BigDecimal.ZERO;
        Integer checkedGoodsCount = 0;
        for (Cart cart1 : cartList1) {
            checkedGoodsAmount = checkedGoodsAmount.add(cart1.getPrice().multiply(BigDecimal.valueOf(cart1.getNumber())));
            checkedGoodsCount += cart1.getNumber();
        }

        WxCartTotalVo wxCartTotalVo = new WxCartTotalVo();
        wxCartTotalVo.setCheckedGoodsAmount(checkedGoodsAmount);
        wxCartTotalVo.setCheckedGoodsCount(checkedGoodsCount);
        wxCartTotalVo.setGoodsAmount(goodsAmount);
        wxCartTotalVo.setGoodsCount(goodsCount);

        wxCartIndexVo.setCartTotal(wxCartTotalVo);

        return wxCartIndexVo;
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public WxCartIndexVo checked(WxCartCheckedBo wxCartCheckedBo, String username) {
        Integer userId = getUserId(username);

        if (wxCartCheckedBo.getIsChecked() == 1) {
            for (Integer productId : wxCartCheckedBo.getProductIds()) {
                CartExample cartExample = new CartExample();
                CartExample.Criteria criteria = cartExample.createCriteria();
                criteria.andDeletedEqualTo(false);
                criteria.andProductIdEqualTo(productId);
                criteria.andUserIdEqualTo(userId);
                Cart cart = new Cart();
                cart.setChecked(true);
                cartMapper.updateByExampleSelective(cart, cartExample);
            }
        } else if (wxCartCheckedBo.getIsChecked() == 0) {
            for (Integer productId : wxCartCheckedBo.getProductIds()) {
                CartExample cartExample = new CartExample();
                CartExample.Criteria criteria = cartExample.createCriteria();
                criteria.andDeletedEqualTo(false);
                criteria.andProductIdEqualTo(productId);
                criteria.andUserIdEqualTo(userId);
                Cart cart = new Cart();
                cart.setChecked(false);
                cartMapper.updateByExampleSelective(cart, cartExample);
            }
        }

        WxCartIndexVo cartIndexVo = this.index(null, userId);
        return cartIndexVo;
    }


    @Override
    public int update(WxCartUpdateBo wxCartUpdateBo) {
        if (wxCartUpdateBo.getNumber() < 1) {
            return 404;
        }
        Cart cart = new Cart();
        cart.setNumber(wxCartUpdateBo.getNumber());
        cart.setId(wxCartUpdateBo.getId());
        cart.setUpdateTime(new Date());
        int i = cartMapper.updateByPrimaryKeySelective(cart);
        if (i == 0) {
            return 404;
        }
        return 200;
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public WxCartIndexVo delete(Integer[] productIds, String username) {
        Integer userId = getUserId(username);

        //todo product_id 加索引
        for (Integer productId : productIds) {
            CartExample cartExample = new CartExample();
            CartExample.Criteria criteria = cartExample.createCriteria();
            criteria.andProductIdEqualTo(productId);
            criteria.andUserIdEqualTo(userId);
            Cart cart = new Cart();
            cart.setDeleted(true);
            cartMapper.updateByExampleSelective(cart, cartExample);
        }

        WxCartIndexVo cartIndexVo = this.index(null, userId);
        return cartIndexVo;
    }

    @Override
    public Integer add(WxCartAddBo wxCartAddBo, String username) {
        Integer userId = getUserId(username);
        GoodsProduct goodsProduct = goodsProductMapper.selectByPrimaryKey(wxCartAddBo.getProductId());
        if (wxCartAddBo.getNumber() > goodsProduct.getNumber()) {
            return null;
        }

        Integer goodsNum;

        CartExample cartExample = new CartExample();
        CartExample.Criteria criteria = cartExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        criteria.andUserIdEqualTo(userId);
        criteria.andProductIdEqualTo(wxCartAddBo.getProductId());
        long l = cartMapper.countByExample(cartExample);
        if (l == 1) {
            CartExample cartExample1 = new CartExample();
            CartExample.Criteria criteria1 = cartExample1.createCriteria();
            criteria1.andProductIdEqualTo(wxCartAddBo.getProductId());
            criteria1.andUserIdEqualTo(userId);
            criteria1.andDeletedEqualTo(false);
            List<Cart> cartList = cartMapper.selectByExample(cartExample1);
            Integer oldNum = cartList.get(0).getNumber();
            goodsNum = oldNum + wxCartAddBo.getNumber();

            Cart cart = new Cart();
            cart.setId(cartList.get(0).getId());
            cart.setNumber(goodsNum);
            cart.setUpdateTime(new Date());
            cartMapper.updateByPrimaryKeySelective(cart);

            return goodsCount(username);
        }

        Goods goods = goodsMapper.selectByPrimaryKey(wxCartAddBo.getGoodsId());
        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setGoodsId(wxCartAddBo.getGoodsId());
        cart.setGoodsSn(goods.getGoodsSn());
        cart.setGoodsName(goods.getName());
        cart.setProductId(wxCartAddBo.getProductId());
        cart.setPrice(goodsProduct.getPrice());
        cart.setNumber(wxCartAddBo.getNumber());
        cart.setSpecifications(goodsProduct.getSpecifications());
        cart.setPicUrl(goodsProduct.getUrl());
        cart.setAddTime(new Date());
        cart.setUpdateTime(new Date());
        cartMapper.insertSelective(cart);

        return goodsCount(username);
    }

    @Override
    public Integer fastAdd(WxCartAddBo wxCartAddBo, String username) {
        Integer userId = getUserId(username);
        GoodsProduct goodsProduct = goodsProductMapper.selectByPrimaryKey(wxCartAddBo.getProductId());
        if (wxCartAddBo.getNumber() > goodsProduct.getNumber()) {
            return null;
        }

        CartExample cartExample = new CartExample();
        CartExample.Criteria criteria = cartExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        criteria.andUserIdEqualTo(userId);
        criteria.andProductIdEqualTo(wxCartAddBo.getProductId());
        long l = cartMapper.countByExample(cartExample);
        if (l == 1) {
            CartExample cartExample1 = new CartExample();
            CartExample.Criteria criteria1 = cartExample1.createCriteria();
            criteria1.andDeletedEqualTo(false);
            criteria1.andProductIdEqualTo(wxCartAddBo.getProductId());
            criteria1.andUserIdEqualTo(userId);
            List<Cart> cartList = cartMapper.selectByExample(cartExample1);

            Cart cart = new Cart();
            cart.setId(cartList.get(0).getId());
            cart.setNumber(wxCartAddBo.getNumber());
            cart.setUpdateTime(new Date());
            cartMapper.updateByPrimaryKeySelective(cart);

            return cartList.get(0).getId();
        }

        Goods goods = goodsMapper.selectByPrimaryKey(wxCartAddBo.getGoodsId());
        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setGoodsId(wxCartAddBo.getGoodsId());
        cart.setGoodsSn(goods.getGoodsSn());
        cart.setGoodsName(goods.getName());
        cart.setProductId(wxCartAddBo.getProductId());
        cart.setPrice(goodsProduct.getPrice());
        cart.setNumber(wxCartAddBo.getNumber());
        cart.setSpecifications(goodsProduct.getSpecifications());
        cart.setPicUrl(goodsProduct.getUrl());
        cart.setAddTime(new Date());
        cart.setUpdateTime(new Date());
        cartMapper.insertSelective(cart);

        return cart.getId();
    }

    @Override
    public WxCartCheckOutVo checkOut(Integer cartId, Integer addressId, Integer couponId, Integer grouponRulesId, String username) {
        Integer userId = getUserId(username);
        List<Cart> cartList1 = new ArrayList<>();
        if (cartId != 0) {
            Cart cart = cartMapper.selectByPrimaryKey(cartId);
            cartList1.add(cart);
        } else {
            CartExample cartExample = new CartExample();
            CartExample.Criteria criteria = cartExample.createCriteria();
            criteria.andCheckedEqualTo(true);
            criteria.andDeletedEqualTo(false);
            criteria.andUserIdEqualTo(userId);
            List<Cart> cartList = cartMapper.selectByExample(cartExample);
            cartList1 = cartList;
        }

        WxCartCheckOutVo wxCartCheckOutVo = new WxCartCheckOutVo();
        BigDecimal goodsTotalPrice = BigDecimal.ZERO;

        for (Cart cart : cartList1) {
            goodsTotalPrice = goodsTotalPrice.add(cart.getPrice().multiply(BigDecimal.valueOf(cart.getNumber())));
        }

        CouponUserExample couponUserExample = new CouponUserExample();
        CouponUserExample.Criteria criteria1 = couponUserExample.createCriteria();
        criteria1.andDeletedEqualTo(false);
        criteria1.andUserIdEqualTo(userId);
        criteria1.andStatusEqualTo((short)0);
        criteria1.andStartTimeLessThan(new Date());
        criteria1.andEndTimeGreaterThan(new Date());
        List<CouponUser> couponUsers = couponUserMapper.selectByExample(couponUserExample);

        Coupon coupon = null;
        if (couponId > 0) {
            CouponExample couponExample = new CouponExample();
            CouponExample.Criteria criteria = couponExample.createCriteria();
            criteria.andDeletedEqualTo(false);
            criteria.andIdEqualTo(couponId);
            criteria.andMinLessThan(goodsTotalPrice);
            criteria.andStartTimeLessThan(new Date());
            criteria.andEndTimeGreaterThan(new Date());
            List<Coupon> coupons = couponMapper.selectByExample(couponExample);
            if (coupons.size() > 0) {
                coupon = coupons.get(0);
            }
        }
        List<Coupon> coupons1 = new ArrayList<>();
        for (CouponUser couponUser : couponUsers) {
            CouponExample couponExample = new CouponExample();
            CouponExample.Criteria criteria = couponExample.createCriteria();
            criteria.andDeletedEqualTo(false);
            criteria.andIdEqualTo(couponUser.getCouponId());
            criteria.andMinLessThan(goodsTotalPrice);
            criteria.andStatusEqualTo((short)0);
            //criteria.andStartTimeLessThan(new Date());
            //criteria.andEndTimeGreaterThan(new Date());
            List<Coupon> coupons = couponMapper.selectByExample(couponExample);
            if (coupons.size() > 0) {
                coupons1.add(coupons.get(0));
            }
        }
        if (coupon == null && coupons1.size() > 0) {
            BigDecimal maxCouponPrice = BigDecimal.ZERO;
            for (Coupon coupon1 : coupons1) {
                if (maxCouponPrice.compareTo(coupon1.getDiscount()) == -1) {
                    maxCouponPrice = coupon1.getDiscount();
                    coupon = coupon1;
                }
            }
        }
        if (couponId == -1) {
            wxCartCheckOutVo.setCouponId(-1);
            wxCartCheckOutVo.setCouponPrice(BigDecimal.ZERO);
        } else if (coupon == null) {
            wxCartCheckOutVo.setCouponId(0);
            wxCartCheckOutVo.setCouponPrice(BigDecimal.ZERO);
        } else {
            wxCartCheckOutVo.setCouponId(coupon.getId());
            wxCartCheckOutVo.setCouponPrice(coupon.getDiscount());
        }
        wxCartCheckOutVo.setAvailableCouponLength(coupons1.size());

        wxCartCheckOutVo.setFreightPrice(BigDecimal.ZERO);
        String keyName = systemMapper.getValueByName("cskaoyan_mall_express_freight_min");
        BigDecimal minPrice = new BigDecimal(keyName);
        if (goodsTotalPrice.compareTo(minPrice) == -1) {
            String keyValue = systemMapper.getValueByName("cskaoyan_mall_express_freight_value");
            wxCartCheckOutVo.setFreightPrice(new BigDecimal(keyValue));
        }

        wxCartCheckOutVo.setGoodsTotalPrice(goodsTotalPrice);
        wxCartCheckOutVo.setOrderTotalPrice((goodsTotalPrice.subtract(wxCartCheckOutVo.getCouponPrice())).add(wxCartCheckOutVo.getFreightPrice()));
        wxCartCheckOutVo.setActualPrice(wxCartCheckOutVo.getOrderTotalPrice());

        Address address = null;
        if (addressId != 0) {
            AddressExample addressExample = new AddressExample();
            AddressExample.Criteria criteria = addressExample.createCriteria();
            criteria.andDeletedEqualTo(false);
            criteria.andIdEqualTo(addressId);
            List<Address> addresses = addressMapper.selectByExample(addressExample);
            if (addresses.size() > 0) {
                address = addresses.get(0);
            }
        }
        if (address == null) {
            AddressExample addressExample = new AddressExample();
            AddressExample.Criteria criteria = addressExample.createCriteria();
            criteria.andDeletedEqualTo(false);
            criteria.andUserIdEqualTo(userId);
            criteria.andIsDefaultEqualTo(true);
            List<Address> addresses = addressMapper.selectByExample(addressExample);
            address = addresses.get(0);
        }
        wxCartCheckOutVo.setAddressId(address.getId());
        wxCartCheckOutVo.setCheckedAddress(address);

        wxCartCheckOutVo.setCheckedGoodsList(cartList1);

        return wxCartCheckOutVo;
    }

    @Override
    public Integer goodsCount(String username) {
        CartExample cartExample = new CartExample();
        CartExample.Criteria criteria = cartExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        criteria.andUserIdEqualTo(getUserId(username));
        List<Cart> cartList = cartMapper.selectByExample(cartExample);
        Integer goodsCount = 0;
        for (Cart cart : cartList) {
            goodsCount += cart.getNumber();
        }
        return goodsCount;
    }
}

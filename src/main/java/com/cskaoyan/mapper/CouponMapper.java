package com.cskaoyan.mapper;

import com.cskaoyan.model.bean.Coupon;
import com.cskaoyan.model.bean.CouponExample;
import com.cskaoyan.model.vo.WxCouponMyListDataVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CouponMapper {
    long countByExample(CouponExample example);

    int deleteByExample(CouponExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Coupon record);

    int insertSelective(Coupon record);

    List<Coupon> selectByExample(CouponExample example);

    Coupon selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Coupon record, @Param("example") CouponExample example);

    int updateByExample(@Param("record") Coupon record, @Param("example") CouponExample example);

    int updateByPrimaryKeySelective(Coupon record);

    int updateByPrimaryKey(Coupon record);

    Coupon selectCouponInfoByCouponId(Integer couponId);

    int selectCouponTotalByCouponId(Integer couponId);

    int updateCouponTotalByCouponId(Integer couponId);

    List<WxCouponMyListDataVo> selectCouponInfo2ByUserId(Integer userId);

    WxCouponMyListDataVo selectCouponInfo2ByCouponId(@Param("couponId") Integer couponId);
}
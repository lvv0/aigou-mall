package com.cskaoyan.mapper;

import com.cskaoyan.model.bean.CouponUser;
import com.cskaoyan.model.bean.CouponUserExample;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface CouponUserMapper {
    long countByExample(CouponUserExample example);

    int deleteByExample(CouponUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CouponUser record);

    int insertSelective(CouponUser record);

    List<CouponUser> selectByExample(CouponUserExample example);

    CouponUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CouponUser record, @Param("example") CouponUserExample example);

    int updateByExample(@Param("record") CouponUser record, @Param("example") CouponUserExample example);

    int updateByPrimaryKeySelective(CouponUser record);

    int updateByPrimaryKey(CouponUser record);

    List<CouponUser> selectConponByUserIdAndCouponId(@Param("userId") Integer userId, @Param("couponId") Integer couponId);

    List<Integer> selectConponIdByUserIdAndStatus(@Param("userId") Integer userId, @Param("status") Integer status);

    void updateByUserIdAndCouponId(@Param("userId") int userId, @Param("couponId") Integer couponId,
                                   @Param("update") Date update, @Param("delete") boolean delete,
                                   @Param("usedTime") Date usedTime, @Param("orderId") Integer orderId);

}
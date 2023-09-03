package com.cskaoyan.service;

import com.cskaoyan.converter.StringToDateConverter;
import com.cskaoyan.mapper.CouponMapper;
import com.cskaoyan.mapper.CouponUserMapper;
import com.cskaoyan.mapper.UserMapper;
import com.cskaoyan.model.bean.Coupon;
import com.cskaoyan.model.bean.CouponExample;
import com.cskaoyan.model.bean.CouponUser;
import com.cskaoyan.model.bean.CouponUserExample;
import com.cskaoyan.model.vo.WxCouponListDataVo;
import com.cskaoyan.model.vo.WxCouponListVo;
import com.cskaoyan.model.vo.WxCouponMyListDataVo;
import com.cskaoyan.model.vo.WxCouponMyListVo;
import com.cskaoyan.utils.UserInfoUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class WxCouponServiceImpl implements WxCouponService {

    @Autowired
    CouponMapper couponMapper;

    @Autowired
    CouponUserMapper couponUserMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    StringToDateConverter stringToDateConverter;

    @Autowired
    UserInfoUtils userInfoUtils;

    @Override
    public WxCouponListVo list(Integer page, Integer size) {
        PageHelper.startPage(page,size);
        CouponExample couponExample = new CouponExample();
        couponExample.setOrderByClause("discount" + " " + "asc");
        CouponExample.Criteria criteria = couponExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        List<Coupon> coupons = couponMapper.selectByExample(couponExample);
        if (coupons == null){
            return null;
        }
        ArrayList<WxCouponListDataVo> wxCouponListDataVos = new ArrayList<>();
        for (Coupon coupon : coupons) {
            WxCouponListDataVo wxCouponListDataVo = new WxCouponListDataVo();
            wxCouponListDataVo.setId(coupon.getId());
            wxCouponListDataVo.setName(coupon.getName());
            wxCouponListDataVo.setDesc(coupon.getDesc());
            wxCouponListDataVo.setTag(coupon.getTag());
            wxCouponListDataVo.setDiscount(coupon.getDiscount());
            wxCouponListDataVo.setMin(coupon.getMin());
            if (coupon.getDays() == 0){
                wxCouponListDataVo.setDays((short) 10);
            }else {
                wxCouponListDataVo.setDays(coupon.getDays());
            }
            wxCouponListDataVos.add(wxCouponListDataVo);
        }
        WxCouponListVo wxCouponListVo = new WxCouponListVo();
        wxCouponListVo.setData(wxCouponListDataVos);
        PageInfo<Coupon> couponPageInfo = new PageInfo<>(coupons);
        long total = couponPageInfo.getTotal();
        wxCouponListVo.setCount(total);
        return wxCouponListVo;
    }

    @Override
    public int receive(Integer couponId) {
        Coupon coupon = couponMapper.selectByPrimaryKey(couponId);
//        Coupon coupon = couponMapper.selectCouponInfoByCouponId(couponId);
        if (coupon == null){
            return 400;
        }
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        Integer userId = userMapper.selectUserIdByUsername(username);
        List<CouponUser> couponUser = couponUserMapper.selectConponByUserIdAndCouponId(userId,couponId);
        if (couponUser != null && couponUser.size() != 0 && coupon.getLimit() == 1){
            return 740;
        }
        if (couponUser.size() >= coupon.getLimit()){
            return 740;
        }
        if (coupon.getTotal() > 0){
            int result1 = couponMapper.updateCouponTotalByCouponId(couponId);
            if (result1 != 1){
                return 500;
            }
            int total = couponMapper.selectCouponTotalByCouponId(couponId);
            if (total <= 0){
                Coupon coupon1 = new Coupon();
                coupon1.setId(couponId);
                coupon1.setDeleted(true);
                int i = couponMapper.updateByPrimaryKeySelective(coupon1);
                if (i < 1){
                    return 500;
                }
            }
        }
        CouponUser couponUser1 = new CouponUser();
        couponUser1.setStartTime(coupon.getStartTime());
        couponUser1.setEndTime(coupon.getEndTime());
        couponUser1.setUserId(userId);
        couponUser1.setCouponId(couponId);
        couponUser1.setStatus((short) 0);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = simpleDateFormat.format(new Date());
        Date convert = stringToDateConverter.convert(time);
        couponUser1.setAddTime(convert);
        couponUser1.setUpdateTime(convert);
        int result2 = couponUserMapper.insertSelective(couponUser1);
        if (result2 != 1){
            return 500;
        }
        return 200;
    }

    @Override
    public WxCouponMyListVo mylist(Integer status, Integer page, Integer size) {
        PageHelper.startPage(page,size);
        Integer userId = userInfoUtils.getUserId();
        List<Integer> couponIds = couponUserMapper.selectConponIdByUserIdAndStatus(userId,status);
        ArrayList<WxCouponMyListDataVo> wxCouponMyListDataVos = new ArrayList<>();
        for (Integer couponId : couponIds) {
            WxCouponMyListDataVo wxCouponMyListDataVo = couponMapper.selectCouponInfo2ByCouponId(couponId);
            if (wxCouponMyListDataVo != null){
                wxCouponMyListDataVos.add(wxCouponMyListDataVo);
            }
        }
        if (wxCouponMyListDataVos == null || wxCouponMyListDataVos.size() == 0){
            return null;
        }
        int k;
        for (int i = 0; i < wxCouponMyListDataVos.size(); i++) {
            k = i;
            for (int j = i + 1; j < wxCouponMyListDataVos.size(); j++) {
                if (wxCouponMyListDataVos.get(j).getDiscount().compareTo(wxCouponMyListDataVos.get(k).getDiscount()) == 1){
                    k = j;
                }
            }
            WxCouponMyListDataVo wxCouponMyListDataVo = wxCouponMyListDataVos.get(k);
            wxCouponMyListDataVos.remove(k);
            wxCouponMyListDataVos.add(0,wxCouponMyListDataVo);
        }

        PageInfo<WxCouponMyListDataVo> wxCouponMyListDataVoPageInfo = new PageInfo<>(wxCouponMyListDataVos);
        long total = wxCouponMyListDataVoPageInfo.getTotal();
        WxCouponMyListVo wxCouponMyListVo = new WxCouponMyListVo();
        wxCouponMyListVo.setData(wxCouponMyListDataVos);
        wxCouponMyListVo.setCount(total);
        return wxCouponMyListVo;
    }

    @Override
    public int exchange(String code) {
        Integer userId = userInfoUtils.getUserId();
        CouponExample couponExample = new CouponExample();
        CouponExample.Criteria criteria1 = couponExample.createCriteria();
        criteria1.andCodeEqualTo(code);
        criteria1.andDeletedEqualTo(false);
        List<Coupon> coupons = couponMapper.selectByExample(couponExample);
        if (coupons == null || coupons.size() == 0){
            return 742;
        }
        Integer couponId = coupons.get(0).getId();
        CouponUserExample couponUserExample = new CouponUserExample();
        CouponUserExample.Criteria criteria = couponUserExample.createCriteria();
        criteria.andUserIdEqualTo(userId);
        criteria.andDeletedEqualTo(false);
        List<CouponUser> couponUsers = couponUserMapper.selectByExample(couponUserExample);
        for (CouponUser couponUser : couponUsers) {
            if (couponUser.getCouponId() == couponId){
                if (coupons.get(0).getLimit() == 1){
                    return 740;
                }else {
                    Integer total = coupons.get(0).getTotal();
                    if (total > 0){
                        int i = couponMapper.updateCouponTotalByCouponId(couponId);
                        int updateTotal = couponMapper.selectCouponTotalByCouponId(couponId);
                        if (updateTotal <= 0){
                            Coupon coupon = new Coupon();
                            coupon.setId(couponId);
                            coupon.setDeleted(true);
                            int i1 = couponMapper.updateByPrimaryKeySelective(coupon);
                            if (i1 < 1){
                                return 500;
                            }
                        }
                        if (i != 1){
                            return 500;
                        }
                    }
                    //

                }
            }
            Coupon coupon = couponMapper.selectByPrimaryKey(couponId);
            CouponUser couponUser1 = new CouponUser();
            couponUser1.setStartTime(coupon.getStartTime());
            couponUser1.setEndTime(coupon.getEndTime());
            couponUser1.setUserId(userId);
            couponUser1.setCouponId(couponId);
            couponUser1.setStatus((short) 0);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = simpleDateFormat.format(new Date());
            Date convert = stringToDateConverter.convert(time);
            couponUser1.setAddTime(convert);
            couponUser1.setUpdateTime(convert);
            int result2 = couponUserMapper.insertSelective(couponUser1);
            if (result2 != 1){
                return 500;
            }
            return 200;
        }
        return 500;
    }

    @Override
    public List<WxCouponMyListDataVo> selectlist(Integer cartId, Integer grouponRulesId) {
        Integer userId = userInfoUtils.getUserId();
        CouponUserExample couponUserExample = new CouponUserExample();
        CouponUserExample.Criteria criteria = couponUserExample.createCriteria();
        criteria.andUserIdEqualTo(userId);
        criteria.andStatusEqualTo((short) 0);
        criteria.andDeletedEqualTo(false);
        List<CouponUser> couponUsers = couponUserMapper.selectByExample(couponUserExample);
        if (couponUsers == null || couponUsers.size() == 0){
            return null;
        }
        ArrayList<WxCouponMyListDataVo> wxCouponMyListDataVos = new ArrayList<>();
        for (CouponUser couponUser : couponUsers) {
            WxCouponMyListDataVo wxCouponMyListDataVo = couponMapper.selectCouponInfo2ByCouponId(couponUser.getCouponId());
            if (wxCouponMyListDataVo != null){
                wxCouponMyListDataVos.add(wxCouponMyListDataVo);
            }
        }
        if (wxCouponMyListDataVos.size() == 0){
            return null;
        }
        int k;
        for (int i = 0; i < wxCouponMyListDataVos.size(); i++) {
            k = i;
            for (int j = i + 1; j < wxCouponMyListDataVos.size(); j++) {
                if (wxCouponMyListDataVos.get(j).getDiscount().compareTo(wxCouponMyListDataVos.get(k).getDiscount()) == 1){
                    k = j;
                }
            }
            WxCouponMyListDataVo wxCouponMyListDataVo = wxCouponMyListDataVos.get(k);
            wxCouponMyListDataVos.remove(k);
            wxCouponMyListDataVos.add(0,wxCouponMyListDataVo);
        }
        return wxCouponMyListDataVos;
    }
}

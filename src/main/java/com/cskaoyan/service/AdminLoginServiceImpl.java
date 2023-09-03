package com.cskaoyan.service;

import com.cskaoyan.mapper.*;
import com.cskaoyan.model.bean.*;
import com.cskaoyan.model.vo.AdminLoginInfoVo;
import com.cskaoyan.model.vo.DashBoardVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ：lww
 * @description：
 * @date ：2021/8/11 19:34
 */
@Service
public class AdminLoginServiceImpl implements AdminLoginService {
    @Autowired
    AdminMapper adminMapper;
    @Autowired
    RoleMapper roleMapper;
    @Autowired
    PermissionMapper permissionMapper;
    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    GoodsProductMapper goodsProductMapper;
    @Autowired
    OrderMapper orderMapper;

    @Autowired
    PermissionTMapper permissionTMapper;

    /**
     * 获取首页信息
     * @return
     */
    @Override
    public DashBoardVo getAllInfo() {
        GoodsExample goodsExample = new GoodsExample();
        GoodsExample.Criteria goodsCriteria = goodsExample.createCriteria().andDeletedEqualTo(false);

        UserExample userExample = new UserExample();
        UserExample.Criteria userCriteria = userExample.createCriteria().andDeletedEqualTo(false);

        GoodsProductExample goodsProductExample = new GoodsProductExample();
        goodsProductExample.createCriteria().andDeletedEqualTo(false);

        OrderExample orderExample = new OrderExample();
        OrderExample.Criteria orderCriteria = orderExample.createCriteria().andDeletedEqualTo(false);

        Integer goodsTotal = Math.toIntExact(goodsMapper.countByExample(goodsExample));
        Integer userTotal = Math.toIntExact(userMapper.countByExample(userExample));
        Integer productTotal = Math.toIntExact(goodsProductMapper.countByExample(goodsProductExample));
        Integer orderTotal = Math.toIntExact(orderMapper.countByExample(orderExample));
        DashBoardVo dashBoardVo = new DashBoardVo(goodsTotal, userTotal, productTotal, orderTotal);
        return dashBoardVo;
    }

    /**
     * 管理员登录信息
     * @param username
     * @return
     */
    @Override
    public AdminLoginInfoVo getAdminInfo(String username,String ip) {
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andUsernameEqualTo(username);
        Admin admin = adminMapper.selectByExample(adminExample).get(0);
        Date date = new Date();
        admin.setLastLoginTime(date);
        admin.setLastLoginIp(ip);
        adminMapper.updateByExampleSelective(admin,adminExample);
        Integer[] roleIds = admin.getRoleIds();
        List<String> roles=new ArrayList<>();
        List<String> perms=new ArrayList<>();
        for (Integer roleId : roleIds) {
            String name = roleMapper.selectNameById(roleId);
            List<String> list = permissionMapper.selectPermsByRoleId(roleId);
            roles.add(name);
            perms.addAll(list);
        }

        List<String> apiList = new ArrayList<>();
        for (String perm : perms) {
            if ("*".equals(perm)) {
                apiList = new ArrayList<>();
                apiList.add("*");
                break;
            }
            String api = permissionTMapper.selectApiByPerm(perm);
            apiList.add(api);
        }

        AdminLoginInfoVo adminLoginInfoVo = new AdminLoginInfoVo(admin.getUsername(), admin.getAvatar(), roles, apiList);
        return adminLoginInfoVo;
    }
}

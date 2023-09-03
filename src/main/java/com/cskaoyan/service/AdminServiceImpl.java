package com.cskaoyan.service;

import com.cskaoyan.mapper.AdminMapper;
import com.cskaoyan.model.bean.Admin;
import com.cskaoyan.model.bean.AdminExample;
import com.cskaoyan.model.bo.BaseParamBo;
import com.cskaoyan.model.vo.AdminVo;
import com.cskaoyan.model.vo.BaseRespData;
import com.cskaoyan.model.vo.ListAdminVo;
import com.cskaoyan.utils.PasswordProcess;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminMapper adminMapper;

    @Override
    public BaseRespData listAdmin(BaseParamBo baseParamBo, String username) {
        PageHelper.startPage(baseParamBo.getPage(), baseParamBo.getLimit());
        List<Admin> items = adminMapper.selectAdmin(baseParamBo, username);
        PageInfo<Admin> pageInfo = new PageInfo<>(items);
        long total = pageInfo.getTotal();
        List<ListAdminVo> listAdminVoList = new ArrayList<>();
        for (Admin item : items) {
            ListAdminVo listAdminVo = new ListAdminVo();
            listAdminVo.setId(item.getId());
            listAdminVo.setUsername(item.getUsername());
            listAdminVo.setAvatar(item.getAvatar());
            listAdminVo.setRoleIds(item.getRoleIds());
            listAdminVoList.add(listAdminVo);
        }
        return BaseRespData.create(listAdminVoList, total);
    }

    @Override
    public Integer delete(Admin admin) {
        Admin admin1 = new Admin();
        admin1.setId(admin.getId());
        admin1.setDeleted(true);
        Integer affectRows = adminMapper.updateByPrimaryKeySelective(admin1);
        if (affectRows == 1) {
            return 200;
        }
        return 500;
    }

    @Override
    public AdminVo create(Admin admin) {
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andUsernameEqualTo(admin.getUsername());
        // 未指定账户是否删除，即已删除的账户名不允许再注册
        long count = adminMapper.countByExample(adminExample);
        if (count > 0) {
            return null;
        }
        if (admin.getAvatar() == null) {
            admin.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        }
        admin.setPassword(PasswordProcess.passwordProcess(admin.getPassword()));
        admin.setAddTime(new Date());
        admin.setUpdateTime(new Date());
        int insert = adminMapper.insertSelective(admin);
        if (insert == 0) {
            return null;
        }
        Admin admin1 = adminMapper.selectByPrimaryKey(admin.getId());

        return new AdminVo(admin1);
    }

    @Override
    public AdminVo update(Admin admin) {
        Admin admin2 = new Admin();
        admin.setPassword(PasswordProcess.passwordProcess(admin.getPassword()));
        Admin admin1 = adminMapper.selectByPrimaryKey(admin.getId());
        if (!admin1.getUsername().equals(admin.getUsername())) {
            AdminExample adminExample = new AdminExample();
            AdminExample.Criteria criteria = adminExample.createCriteria();
            criteria.andUsernameEqualTo(admin.getUsername());
            criteria.andIdNotIn(Arrays.asList(admin.getId()));
            long count = adminMapper.countByExample(adminExample);
            if (count > 0) {
                return null;
            }
            admin2.setUsername(admin.getUsername());
        }
        if (!admin1.getPassword().equals(admin.getPassword())) {
            admin2.setPassword(admin.getPassword());
        }
        if (!admin1.getAvatar().equals(admin.getAvatar())) {
            admin2.setAvatar(admin.getAvatar());
        }
        if (!admin1.getRoleIds().equals(admin.getRoleIds())) {
            admin2.setRoleIds(admin.getRoleIds());
        }
        admin2.setUpdateTime(new Date());
        admin2.setId(admin.getId());
        int update = adminMapper.updateByPrimaryKeySelective(admin2);
        if (update != 1) {
            return null;
        }
        Admin admin3 = adminMapper.selectByPrimaryKey(admin.getId());
        return new AdminVo(admin3);
    }
}

package com.cskaoyan.service;

import com.cskaoyan.mapper.LogMapper;
import com.cskaoyan.model.bean.Log;
import com.cskaoyan.model.bean.LogExample;
import com.cskaoyan.model.bo.BaseParamBo;
import com.cskaoyan.model.vo.BaseRespData;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LogServiceImpl implements LogService {




    @Autowired
    LogMapper logMapper;

    @Override
    public void log(Log log) {
        log.setAddTime(new Date());
        log.setUpdateTime(new Date());
        logMapper.insertSelective(log);
    }

    @Override
    public BaseRespData list(BaseParamBo baseParamBo, String name) {
        PageHelper.startPage(baseParamBo.getPage(), baseParamBo.getLimit());
        LogExample logExample = new LogExample();
        LogExample.Criteria criteria = logExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        if (name != null && !"".equals(name)) {
            criteria.andAdminLike("%" + name + "%");
        }
        logExample.setOrderByClause(baseParamBo.getSort()+ " " +baseParamBo.getOrder());
        List<Log> logList = logMapper.selectByExample(logExample);
        PageInfo<Log> logPageInfo = new PageInfo<>(logList);
        long total = logPageInfo.getTotal();
        return new BaseRespData<Log>(logList, total);
    }
}

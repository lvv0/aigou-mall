package com.cskaoyan.service;

import com.cskaoyan.mapper.StorageMapper;
import com.cskaoyan.model.bean.Storage;
import com.cskaoyan.model.bean.StorageExample;
import com.cskaoyan.model.bo.BaseParamBo;
import com.cskaoyan.model.vo.BaseRespData;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class StorageServiceImpl implements StorageService {

    @Autowired
    StorageMapper storageMapper;

    @Override
    public int delete(Storage storage) {
        Storage storage1 = new Storage();
        storage1.setId(storage.getId());
        storage1.setDeleted(true);
        int i = storageMapper.updateByPrimaryKeySelective(storage1);
        if (i != 1) {
            return 500;
        }
        return 200;
    }

    @Override
    public BaseRespData list(BaseParamBo baseParamBo, String name, String key) {
        PageHelper.startPage(baseParamBo.getPage(), baseParamBo.getLimit());
        StorageExample storageExample = new StorageExample();
        StorageExample.Criteria criteria = storageExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        if (name != null && !"".equals(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        if (key != null && !"".equals(key)) {
            criteria.andKeyEqualTo(key);
        }
        storageExample.setOrderByClause(baseParamBo.getSort()+ " " +baseParamBo.getOrder());
        List<Storage> storageList = storageMapper.selectByExample(storageExample);
        PageInfo<Storage> storagePageInfo = new PageInfo<>(storageList);
        long total = storagePageInfo.getTotal();
        return new BaseRespData<Storage>(storageList, total);
    }

    @Override
    public Storage update(Storage storage) {
        Storage storage1 = new Storage();
        storage1.setId(storage.getId());
        storage1.setName(storage.getName());
        storage1.setUpdateTime(new Date());
        int i = storageMapper.updateByPrimaryKeySelective(storage1);
        if (i != 1) {
            return null;
        }
        Storage storage2 = storageMapper.selectByPrimaryKey(storage.getId());
        return storage2;
    }
}

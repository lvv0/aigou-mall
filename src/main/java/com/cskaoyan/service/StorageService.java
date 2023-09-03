package com.cskaoyan.service;

import com.cskaoyan.model.bean.Storage;
import com.cskaoyan.model.bo.BaseParamBo;
import com.cskaoyan.model.vo.BaseRespData;

public interface StorageService {
    int delete(Storage storage);

    BaseRespData list(BaseParamBo baseParamBo, String name, String key);

    Storage update(Storage storage);
}

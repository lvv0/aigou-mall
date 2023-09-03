package com.cskaoyan.service;

import com.cskaoyan.model.bean.Log;
import com.cskaoyan.model.bo.BaseParamBo;
import com.cskaoyan.model.vo.BaseRespData;

public interface LogService {
    void log(Log log);

    BaseRespData list(BaseParamBo baseParamBo, String name);
}

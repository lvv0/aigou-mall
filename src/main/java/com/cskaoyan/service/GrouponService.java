package com.cskaoyan.service;

import com.cskaoyan.model.bean.GrouponRules;
import com.cskaoyan.model.bo.BaseParamBo;
import com.cskaoyan.model.bo.GrouponRulesBo;
import com.cskaoyan.model.vo.BaseRespData;

/**
 * @Name : GrouponService.java
 * @Time : 2021/8/13 11:53
 * @Author : Ashe
 * @Software : IntelliJ IDEA
 */
public interface GrouponService {

    BaseRespData query(Integer goodsId, BaseParamBo param);

    Integer update(GrouponRulesBo grouponRulesBo);

    void delete(GrouponRules grouponRules);

    GrouponRules create(GrouponRulesBo grouponRulesBo);

    BaseRespData listRecord(Integer goodsId, BaseParamBo param);

}

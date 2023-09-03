package com.cskaoyan.service;

import com.cskaoyan.model.bo.BaseParamBo;
import com.cskaoyan.model.vo.BaseRespVo;
import com.cskaoyan.model.vo.UserManageListVo;

/**
 * @author ：lww
 * @description：
 * @date ：2021/8/11 20:21
 */
public interface UserManageService {
    UserManageListVo getUserList(String username, String mobile, BaseParamBo baseParamBo);
    UserManageListVo getAddressList(String userId,String username,BaseParamBo baseParamBo);
    UserManageListVo getCollectList(String userId,String valueId,BaseParamBo baseParamBo);
    UserManageListVo getFootprintList(String userId, String goodsId, BaseParamBo baseParamBo);
    UserManageListVo getSearchHistoryList(String userId,String keyword,BaseParamBo baseParamBo);
    UserManageListVo getFeedBackList(String username,String feedBackId,BaseParamBo baseParamBo);
}

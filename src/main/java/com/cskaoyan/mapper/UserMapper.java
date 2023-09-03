package com.cskaoyan.mapper;

import com.cskaoyan.model.bean.User;
import com.cskaoyan.model.bean.UserExample;
import com.cskaoyan.model.vo.UserAddressVo;
import com.cskaoyan.model.vo.UserRowsVo;
import com.cskaoyan.model.vo.WxTopicCommentUserInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    long countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int getAddUserCount();

    int getIdByName(String username);

    List<UserRowsVo> getUserStat();

    int queryUserIdByUsername(String username);

    String getPassWordByUserName(String username);

    WxTopicCommentUserInfoVo selectNickNameAndAvatarByUserName(String username);

    Integer selectUserIdByUsername(String username);

    int judgeUserNameIsExist(String username);

    int judgeMobilePhoneIsExist(String mobile);
}
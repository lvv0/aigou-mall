package com.cskaoyan.mapper;

import com.cskaoyan.model.vo.PermissionT;

import java.util.List;

public interface PermissionTMapper {
    List<PermissionT> selectTwoLevel(Integer id);

    List<PermissionT> selectThreeLevel(Integer id);

    List<PermissionT> selectOneLevel();

    List<String> selectAllThird();

    String selectApiByPerm(String perm);

    List<PermissionT> selectSecLevel();

    List<PermissionT> selectThrLevel();
}

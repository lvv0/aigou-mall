package com.cskaoyan.model.vo;

import com.cskaoyan.model.bean.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author ：lww
 * @description：
 * @date ：2021/8/11 20:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserManageListVo<T> {
    List<T> items;
    Long total;
}

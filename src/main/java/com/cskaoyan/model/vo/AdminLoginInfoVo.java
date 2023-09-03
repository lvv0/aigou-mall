package com.cskaoyan.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author ：lww
 * @description：
 * @date ：2021/8/11 19:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminLoginInfoVo {

    private String name;
    private String avatar;
    private List<String> roles;
    private List<String> perms;

}

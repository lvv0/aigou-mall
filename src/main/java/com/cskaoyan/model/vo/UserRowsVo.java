package com.cskaoyan.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author ：lww
 * @description：
 * @date ：2021/8/12 19:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRowsVo {
    private Integer users;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date day;
}

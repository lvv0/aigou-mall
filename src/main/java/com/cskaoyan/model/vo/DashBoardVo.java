package com.cskaoyan.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ：lww
 * @description：TODO
 * @date ：2021/8/11 19:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashBoardVo {

    private Integer goodsTotal;
    private Integer userTotal;
    private Integer productTotal;
    private Integer orderTotal;


}

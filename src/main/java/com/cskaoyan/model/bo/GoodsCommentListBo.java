package com.cskaoyan.model.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsCommentListBo {
    Integer page;
    Integer limit;
    @Min(value = 0,message = "参数必须是数字")
    Integer userId;
    @Min(value = 0,message = "参数必须是数字")
    Integer valueId;
    String sort;
    String order;
}

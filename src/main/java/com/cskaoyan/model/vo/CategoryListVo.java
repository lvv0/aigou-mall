package com.cskaoyan.model.vo;

import java.util.List;

public class CategoryListVo {
    /**
     * children : [{"label":"布艺软装","value":1008002},{"label":"被枕","value":1008008},{"label":"床品件套","value":1008009},{"label":"灯具","value":1008016},{"label":"地垫","value":1010003},{"label":"床垫","value":1011003},{"label":"家饰","value":1011004},{"label":"家具","value":1015000},{"label":"宠物","value":1017000},{"label":"夏凉","value":1036000},{"label":"风景","value":1036015},{"label":"123","value":1036019}]
     * label : 居家
     * value : 1005000
     */
    private List<ChildrenVo> children;
    private String label;
    private Integer value;

    public void setChildren(List<ChildrenVo> children) {
        this.children = children;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public List<ChildrenVo> getChildren() {
        return children;
    }

    public String getLabel() {
        return label;
    }

    public Integer getValue() {
        return value;
    }
}


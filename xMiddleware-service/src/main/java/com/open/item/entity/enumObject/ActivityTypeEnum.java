package com.open.item.entity.enumObject;

public enum ActivityTypeEnum {

    ARTICLE("文章"),

    IMG("图片"),

    VOTE("投票");

    private String label;

    private ActivityTypeEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}

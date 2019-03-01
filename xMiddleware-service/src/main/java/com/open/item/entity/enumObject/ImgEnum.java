package com.open.item.entity.enumObject;

public enum ImgEnum {

    ART_IMG("内容图片"),

    IMG_IMG("资源图片");

    private String label;

    private ImgEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}

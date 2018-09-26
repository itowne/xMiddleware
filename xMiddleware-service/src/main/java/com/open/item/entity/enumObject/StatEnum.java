package com.open.item.entity.enumObject;

public enum StatEnum {

    VALID("有效"),

    INVALID("无效");

    private String label;

    private StatEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}

package com.open.item.entity.enumObject;

public enum BooleanEnum {

    NO("否"),

    YES("是");

    private String label;

    private BooleanEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}

package com.open.item.entity.enumObject;

public enum UserRoleEnum {

    ADMIN("管理员"),

    NORMAL("用户"),

    SUPER("超级管理员");

    private String label;

    private UserRoleEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}

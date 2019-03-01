package com.open.item.entity.enumObject;

import org.apache.commons.lang3.StringUtils;

public enum ViewTypeEnum {

    NOTICE("notice", "活动预告"),

    CURRENT("current", "热门活动"),

    HISTORY("history", "往期回顾");

    private String code;
    private String label;

    private ViewTypeEnum(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public static ViewTypeEnum getByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        for (ViewTypeEnum vte : ViewTypeEnum.values()) {
            if (vte.getCode().equals(code)) {
                return vte;
            }
        }
        return null;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

package com.caifu.enums;

/**
 * ClassName :SexTypeEnum
 *
 * @author :  yb
 * @description ：
 * @date : 2020-09-08 09:31
 */
public enum SexTypeEnum {

    /**
     * 性别
     */
    male("1", "男")
    ,female("2", "女");

    SexTypeEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }


    public static String getName(String key) {
        for (SexTypeEnum c : SexTypeEnum.values()) {
            if (c.getKey().equals(key)) {
                return c.value;
            }
        }
        return "";
    }

    public static String getEnumKey(String name) {
        for (SexTypeEnum c : SexTypeEnum.values()) {
            if (c.getValue().equals(name)) {
                return c.key;
            }
        }
        return "";
    }

    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


}

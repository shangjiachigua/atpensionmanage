package com.caifu.enums;

/**
 * ClassName :UserInfoStatusEnum
 *
 * @author :  yb
 * @description ：
 * @date : 2020-09-08 09:31
 */
public enum UserInfoStatusEnum {

    /**
     * userInfo status
     */
    normal("1", "健在")
    ,female("8", "离世");

    UserInfoStatusEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }


    public static String getName(String key) {
        for (UserInfoStatusEnum c : UserInfoStatusEnum.values()) {
            if (c.getKey().equals(key)) {
                return c.value;
            }
        }
        return "";
    }

    public static String getEnumKey(String name) {
        for (UserInfoStatusEnum c : UserInfoStatusEnum.values()) {
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

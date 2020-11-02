package com.caifu.enums;

/**
 * ClassName :UserInfoLiveConditionEnum
 *
 * @author :  yb
 * @description ：
 * @date : 2020-10-28 15:31
 */
public enum UserInfoLiveConditionEnum {

    /**
     * userInfo 居住状况  1-人在户在 2-人在户不在 3-户在人不在
     */
    RZHZ("1", "人在户在")
    ,RZHBZ("2", "人在户不在")
    ,HZRBZ("3", "户在人不在");

    UserInfoLiveConditionEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }


    public static String getName(String key) {
        for (UserInfoLiveConditionEnum c : UserInfoLiveConditionEnum.values()) {
            if (c.getKey().equals(key)) {
                return c.value;
            }
        }
        return "";
    }

    public static String getEnumKey(String name) {
        for (UserInfoLiveConditionEnum c : UserInfoLiveConditionEnum.values()) {
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

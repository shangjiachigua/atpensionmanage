package com.caifu.enums;

/**
 * ClassName :LabelTypeEnum
 *
 * @author :  yb
 * @description ：
 * @date : 2020-08-27 15:24
 */
public enum LabelTypeEnum {

    /**
     *
     */
    ZJLX("13", "证件类型")
    ,ZZMM("17", "政治面貌")
    ,HJ("18", "户籍")
    ,SZDQ("19", "所在地区")
    ,SBLX("20", "社保类型")
    ,WHCD("21", "文化程度")
    ,YLZK("22", "养老状况")
    ,ZGRQ("24", "照顾人群")
    ,HYQK("41", "婚姻情况")
    ,YLRGX("42", "与老人关系")
    ,MZ("51", "民族")
    ,CZQK("81", "持证情况")
    ,JJQK("82", "居家情况")
    ,RZQK("83", "认知情况")
    ,SWQK("84", "涉外情况")
    ,JKQK("85", "健康情况")
    ,AQQK("86", "安全情况")
    ,QTQK("87", "其他情况")
    ;

    private String key;
    private String value;
    LabelTypeEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static String getName(String key) {
        for (LabelTypeEnum c : LabelTypeEnum.values()) {
            if (c.getKey().equals(key)) {
                return c.value;
            }
        }
        return "";
    }

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

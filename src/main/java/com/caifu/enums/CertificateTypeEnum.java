package com.caifu.enums;

/**
 * ClassName :CertificateTypeEnum
 *
 * @author :  yb
 * @description ：
 * @date : 2020-09-07 11:22
 */
public enum CertificateTypeEnum {
    /**
     * 证件类型
     */
    SFZ("1301", "大陆身份证")
    ,HZ("1302", "护照")
    ,GATSFZ("1303", "港澳台居民身份证");

    private String key;
    private String value;

    CertificateTypeEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static String getName(String key) {
        for (CertificateTypeEnum c : CertificateTypeEnum.values()) {
            if (c.getKey().equals(key)) {
                return c.value;
            }
        }
        return "";
    }

    public static String getEnumKey(String name) {
        for (CertificateTypeEnum c : CertificateTypeEnum.values()) {
            if (c.getValue().equals(name)) {
                return c.key;
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

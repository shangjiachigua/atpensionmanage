package com.caifu.vo.bmfw;

import lombok.Data;

import java.util.Date;

/**
 * @author tww
 * ClassName: BmUserinfoVo.java、
 * createTime: 2020年10月16日 17:06:18
 **/
@Data
public class BmUserinfoVo {
    private String userNo;
    /**
     * 姓名
     */
    private String userName;

    /**
     * 状态 1-正常 9-删除 8-去世
     */
    private String status;

    /**
     * 证件类型
     */
    private String certificateType;

    /**
     * 证件号
     */
    private String userPid;

    /**
     * 出生日期
     */
    private String birthDate;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 性别 1-男 2-女
     */
    private String userSex;

    /**
     * 民族
     */
    private String nation;

    /**
     * 户籍地址
     */
    private String registerAddress;

    /**
     * 居住地址
     */
    private String liveAddress;

    /**
     * 居住所在行政区域
     */
    private String liveAreaOfResidence;

    /**
     * 户籍所在行政区域
     */
    private String registerAreaOfResidence;

    /**
     * 所属行政管辖机构
     */
    private String belongOrganization;

    /**
     * 居住状况
     */
    private String liveStatus;

    /**
     * 婚姻状况
     */
    private String marriage;

    /**
     * 政治面貌
     */
    private String political;

    /**
     * 户籍
     */
    private String householdRegister;

    /**
     * 所在地区
     */
    private String location;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 固定电话
     */
    private String tel;

    /**
     * 社保类型
     */
    private String socialSecurityType;

    /**
     * 社保卡号
     */
    private String socialSecurityCardNo;

    /**
     * 退休工资
     */
    private String retireSalary;

    /**
     * 退休单位
     */
    private String retireCompany;

    /**
     * 退休时间
     */
    private String retireTime;

    /**
     * 职业
     */
    private String occupation;

    /**
     * 文化程度
     */
    private String degreeOfEducation;

    /**
     * 养老状况
     */
    private String pensionStatus;

    /**
     * 照顾人群
     */
    private String careCrowd;

    /**
     * 养老机构
     */
    private String pensionInstitutions;

    /**
     * 入驻时间
     */
    private String checkInTime;

    /**
     * 离世日期
     */
    private Date deathDate;

    /**
     * 离世原因
     */
    private String deathReason;

    /**
     * 居家情况
     */
    private String liveHomeStatus;

    /**
     * 持证情况
     */
    private String holdCertificateStatus;

    /**
     * 认知情况
     */
    private String cognitionStatus;

    /**
     * 涉外情况
     */
    private String foreignIdentityStatus;

    /**
     * 安全情况
     */
    private String safeStatus;

    /**
     * 健康情况
     */
    private String healthStatus;

    /**
     * 其他情况
     */
    private String otherStatus;

    /**
     * 头像图片
     */
    private String imgPath;


    private String id;

    /**
     * 老人id
     */
    private String userinfoId;

    /**
     * 总优惠券
     */
    private Integer totalCoupons;

    /**
     * 已用优惠券
     */
    private Integer usedCoupons;

    /**
     * 剩余优惠券
     */
    private Integer unusedCoupons;

    /**
     * 优惠券类型（1.理发2.缝纫）
     */
    private String type;


    private String param;
    private String orgId;
    private String currentPage;
    private String pageSize;
}

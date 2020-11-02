package com.caifu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author yb
 * @since 2020-10-12
 */
@Data
@TableName("user_info")
public class UserInfo implements Serializable{

    private static final long serialVersionUID = 5552413430281742921L;

    @TableId(value = "user_no",type = IdType.INPUT )
    private String userNo;

    /**
     * 姓名
     */
    private String userName;

    /**
     * 状态 1-正常 8-去世  9-删除
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
     * 健康描述
     */
    private String healthDescription;

    /**
     * 离世日期
     */
    private String deathDate;

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
    /**
     * 居住状况 1-人在户在 2-人在户不在 3-户在人不在
     */
    private String liveCondition;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 紧急联系人集合
     */
    @TableField(exist = false)
    private List<UserInfoContact> userInfoContactList;
}

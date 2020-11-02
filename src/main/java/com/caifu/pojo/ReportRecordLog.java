package com.caifu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author yb
 * @since 2020-10-13
 */
@Data
@TableName(value = "report_record_log")
public class ReportRecordLog  implements Serializable {


    private static final long serialVersionUID = -6195890941257564894L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * report_record表主键
     */
    private Integer reportRecordId;

    /**
     * 状态 1：成功 9：失败
     */
    private Integer state;

    /**
     * 姓名
     */
    private String userName;

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
     * 性别
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
     * 手机号码
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
     * 退休工资(单位: 元/月)
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
     * 社保卡号
     */
    private String socialSecurityCardNo;

    /**
     * 养老状况
     */
    private String pensionStatus;

    /**
     * 居家情况
     */
    private String liveHomeStatus;

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
     * 第一联系人姓名
     */
    private String oneContractName;

    /**
     * 第一联系人与老人关系
     */
    private String oneRelationship;

    /**
     * 第一联系人固定电话
     */
    private String oneTel;

    /**
     * 第一联系人详细地址
     */
    private String oneAddress;

    /**
     * 第一联系人手机电话
     */
    private String oneMobile;

    /**
     * 第二联系人姓名
     */
    private String twoCntractName;

    /**
     * 第二联系人与老人关系
     */
    private String twoRelationship;

    /**
     * 第二联系人固定电话
     */
    private String twoTel;

    /**
     * 第二联系人详细地址
     */
    private String twoAddress;

    /**
     * 第二联系人手机电话
     */
    private String twoMobile;

    /**
     * 第三联系人姓名
     */
    private String threeContractName;

    /**
     * 第三联系人与老人关系
     */
    private String threeRelationship;

    /**
     * 第三联系人固定电话
     */
    private String threeTel;

    /**
     * 第三联系人详细地址
     */
    private String threeAddress;

    /**
     * 第三联系人手机电话
     */
    private String threeMobile;

    /**
     * 第四联系人姓名
     */
    private String fourContractName;

    /**
     * 第四联系人与老人关系
     */
    private String fourRelationship;

    /**
     * 第四联系人固定电话
     */
    private String fourTel;

    /**
     * 第四联系人详细地址
     */
    private String fourAddress;

    /**
     * 第四联系人手机电话
     */
    private String fourMobile;

    /**
     * 序号
     */
    private Integer sort;

    /**
     * 错误信息记录
     */
    private String errorMessage;

    private Date createTime;


}

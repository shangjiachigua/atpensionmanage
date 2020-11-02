package com.caifu.excel.userInfoExcel;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.caifu.pojo.UserInfoContact;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * ClassName : UserInfoExcelError
 *
 * @author :  yb
 * @description ：
 * @date : 2020-10-12 16:22
 */
@Data
public class UserInfoExcelError {


    @ExcelProperty(value = "*姓名", index = 0)
    private String userName;

    @ExcelProperty(value = "*证件类型(根据下拉框选择)", index = 1)
    private String certificateType;

    @ExcelProperty(value = "*证件号", index = 2)
    private String userPid;

    @ExcelProperty(value = "出生日期(例:1988-08-22)", index = 3)
    private String birthDate;

    @ExcelProperty(value = "性别(根据下拉框选择)", index = 4)
    private String userSex;

    @ExcelProperty(value = "民族(根据下拉框选择)", index = 5)
    private String nation;

    @ExcelProperty(value = "户籍地址", index = 6)
    private String registerAddress;

    @ExcelProperty(value = "居住地址", index = 7)
    private String liveAddress;

    @ExcelProperty(value = "婚姻状况(根据下拉框选择)", index = 8)
    private String marriage;

    @ExcelProperty(value = "政治面貌(根据下拉框选择)", index = 9)
    private String political;

    @ExcelProperty(value = "*户籍(根据下拉框选择)", index = 10)
    private String householdRegister;

    @ExcelProperty(value = "*所在地区(根据下拉框选择)", index = 11)
    private String location;

    @ExcelProperty(value = "手机号码", index = 12)
    private String mobile;

    @ExcelProperty(value = "固定电话", index = 13)
    private String tel;

    @ExcelProperty(value = "社保类型(根据下拉框选择)", index = 14)
    private String socialSecurityType;

    @ExcelProperty(value = "退休工资(单位: 元/月)", index = 15)
    private String retireSalary;

    @ExcelProperty(value = "退休单位", index = 16)
    private String retireCompany;

    @ExcelProperty(value = "退休时间(例:1985-05-01)", index = 17)
    private Date retireTime;

    @ExcelProperty(value = "职业", index = 18)
    private String occupation;

    @ExcelProperty(value = "文化程度(根据下拉框选择)", index = 19)
    private String degreeOfEducation;

    @ExcelProperty(value = "社保卡号", index = 20)
    private String socialSecurityCardNo;

    @ExcelProperty(value = "*养老状况(根据下拉框选择)", index = 21)
    private String pensionStatus;

    @ExcelProperty(value = "居家情况(根据下拉框选择)", index = 22)
    private String liveHomeStatus;

    @ExcelProperty(value = "照顾人群(根据下拉框选择)", index = 23)
    private String careCrowd;

    @ExcelProperty(value = "养老机构", index = 24)
    private String pensionInstitutions;

    @ExcelProperty(value = "入驻时间(例:1985-05-01)", index = 25)
    private Date checkInTime;

    @ExcelProperty(value = "健康描述(不超过300字)", index = 26)
    private String healthDescription;

    @ExcelProperty(value = "第一联系人姓名", index = 27)
    private String oneContractName;

    @ExcelProperty(value = "第一联系人与老人关系(根据下拉框选择)", index = 28)
    private String oneRelationship;

    @ExcelProperty(value = "第一联系人固定电话", index = 29)
    private String oneTel;

    @ExcelProperty(value = "第一联系人详细地址", index = 30)
    private String oneAddress;

    @ExcelProperty(value = "第一联系人手机电话", index = 31)
    private String oneMobile;

    @ExcelProperty(value = "第二联系人姓名", index = 32)
    private String twoContractName;

    @ExcelProperty(value = "第二联系人与老人关系(根据下拉框选择)", index = 33)
    private String twoRelationship;

    @ExcelProperty(value = "第二联系人固定电话", index = 34)
    private String twoTel;

    @ExcelProperty(value = "第二联系人详细地址", index = 35)
    private String twoAddress;

    @ExcelProperty(value = "第二联系人手机电话", index = 36)
    private String twoMobile;

    @ExcelProperty(value = "第三联系人姓名", index = 37)
    private String threeContractName;

    @ExcelProperty(value = "第三联系人与老人关系(根据下拉框选择)", index = 38)
    private String threeRelationship;

    @ExcelProperty(value = "第三联系人固定电话", index = 39)
    private String threeTel;

    @ExcelProperty(value = "第三联系人详细地址", index = 40)
    private String threeAddress;

    @ExcelProperty(value = "第三联系人手机电话", index = 41)
    private String threeMobile;

    @ExcelProperty(value = "第四联系人姓名", index = 42)
    private String fourContractName;

    @ExcelProperty(value = "第四联系人与老人关系(根据下拉框选择)", index = 43)
    private String fourRelationship;

    @ExcelProperty(value = "第四联系人固定电话", index = 44)
    private String fourTel;

    @ExcelProperty(value = "第四联系人详细地址", index = 45)
    private String fourAddress;

    @ExcelProperty(value = "第四联系人手机电话", index = 46)
    private String fourMobile;

    @ExcelProperty(value = "错误信息", index = 47)
    private String errorMessage;

    /**
     * 序号
     */
    @ExcelIgnore
    private Integer sort;

    /**
     * 紧急联系人集合
     */
    @ExcelIgnore
    private List<UserInfoContact> userInfoContactList;

}

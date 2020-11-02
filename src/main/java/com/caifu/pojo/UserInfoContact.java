package com.caifu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author yb
 * @since 2020-10-12
 */
@Data
@TableName("user_info_contact")
public class UserInfoContact implements  Serializable{


    private static final long serialVersionUID = -7340445621355442586L;
    /**
     * 联系人主键 
     */
    @TableId(value = "contact_no", type = IdType.AUTO)
    private Integer contactNo;

    /**
     * 关联 userInfo 主键
     */
    private String userNo;

    /**
     * 姓名
     */
    private String contactName;

    /**
     * 与老人关系
     */
    private String relationship;

    /**
     * 固定电话
     */
    private String tel;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 是否是紧急联系人 1-是 2否
     */
    private String isEmergencyContact;

    private Date createTime;

}

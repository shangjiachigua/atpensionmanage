package com.caifu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author yb
 * @since 2020-10-13
 */
@Data
@TableName(value = "sys_user")
public class SysUser implements Serializable {


    private static final long serialVersionUID = 1447245602039159856L;
    /**
     * 主键id
     */
    @TableId(value = "USER_NO", type = IdType.AUTO)
    private Integer userNo;

    /**
     * 姓名
     */
    @TableField("USER_NAME")
    private String userName;

    /**
     * 用户头像
     */
    @TableField("USER_PHOTO")
    private String userPhoto;

    /**
     * 用户名（登录账号）
     */
    @TableField("LOGIN_NAME")
    private String loginName;

    /**
     * 密码
     */
    @TableField("PASSWORD")
    private String password;

    /**
     * 手机号
     */
    @TableField("PHONE")
    private String phone;

    /**
     * 加密盐
     */
    @TableField("SALT")
    private String salt;

    /**
     * 明文密码
     */
    @TableField("PAW")
    private String paw;

    /**
     * 部门/职位
     */
    @TableField("POST")
    private String post;

    /**
     * 所属组织
     */
    @TableField("ORG_ID")
    private String orgId;

    /**
     * 状态（0.停用 1.开通 9.删除 ）
     */
    @TableField("STATUS")
    private String status;

    /**
     * 创建人
     */
    @TableField("CREATE_BY")
    private Integer createBy;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    private Date createTime;

    /**
     * 更新人
     */
    @TableField("UPDATE_BY")
    private Integer updateBy;

    /**
     * 更新时间
     */
    @TableField("UPDATE_TIME")
    private Date updateTime;

    /**
     * 最近登录时间
     */
    @TableField("LOGIN_TIME")
    private Date loginTime;

    /**
     * 职责类型  1-系统管理员 2-物业管理员
     */
    @TableField("POST_TYPE")
    private String postType;

    private String token;

    @TableField("PASSWORD_EDIT_FLAG")
    private String passwordEditFlag;


}

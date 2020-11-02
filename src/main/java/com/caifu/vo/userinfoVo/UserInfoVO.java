package com.caifu.vo.userinfoVo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.caifu.pojo.UserInfoContact;
import lombok.Data;
import sun.rmi.runtime.Log;

import java.io.Serializable;
import java.util.Date;
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
public class UserInfoVO implements Serializable {

    private static final long serialVersionUID = -2778383519617029817L;

    private Long currentPage;

    private Long pageSize;


    /**
     * 模糊搜索
     */
    private String searchContent;


    /**
     * 年龄
     */
    private Integer age;

    /**
     * 性别 1-男 2-女
     */
    private String userSex;


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
     * 社保类型
     */
    private String socialSecurityType;


    /**
     * 养老状况
     */
    private String pensionStatus;

    /**
     * 照顾人群
     */
    private String careCrowd;


    /**
     * 居家情况
     */
    private String liveHomeStatus;


    /**
     * 居住状况 1-人在户在 2-人在户不在 3-户在人不在
     */
    private String liveCondition;

    /**
     * 死亡开始时间
     */
    private String deathStartDate;

    /**
     * 死亡结束时间
     */
    private String deathEndDate;

}

package com.caifu.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 组织架构表
 * </p>
 *
 * @author tww
 * @since 2020-10-20
 */
@Data
public class SysOrganization extends Model<SysOrganization> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private int id;

    /**
     * 名称
     */
    private String orgName;

    /**
     * 父id
     */
    private int pid;

    /**
     * 祖级列表
     */
    private String ancestral;

    /**
     * 类型（关联sys_organization_type）
     */
    private String orgTypeId;

    /**
     * 显示顺序
     */
    private Integer orderNum;

    /**
     * 状态（1.正常 9.删除）
     */
    private String status;

    /**
     * 地址
     */
    private String orgAddress;

    /**
     * 创建时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;

    /**
     * 更新时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updatedTime;

    /**
     * 子节点列表
     */
    @TableField(exist = false)
    private List<SysOrganization> sysOrganizations = new ArrayList<>();

}

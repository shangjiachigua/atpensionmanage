package com.caifu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author yb
 * @since 2020-10-13
 */
@Data
@TableName(value = "report_record")
public class ReportRecord implements Serializable {


    private static final long serialVersionUID = 2484729903648085544L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 导入文件名称
     */
    private String reportName;

    /**
     * 导入时间
     */
    private Date createTime;

    /**
     * 导入类型 1-居民报表 2-房屋报表
     */
    private Integer reportType;

    /**
     * 操作人员id(关联sys_user)
     */
    private Integer sysUserNo;

    /**
     * 导入结果
     */
    private String importResult;

    /**
     * 小区id（SysOrganization的orgId）
     */
    private String villageNo;


    /**
     *  创建时间
     */
    @TableField(exist = false)
    private String formatCreateTime;

    /**
     * 类型名称
     */
    @TableField(exist = false)
    private String formatReportTypeName;

    /**
     * 机构名称
     */
    @TableField(exist = false)
    private String formatOrgName;

    /**
     * 操作员姓名
     */
    @TableField(exist = false)
    private String sysName;

    /**
     * 成功数量
     */
    @TableField(exist = false)
    private Integer successCount;
    /**
     * 失败数量
     */
    @TableField(exist = false)
    private Integer failCount;


}

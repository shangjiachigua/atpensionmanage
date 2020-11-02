package com.caifu.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 活动室
 * </p>
 *
 * @author tww
 * @since 2020-10-16
 */
@Data
public class BmActivityManager extends Model<BmActivityManager> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.UUID)
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 类型（1.理发店2.缝纫店，可多选）
     */
    private String type;

    /**
     * 组织id
     */
    private String orgId;

    /**
     * 地址
     */
    private String address;

    /**
     * 联系人
     */
    private String contacts;

    /**
     * 联系方式
     */
    private String phone;

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
     * 状态(1.正常 9.删除)
     */
    private String status;

}

package com.caifu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 标签表
 * </p>
 *
 * @author yb
 * @since 2020-10-12
 */
@Data
@TableName("ys_label")
public class YsLabel  implements Serializable {

    private static final long serialVersionUID = 4684412826897679254L;

    /**
     * 标签编号
     */
    @TableId(value = "LABEL_CODE",type = IdType.INPUT )
    private String labelCode;

    /**
     * 标签名称
     */
    private String labelName;

    /**
     * 父类编号
     */
    private String parentCode;

    /**
     * 标签图片
     */
    private String imgPath;

    /**
     * 状态 1-正常 9-删除 0-禁用
     */
    private Integer status;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 是否展示      1-展示 -不展示
     */
    private String isShow;

    /**
     * 是否能修改  1-可修改 2-不可修改 
     */
    private Integer editable;

    /**
     * 是否属于养老 1-是 2-否
     */
    private String isPension;


}

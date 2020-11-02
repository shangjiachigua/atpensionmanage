package com.caifu.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 优惠券来源消费记录
 * </p>
 *
 * @author tww
 * @since 2020-10-16
 */
@Data
public class BmCouponsRecord extends Model<BmCouponsRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.UUID)
    private String id;

    /**
     * 优惠老人信息id
     */
    private String bmUserinfoId;

    /**
     * 来源/使用
     */
    private String sourceUse;

    /**
     * 消费或获得优惠券数量
     */
    private String number;

    /**
     * 类型（1.理发2.缝纫）
     */
    private String type;

    /**
     * 消费类型（1.发放2.消费）
     */
    private String hairCut;

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

}

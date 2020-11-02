package com.caifu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 优惠老人信息表
 * </p>
 *
 * @author tww
 * @since 2020-10-16
 */
@Data
public class BmUserinfo extends Model<BmUserinfo> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.UUID)
    private String id;

    /**
     * 老人id
     */
    private String userinfoId;

    /**
     * 总优惠券
     */
    private Integer totalCoupons;

    /**
     * 已用优惠券
     */
    private Integer usedCoupons;

    /**
     * 剩余优惠券
     */
    private Integer unusedCoupons;

    /**
     * 优惠券类型（1.理发2.缝纫）
     */
    private String type;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 更新时间
     */
    private Date updatedTime;

    /**
     * 状态（1.正常9.删除）
     */
    private String status;

}

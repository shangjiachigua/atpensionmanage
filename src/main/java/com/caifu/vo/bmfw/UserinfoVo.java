package com.caifu.vo.bmfw;

import lombok.Data;

/**
 * @author tww
 * ClassName: UserinfoVo.java、
 * createTime: 2020年10月21日 14:16:54
 **/
@Data
public class UserinfoVo {
    /**
     * 姓名
     */
    private String userName;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 性别 1-男 2-女
     */
    private String userSex;

    /**
     * 头像图片
     */
    private String imgPath;

    /**
     * 剩余优惠券
     */
    private Integer unusedCoupons;
}

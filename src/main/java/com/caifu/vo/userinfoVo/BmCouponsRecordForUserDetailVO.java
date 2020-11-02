package com.caifu.vo.userinfoVo;

import lombok.Data;

/**
 * ClassName : BmCouponsRecordForUserDetailVO
 *
 * @author :  yb
 * @description ：
 * @date : 2020-11-02 15:03
 */
@Data
public class BmCouponsRecordForUserDetailVO {


    private Long currentPage;

    private Long pageSize;
    /**
     * userInfo_no
     */
    private String userNo;
    /**
     * 1.理发2.缝纫）
     */
    private Integer type;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;


}

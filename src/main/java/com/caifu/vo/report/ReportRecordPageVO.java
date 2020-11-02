package com.caifu.vo.report;

import lombok.Data;


/**
 * ClassName :ReportRecordPageVO
 *
 * @author :  yb
 * @description ：
 * @date : 2020-10-14 13:00
 */
@Data
public class ReportRecordPageVO {

    /**
     * 当前页
     */
    private Integer currentPage;

    /**
     * 每页数量
     */
    private Integer pageSize;


}

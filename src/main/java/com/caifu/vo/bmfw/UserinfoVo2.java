package com.caifu.vo.bmfw;

import lombok.Data;

/**
 * @author tww
 * ClassName: UserinfoVo2.java、
 * createTime: 2020年10月28日 10:45:28
 **/
@Data
public class UserinfoVo2 {
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
     * 行政区域
     */
    private String orgId;

    private Long currentPage;
    private Long pageSize;
}

package com.caifu.vo.report;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * ClassName :ReportRecordVO
 *
 * @author :  yb
 * @description ：
 * @date : 2020-10-13 09:49
 */
@Data
public class ReportRecordVO {

    /**
     * 导入类型 1-老人档案报表
     */
    private Integer reportType;

    private MultipartFile file;

    /**
     * 小区编号
     */
    private String villageNo;

    /**
     * 增加或者覆盖 1-增加 2-覆盖
     */
    private Integer addOrCover;


}

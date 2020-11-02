package com.caifu.vo.label;

import lombok.Data;


/**
 * ClassName :SaveMsLabelVO
 *
 * @author :  yb
 * @description ：
 * @date : 2020-10-16 14:36
 */
@Data
public class SaveMsLabelVO {

    /**
     * 父级labelCode
     */
    private String parentCode;

    /**
     * 标签名称
     */
    private String labelName;

}

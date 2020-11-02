package com.caifu.excel.userInfoExcel;

import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import com.caifu.enums.LabelTypeEnum;
import com.caifu.service.YsLabelService;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName :SpinnerWriteHandler
 * 居民信息报表下拉框
 *
 * @author :  yb
 * @description ： 下拉框
 * @date : 2020-09-08 10:26
 */
public class SpinnerWriteHandler implements SheetWriteHandler {


    private final YsLabelService ysLabelService;

    public SpinnerWriteHandler(YsLabelService ysLabelService) {
        this.ysLabelService = ysLabelService;
    }


    @Override
    public void beforeSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {

    }

    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {

        //证件类型
        String[] certificateArr = ysLabelService.getLabelNameArray(LabelTypeEnum.ZJLX.getKey());
        //性别
        String[] sexArr = new String[]{"男", "女"};
        //民族
        String[] nationArr = ysLabelService.getLabelNameArray(LabelTypeEnum.MZ.getKey());
        //婚姻状况
        String[] marriageArr = ysLabelService.getLabelNameArray(LabelTypeEnum.HYQK.getKey());
        //政治面貌
        String[] politicalArr = ysLabelService.getLabelNameArray(LabelTypeEnum.ZZMM.getKey());
        //户籍
        String[] householdRegisterArr = ysLabelService.getLabelNameArray(LabelTypeEnum.HJ.getKey());
        //所在地区
        String[] locationArr = ysLabelService.getLabelNameArray(LabelTypeEnum.SZDQ.getKey());
        //社保类型
        String[] socialSecurityArr = ysLabelService.getLabelNameArray(LabelTypeEnum.SBLX.getKey());
        //文化程度
        String[] educationArr = ysLabelService.getLabelNameArray(LabelTypeEnum.WHCD.getKey());
        //养老状况
        String[] pensionStatusArr = ysLabelService.getLabelNameArray(LabelTypeEnum.YLZK.getKey());
        //居家状况
        String[] liveHomeStatusArr = ysLabelService.getLabelNameArray(LabelTypeEnum.JJQK.getKey());
        //照顾人群
        String[] careCrowdArr = ysLabelService.getLabelNameArray(LabelTypeEnum.ZGRQ.getKey());
        //关系
        String[] relationshipArr = ysLabelService.getLabelNameArray(LabelTypeEnum.YLRGX.getKey());


        Map<Integer, String[]> mapDropDown = new HashMap<>(15);
        mapDropDown.put(1, certificateArr);
        mapDropDown.put(4, sexArr);
        mapDropDown.put(5, nationArr);
        mapDropDown.put(8, marriageArr);
        mapDropDown.put(9, politicalArr);
        mapDropDown.put(10, householdRegisterArr);
        mapDropDown.put(11, locationArr);
        mapDropDown.put(14, socialSecurityArr);
        mapDropDown.put(19, educationArr);
        mapDropDown.put(21, pensionStatusArr);
        mapDropDown.put(22, liveHomeStatusArr);
        mapDropDown.put(23, careCrowdArr);
        mapDropDown.put(28, relationshipArr);
        mapDropDown.put(33, relationshipArr);
        mapDropDown.put(38, relationshipArr);
        mapDropDown.put(43, relationshipArr);

        CommonHandle commonHandle = new CommonHandle();
        commonHandle.processSpinnerWriter(writeSheetHolder, mapDropDown);
    }


}

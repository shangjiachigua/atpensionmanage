package com.caifu.excel.userInfoExcel;

import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;

import java.util.Map;

/**
 * ClassName :CommonHandle
 *
 * @author :  yb
 * @description ：
 * @date : 2020-09-09 09:39
 */
class CommonHandle {


     void processSpinnerWriter(WriteSheetHolder writeSheetHolder, Map<Integer,String []> mapDropDown) {

        Sheet sheet = writeSheetHolder.getSheet();
//        ///开始设置下拉框 //设置下拉框
        DataValidationHelper helper = sheet.getDataValidationHelper();
        for (Map.Entry<Integer, String[]> entry : mapDropDown.entrySet()) {

            //起始行、终止行、起始列、终止列
            CellRangeAddressList addressList = new CellRangeAddressList(1, 60000, entry.getKey(), entry.getKey());
            //设置下拉框数据
            DataValidationConstraint constraint = helper.createExplicitListConstraint(entry.getValue());
            DataValidation dataValidation = helper.createValidation(constraint, addressList);
            //处理Excel兼容性问题
            if (dataValidation instanceof XSSFDataValidation) {
                dataValidation.setSuppressDropDownArrow(true);
                dataValidation.setShowErrorBox(true);
            } else {
                dataValidation.setSuppressDropDownArrow(false);
            }
            sheet.addValidationData(dataValidation);
        }

    }





}

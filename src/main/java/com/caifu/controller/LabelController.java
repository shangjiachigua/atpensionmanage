package com.caifu.controller;

import com.caifu.bean.Result;
import com.caifu.bean.ResultUtil;
import com.caifu.service.YsLabelService;
import com.caifu.util.Utils;
import com.caifu.vo.label.SaveMsLabelVO;
import com.caifu.vo.label.UpdateMsLabelVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * ClassName : LabelController
 *
 * @author :  yb
 * @description ：
 * @date : 2020-10-12 14:22
 */
@Controller
@RequestMapping(value = "/label")
public class LabelController {


    @Resource
    private YsLabelService ysLabelService;




    /**
     * 保存标签名称
     */
    @RequestMapping(value = "saveLabel", method = RequestMethod.POST)
    @ResponseBody
    public Result saveLabel(SaveMsLabelVO saveMsLabelVO) {
        try {
            return ysLabelService.saveLabel(saveMsLabelVO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error();
        }
    }

    /**
     * 修改标签
     */
    @RequestMapping(value = "updateLabel", method = RequestMethod.POST)
    @ResponseBody
    public Result updateLabel(UpdateMsLabelVO updateMsLabelVO) {
        try {
            return ysLabelService.updateLabel(updateMsLabelVO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error();
        }
    }

    /**
     * 删除 标签
     */
    @RequestMapping(value = "deleteLabel", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteLabel(String labelCode) {
        try {
            return ysLabelService.deleteLabel(labelCode);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error();
        }
    }




}

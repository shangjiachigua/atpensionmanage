package com.caifu.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.caifu.bean.Result;
import com.caifu.pojo.BmCouponsRecord;
import com.caifu.service.BmCouponsRecordService;
import com.caifu.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 优惠券消费记录
 *
 * @author tianwenwen
 * ClassName: BmCouponsRecordController.java
 * createTime: 2020年10月16日 11:03:00
 **/
@Controller
public class BmCouponsRecordController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(BmActivityManagerController.class);

    @Resource
    private BmCouponsRecordService bmCouponsRecordService;

    /**
     * 根据老人id获取优惠券记录列表
     */
    @GetMapping("getRecordById")
    @ResponseBody
    public Result getRecordById(String id, String type, String currentPage, String pageSize) {
        try {
            IPage<BmCouponsRecord> record = bmCouponsRecordService.getRecordById(id, type, currentPage, pageSize);
            return success(0, "操作成功", record);
        } catch (Exception e) {
            String msg = "网络异常";
            if (Utils.isNotNull(e.getMessage())) {
                msg = e.getMessage();
            }
            logger.error(e.toString());
            e.printStackTrace();
            return error(500, msg);
        }
    }
}

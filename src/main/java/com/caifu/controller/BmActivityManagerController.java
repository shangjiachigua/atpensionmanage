package com.caifu.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.caifu.bean.Result;
import com.caifu.pojo.BmActivityManager;
import com.caifu.service.BmActivityManagerService;
import com.caifu.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 活动室管理
 *
 * @author tianwenwen
 * ClassName: BmActivityManagerController.java
 * createTime: 2020年10月16日 11:01:59
 **/
@Controller
@RequestMapping("activity")
public class BmActivityManagerController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(BmActivityManagerController.class);

    @Resource
    private BmActivityManagerService bmActivityManagerService;

    private String prefix = "convenience/";

    /**
     * 活动室页面
     */
    @RequestMapping("toActivity")
    public String toActivity() {
        return prefix + "activityroom_list";
    }

    /**
     * 活动室详情页面
     */
    @RequestMapping("toActivityDetails")
    public String toActivityDetails() {
        return prefix + "activityroom_details";
    }

    /**
     * 活动室添加页面
     */
    @RequestMapping("toActivityAdd")
    public String toActivityAdd() {
        return prefix + "activityroom_add";
    }

    /**
     * 活动室列表
     */
    @GetMapping("getActivityList")
    @ResponseBody
    public Result getActivityList(String param, String currentPage, String pageSize) {
        try {
            IPage<BmActivityManager> activityList = bmActivityManagerService.getActivityList(param, currentPage, pageSize);
            return success(0, "成功", activityList);
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

    /**
     * 活动室添加
     */
    @PostMapping("addActivity")
    @ResponseBody
    public Result addActivity(BmActivityManager activityManager) {
        try {
            return bmActivityManagerService.addActivity(activityManager);
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

    /**
     * 根据id获取活动室
     */
    @GetMapping("getActivityById")
    @ResponseBody
    public Result getActivityById(String id) {
        try {
            BmActivityManager activityById = bmActivityManagerService.getActivityById(id);
            return success(200, "成功", activityById);
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

    /**
     * 根据id删除活动室
     */
    @DeleteMapping("deleteActivityById")
    @ResponseBody
    public Result deleteActivityById(String id) {
        try {
            bmActivityManagerService.deleteActivityById(id);
            return success(200, "删除成功");
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

    /**
     * 活动室登录
     */
    @RequestMapping("bmLogin")
    @ResponseBody
    public Map<String, Object> bmLogin(String phone, HttpServletRequest request) {
        try {
            return bmActivityManagerService.bmLogin(phone, request);
        } catch (Exception e) {
            String msg = "网络异常";
            if (Utils.isNotNull(e.getMessage())) {
                msg = e.getMessage();
            }
            logger.error(e.toString());
            e.printStackTrace();
            HashMap<String, Object> map = new HashMap<>(5);
            map.put("code", 500);
            map.put("msg", msg);
            return map;
        }
    }

    /**
     * 老人信息
     */
    @RequestMapping("getUserinfoByUserPid")
    @ResponseBody
    public Result getUserinfoByUserPid(String type, String userPid, String tokenId, String phone) {
        try {
            return bmActivityManagerService.getUserinfoByUserPid(type, userPid, tokenId, phone);
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

    /**
     * 优惠券核销
     */
    @RequestMapping("pinCoupon")
    @ResponseBody
    public Result pinCoupon(String type, String userPid, String tokenId, String phone) {
        try {
            return bmActivityManagerService.pinCoupon(type, userPid, tokenId, phone);
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

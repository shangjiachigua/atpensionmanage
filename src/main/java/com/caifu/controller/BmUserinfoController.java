package com.caifu.controller;

import com.caifu.bean.Result;
import com.caifu.bean.ResultUtil;
import com.caifu.service.BmUserinfoService;
import com.caifu.service.UserInfoService;
import com.caifu.util.Utils;
import com.caifu.vo.bmfw.BmUserinfoVo;
import com.caifu.vo.userinfoVo.UserInfoVO;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 描述：优惠老人信息
 *
 * @author tianwenwen
 * ClassName: BmUserinfoController.java、
 * createTime: 2020年10月16日 11:08:43
 **/
@Controller
@RequestMapping("bmuserinfo")
public class BmUserinfoController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(BmUserinfoController.class);

    @Resource
    private BmUserinfoService bmUserinfoService;

    @Resource
    private UserInfoService userInfoService;

    private String prefix = "convenience/";

    /**
     * 跳转理发老人页
     */
    @GetMapping("toHaircutInfoPage")
    public String toHaircutInfoPage() {
        return prefix + "haircutInfo_list";
    }

    /**
     * 跳转缝纫老人页
     */
    @GetMapping("toSewingInfoPage")
    public String toSewingInfoPage() {
        return prefix + "sewingInfo_list";
    }

    /**
     * 跳转添加老人页
     */
    @GetMapping("toAddBmUserinfo")
    public String toAddBmUserinfo(String type, Model model) {
        model.addAttribute("type", type);
        return prefix + "bmuserinfo_add";
    }

    /**
     * 添加老人优惠券信息
     */
    @PostMapping("addBmUserinfo")
    @ResponseBody
    public Result addBmUserinfo(String type, String ids, String coupons) {
        try {
            return bmUserinfoService.addBmUserinfo(type, ids, coupons);
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
     * 查询老人优惠信息列表
     */
    @GetMapping("getBmUserinfoList")
    @ResponseBody
    public Result getBmUserinfoList(BmUserinfoVo bmUserinfoVo) {
        try {
            PageInfo<BmUserinfoVo> bmUserinfoList = bmUserinfoService.getBmUserinfoList(bmUserinfoVo);
            return success(0, "成功", bmUserinfoList);
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
     * 批量发券
     */
    @PostMapping("batchCoupons")
    @ResponseBody
    public Result batchCoupons(String type, String ids, String coupons) {
        try {
            bmUserinfoService.batchCoupons(type, ids, coupons);
            return success(200, "操作成功");
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
     * 批量移除
     */
    @PostMapping("batchRemove")
    @ResponseBody
    public Result batchRemove(String type, String ids) {
        try {
            bmUserinfoService.batchRemove(type, ids);
            return success(200, "操作成功");
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
     * 跳老人详情页
     */
    @GetMapping("toBmUserinfoPage")
    public String toBmUserinfoPage(String id, String type, Model model) {
        try {
            BmUserinfoVo bmUserinfoVo = bmUserinfoService.getBmUserInfoByid(id, type);
            model.addAttribute("userinfo", bmUserinfoVo);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
        }
        return prefix + "bmuserinfo_details";
    }

    /**
     * 查询老人列表
     */
    @GetMapping("getUserinfoList")
    @ResponseBody
    public Result getUserinfoList(UserInfoVO userInfoVO) {
        try {
            Result userInfoPageList = userInfoService.getUserInfoPageList(userInfoVO, "1");
            return success(200, "success", userInfoPageList);
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
     * 获取老人档案 健在老人
     */
    @GetMapping(value = "getBmUserInfoPageList")
    @ResponseBody
    public Result getBmUserInfoPageList(BmUserinfoVo userInfoVO) {
        try {
            return userInfoService.getBmUserInfoPageList(userInfoVO, "1");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error();
        }
    }


    /**
     * 测试
     */
    @GetMapping("getBy")
    @ResponseBody
    public Result getBy(String id, String type) {
        BmUserinfoVo bmUserinfoVo = null;
        try {
            bmUserinfoVo = bmUserinfoService.getBmUserInfoByid(id, type);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
        }
        return success(200, "success", bmUserinfoVo);
    }
}

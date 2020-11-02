package com.caifu.controller;

import com.caifu.bean.Result;
import com.caifu.pojo.SysOrganization;
import com.caifu.service.SysOrganizationService;
import com.caifu.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 组织架构
 *
 * @author tww
 * ClassName: SysOrganizationController.java、
 * createTime: 2020年10月20日 10:49:14
 **/
@Controller
@RequestMapping("sysOrganization")
public class SysOrganizationController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(SysOrganizationController.class);

    @Resource
    private SysOrganizationService sysOrganizationService;

    /**
     * 获取组织架构列表
     */
    @GetMapping("getSysOrganizationList")
    @ResponseBody
    public Result getSysOrganizationList() {
        try {
            List<SysOrganization> sysOrganizationList = sysOrganizationService.getSysOrganizationList();
            return success(200, "获取成功", sysOrganizationList);
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
     * 获取当前管理员组织架构列表
     */
    @GetMapping("getCurrentUserSysOrganizationList")
    @ResponseBody
    public List<SysOrganization> getCurrentUserSysOrganizationList() {
        return null;
    }

    /**
     * 添加组织
     */
    @PostMapping("addSysOrganization")
    @ResponseBody
    public Result addSysOrganization(SysOrganization sysOrganization) {
        try {
            sysOrganizationService.addSysOrganization(sysOrganization);
            return success(200, "保存成功");
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

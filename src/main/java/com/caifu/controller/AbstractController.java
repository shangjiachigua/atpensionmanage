package com.caifu.controller;

import com.caifu.bean.Result;
import com.caifu.bean.ResultUtil;
import com.caifu.pojo.SysUser;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;


/**
 * @author :debug
 * @date : 2020-10-12 14:22
 **/
@Controller
public abstract class AbstractController {

    /**
     * 获取当前登录用户
     *
     * @return SysUsers
     */
    protected SysUser getSysUser() {
        return (SysUser) SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * 返回成功
     */
    protected Result success(Integer code, String msg) {
        return ResultUtil.success(code, msg);
    }

    /**
     * 返回成功
     */
    protected Result success(Integer code, String msg, Object data) {
        return ResultUtil.success(code, msg, data);
    }

    /**
     * 返回失败消息
     */
    protected Result error(Integer code, String message) {
        return ResultUtil.error(code, message);
    }

}
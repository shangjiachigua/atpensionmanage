package com.caifu.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.caifu.bean.Result;
import com.caifu.bean.ResultUtil;
import com.caifu.enums.LabelTypeEnum;
import com.caifu.excel.userInfoExcel.Custemhandler;
import com.caifu.excel.userInfoExcel.SpinnerWriteHandler;
import com.caifu.excel.userInfoExcel.UserInfoExcel;
import com.caifu.excel.userInfoExcel.UserInfoExcelError;
import com.caifu.pojo.SysUser;
import com.caifu.pojo.UserInfo;
import com.caifu.service.BmUserinfoService;
import com.caifu.service.UserInfoService;
import com.caifu.service.YsLabelService;
import com.caifu.util.Constance;
import com.caifu.util.Utils;
import com.caifu.vo.report.ReportRecordPageVO;
import com.caifu.vo.report.ReportRecordVO;
import com.caifu.vo.userinfoVo.BmCouponsRecordForUserDetailVO;
import com.caifu.vo.userinfoVo.UserInfoVO;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

/**
 * ClassName : UserInfoController
 *
 * @author :  yb
 * @description ：
 * @date : 2020-10-12 14:07
 */
@Controller
@RequestMapping(value = "/userInfo")
public class UserInfoController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(UserInfoController.class);

    @Resource
    private UserInfoService userInfoService;
    @Resource
    private YsLabelService ysLabelService;
    @Resource
    private BmUserinfoService bmUserinfoService;
    /**
     * 下載PDF时候 模板引擎
     */
    @Resource
    private TemplateEngine templateEngine;


    /**
     * 跳转老人档案列表 -健在老人
     *
     * @return Result
     */
    @RequestMapping(value = "jumpUserInfoList", method = RequestMethod.GET)
    public ModelAndView jumpUserInfoList() {
        ModelAndView modelAndView = new ModelAndView();
        try {
            modelAndView.setViewName("/userInfo/userInfo_list");
            setParamModelAndView(modelAndView);
        } catch (Exception ex) {
            logger.error(ex.toString());
        }
        return modelAndView;
    }

    /**
     * 跳转老人档案列表  -去世老人
     *
     * @return Result
     */
    @GetMapping(value = "jumpUserInfoListOfDeath")
    public ModelAndView jumpUserInfoListOfDeath() {
        ModelAndView modelAndView = new ModelAndView();
        try {
            modelAndView.setViewName("/userInfo/userInfo_list_death");
            setParamModelAndView(modelAndView);
        } catch (Exception ex) {
            logger.error(ex.toString());
        }
        return modelAndView;
    }

    /**
     * 获取老人档案列表 -健在老人
     *
     * @return Result
     */
    @GetMapping(value = "getUserInfoPageList")
    @ResponseBody
    public Result getUserInfoPageList(UserInfoVO userInfoVO) {
        try {
            return userInfoService.getUserInfoPageList(userInfoVO, "1");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error();
        }
    }


    /**
     * 获取老人档案列表 - 离世老人
     *
     * @return Result
     */
    @GetMapping(value = "getUserInfoPageListOfDeath")
    @ResponseBody
    public Result getUserInfoPageListOfDeath(UserInfoVO userInfoVO) {
        try {
            return userInfoService.getUserInfoPageList(userInfoVO, "8");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error();
        }
    }

    /**
     * 跳转老人档案添加页面
     *
     * @return Result
     */
    @RequestMapping(value = "jumpUserInfoAdd")
    public ModelAndView jumpUserInfoAdd() {
        ModelAndView modelAndView = new ModelAndView();
        try {
            modelAndView.setViewName("/userInfo/userInfo_add");
            setParamModelAndView(modelAndView);
        } catch (Exception ex) {
            logger.error(ex.toString());
        }
        return modelAndView;

    }


    private void setParamModelAndView(ModelAndView modelAndView) {
        //证件类型
        modelAndView.addObject("certificateList", ysLabelService.getYsLabelList(LabelTypeEnum.ZJLX.getKey()));
        //民族
        modelAndView.addObject("nationList", ysLabelService.getYsLabelList(LabelTypeEnum.MZ.getKey()));
        //婚姻状况
        modelAndView.addObject("marriageList", ysLabelService.getYsLabelList(LabelTypeEnum.HYQK.getKey()));
        //户籍
        modelAndView.addObject("householdRegisterList", ysLabelService.getYsLabelList(LabelTypeEnum.HJ.getKey()));
        //所在地区
        modelAndView.addObject("locationList", ysLabelService.getYsLabelList(LabelTypeEnum.SZDQ.getKey()));
        //户籍行政区

        //居住行政区

        //社保类型
        modelAndView.addObject("socialSecurityList", ysLabelService.getYsLabelList(LabelTypeEnum.SBLX.getKey()));
        //文化程度
        modelAndView.addObject("educationList", ysLabelService.getYsLabelList(LabelTypeEnum.WHCD.getKey()));
        //政治面貌
        modelAndView.addObject("politicalList", ysLabelService.getYsLabelList(LabelTypeEnum.ZZMM.getKey()));
        //关系
        modelAndView.addObject("relationshipList", ysLabelService.getYsLabelList(LabelTypeEnum.YLRGX.getKey()));
        //养老状况
        modelAndView.addObject("pensionStatusList", ysLabelService.getYsLabelList(LabelTypeEnum.YLZK.getKey()));
        //养老机构

        //照顾人群
        modelAndView.addObject("careCrowdList", ysLabelService.getYsLabelList(LabelTypeEnum.ZGRQ.getKey()));
        //居家情况
        modelAndView.addObject("liveHomeStatusList", ysLabelService.getYsLabelList(LabelTypeEnum.JJQK.getKey()));
        //持证情况
        modelAndView.addObject("holdCertificateStatusList", ysLabelService.getYsLabelList(LabelTypeEnum.CZQK.getKey()));
        //认知情况
        modelAndView.addObject("cognitionStatusList", ysLabelService.getYsLabelList(LabelTypeEnum.RZQK.getKey()));
        //涉外情况
        modelAndView.addObject("foreignIdentityStatusList", ysLabelService.getYsLabelList(LabelTypeEnum.SWQK.getKey()));
        //安全情况
        modelAndView.addObject("safeStatusList", ysLabelService.getYsLabelList(LabelTypeEnum.AQQK.getKey()));
        //健康情况
        modelAndView.addObject("healthStatusList", ysLabelService.getYsLabelList(LabelTypeEnum.JKQK.getKey()));
        //其他情况
        modelAndView.addObject("otherStatusList", ysLabelService.getYsLabelList(LabelTypeEnum.QTQK.getKey()));
    }


    /**
     * 跳转老人档案 修改页面
     *
     * @return Result
     */
    @RequestMapping(value = "jumpUserInfoUpdate")
    public ModelAndView jumpUserInfoUpdate(String userNo) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            modelAndView.setViewName("/userInfo/userInfo_update");
            modelAndView.addObject("userNo", userNo);
            setParamModelAndView(modelAndView);
        } catch (Exception ex) {
            logger.error(ex.toString());
        }
        return modelAndView;
    }

    /**
     * 通过 userNo 获取userInfo
     *
     * @param userNo userNo
     * @return Result
     */
    @PostMapping(value = "loadUserInfoByUserNo")
    @ResponseBody
    public Result loadUserInfoByUserNo(String userNo) {
        try {
            UserInfo userInfo = userInfoService.loadUserInfoByUserNo(userNo);
            return ResultUtil.getDataSuccess(userInfo);
        } catch (Exception ex) {
            logger.error(ex.toString());
            return ResultUtil.error();
        }
    }


    /**
     * 删除联系人
     *
     * @param contactNo contactNo
     * @return Result
     */
    @PostMapping(value = "deleteUserInfoContact")
    @ResponseBody
    public Result deleteUserInfoContact(String contactNo) {
        try {
            return userInfoService.deleteUserInfoContact(contactNo);
        } catch (Exception ex) {
            logger.error(ex.toString());
            return ResultUtil.error();
        }
    }


    /**
     * 保存老人
     *
     * @return Result
     */
    @PostMapping(value = "saveUserInfo")
    @ResponseBody
    public Result saveUserInfo(@RequestBody UserInfo userInfo) {
        try {
            return userInfoService.saveUserInfo(userInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error();
        }
    }


    /**
     * 修改老人
     *
     * @return Result
     */
    @PostMapping(value = "updateUserInfo")
    @ResponseBody
    public Result updateUserInfo(@RequestBody UserInfo userInfo) {
        try {
            return userInfoService.updateUserInfo(userInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error();
        }
    }


    /**
     * 查看老人档案 detail
     *
     * @return Result
     */
    @GetMapping(value = "jumpUserInfoDetail")
    @ResponseBody
    public ModelAndView jumpUserInfoDetail(String userNo) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            modelAndView.setViewName("/userInfo/userInfo_detail");
            //获取老人基本信息
            UserInfo userInfo = userInfoService.getUserInfoDetailByUserNo(userNo);
            modelAndView.addObject("userInfo", userInfo);
            // 优惠券基本信息  type 优惠券类型（1.理发2.缝纫）
            modelAndView.addObject("bmUserInfoOfFair", bmUserinfoService.getBmUserInfoByUserInfoId(userNo, 1));
            modelAndView.addObject("bmUserInfoOfSewing", bmUserinfoService.getBmUserInfoByUserInfoId(userNo, 2));

            setParamModelAndView(modelAndView);
        } catch (Exception ex) {
            logger.error(ex.toString());
        }
        return modelAndView;
    }

    /**
     * 查看老人档案 detail 中 便民 分页查询
     */
    @GetMapping(value = "getBmCouponsRecordListOfUserInfoId")
    @ResponseBody
    public Result getBmCouponsRecordListOfUserInfoId(BmCouponsRecordForUserDetailVO vo) {
        try {
            return bmUserinfoService.getBmCouponsRecordListOfUserInfoId(vo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error();
        }
    }

    /**
     * 删除老人
     *
     * @return Result
     */
    @PostMapping(value = "deleteUserInfoByUserNo")
    @ResponseBody
    public Result deleteUserInfoByUserNo(String userNo) {
        try {
            return userInfoService.deleteUserInfoByUserNo(userNo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error();
        }
    }


    /**
     * 处理老人 离世
     *
     * @return Result
     */
    @PostMapping(value = "dealDeathUserInfoByUserNo")
    @ResponseBody
    public Result dealDeathUserInfoByUserNo(UserInfo userInfo) {
        try {
            return userInfoService.dealDeathUserInfoByUserNo(userInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error();
        }
    }


    /**
     * 老人 离世 页面
     *
     * @return Result
     */
    @RequestMapping(value = "jumpUserInfoDetailOfDeath")
    public ModelAndView jumpUserInfoDetailOfDeath(String userNo) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            modelAndView.setViewName("/userInfo/userInfo_detail_death");
            //获取老人基本信息
            UserInfo userInfo = userInfoService.getUserInfoDetailByUserNo(userNo);
            modelAndView.addObject("userInfo", userInfo);
            setParamModelAndView(modelAndView);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return modelAndView;
    }


    /**
     * 老人档案标签
     *
     * @return Result
     */
    @RequestMapping(value = "jumpUserInfoLabel")
    public ModelAndView jumpUserInfoLabel() {
        ModelAndView modelAndView = new ModelAndView();
        try {
            modelAndView.addObject("pensionLabel", ysLabelService.getPensionLabelType().getData());
            modelAndView.setViewName("/userInfo/userInfo_label");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelAndView;
    }


    /**
     * 老人标签 分页
     *
     * @param labelCode 主labelCode
     * @return Result
     */
    @GetMapping(value = "getLabelListByLabelCodePagination")
    @ResponseBody
    public Result getLabelListByLabelCodePagination(String labelCode, Integer currentPage, Integer pageSize) {
        try {
            Result result = ysLabelService.getLabelListByLabelCodePagination(labelCode, currentPage, pageSize);
            result.setCode(0);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error();
        }
    }


    /**
     * 老人档案  批量导入 页面
     *
     * @return Result
     */
    @RequestMapping(value = "jumpUserInfoImportList")
    public ModelAndView jumpUserInfoImportList() {
        ModelAndView modelAndView = new ModelAndView();
        try {
            modelAndView.setViewName("/userInfo/userInfo_import_list");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelAndView;
    }


    /**
     *  批量导入 列表
     * @param reportRecordPageVO vo
     * @return Result
     */
    @GetMapping(value = "getReportRecordList")
    @ResponseBody
    public Result getReportRecordList( ReportRecordPageVO reportRecordPageVO) {
        try {
            return userInfoService.getReportRecordList(reportRecordPageVO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error();
        }

    }



    /**
     * 导出 老人信息模板
     */
    @GetMapping(value = "getUserInfoReportTemplate", headers = "Accept=application/octet-stream")
    @ResponseBody
    public void getUserInfoReportTemplate(HttpServletResponse response) {
        try {

            List<UserInfoExcel> excelList = new ArrayList<>();
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 可以防止中文乱码
            String fileName = URLEncoder.encode("老人信息报表导入模板", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + "." + ExcelTypeEnum.XLSX);
            EasyExcel.write(response.getOutputStream(), UserInfoExcel.class)
                    .sheet("老人信息报表导入模板")
                    .registerWriteHandler(new Custemhandler())
                    .registerWriteHandler(new SpinnerWriteHandler(ysLabelService))
                    .doWrite(excelList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 导入老人档案报表
     *
     * @param addOrCover 增加或者覆盖 1-增加 2-覆盖
     * @param file       报表文件
     * @return Result
     */
    @PostMapping(value = "importReport")
    @ResponseBody
    public Result importReport(Integer addOrCover, MultipartFile file) {
        try {
            if (Utils.isNull(file)) {
                return ResultUtil.errorMsg("请选择文件");
            }

            ReportRecordVO reportRecordVO = new ReportRecordVO();
            reportRecordVO.setFile(file);
            reportRecordVO.setAddOrCover(addOrCover);

            SysUser user = new SysUser();
            user.setUserNo(1235);
            user.setUserName("测试20201102");
            user.setLoginName("测试20201102");

            return userInfoService.importReport(reportRecordVO, user);
//            return userInfoService.importReport(reportRecordVO, getSysUser());
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error();
        }
    }


    /**
     * 获取原始Excel
     *
     * @param reportId 原Excel的id
     * @param response HttpServletResponse
     */
    @GetMapping(value = "getOriginalExcelUserInfo")
    @ResponseBody
    public void getOriginalExcelUserInfo(HttpServletResponse response, Integer reportId) {
        try {
            if (Utils.isNull(reportId)) {
                return;
            }
            Map<String, Object> map = userInfoService.getErrorLogInfo(reportId);
            String excelName = Utils.isNull(map.get("excelName")) ? "原始报表" : map.get("excelName").toString();
            List<UserInfoExcel> excelUserInfos = userInfoService.getOriginalExcelUserInfo(reportId);

            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 可以防止中文乱码
            String fileName = URLEncoder.encode(excelName, "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + "." + ExcelTypeEnum.XLSX);
            EasyExcel.write(response.getOutputStream(), UserInfoExcel.class)
                    .sheet(excelName)
                    .registerWriteHandler(new Custemhandler())
                    .registerWriteHandler(new SpinnerWriteHandler(ysLabelService))
                    .doWrite(excelUserInfos);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 获取错误日志报表
     *
     * @param reportId 原Excel的id
     * @param response HttpServletResponse
     */
    @GetMapping(value = "getErrorLogUserInfoExcel", headers = "Accept=application/octet-stream")
    @ResponseBody
    public void getErrorLogUserInfoExcel(Integer reportId, HttpServletResponse response) {
        try {
            if (Utils.isNull(reportId)) {
                return;
            }
            Map<String, Object> map = userInfoService.getErrorLogInfo(reportId);
            String excelName = Utils.isNull(map.get("excelName")) ? "错误日志" : map.get("excelName").toString() + "-错误日志";
            List<UserInfoExcelError> excelUserInfoErrorList = userInfoService.getExcelUserInfoError(reportId);
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 可以防止中文乱码
            String fileName = URLEncoder.encode(excelName, "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + "." + ExcelTypeEnum.XLSX);
            EasyExcel.write(response.getOutputStream(), UserInfoExcelError.class)
                    .sheet(excelName)
                    .registerWriteHandler(new Custemhandler())
                    .registerWriteHandler(new SpinnerWriteHandler(ysLabelService))
                    .doWrite(excelUserInfoErrorList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 老人档案 下载pdf
     *
     * @throws Exception Exception
     */
    @RequestMapping(value = "getPdfByUserNo")
    public void getPdfByUserNo(String userNo, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //设置字符 否则pdf不会显示中文
        String fontStyle = "templates/userInfo/simSun.ttf";
        //模板文件
        String pdfTemplate = "userInfo/userInfo_detail_for_PDF";
        //获取路径 设置成了全路径，要不然在pdf中不显示
        String baseUrl = request.getRequestURL().substring(0, StringUtils.ordinalIndexOf(request.getRequestURL(), "/", 3));
        //获取userInfo
        UserInfo userInfo = userInfoService.getUserInfoDetailByUserNo(userNo);
        //设置PDF参数
        WebContext ctx = new WebContext(request, response, request.getServletContext(), request.getLocale());
        ctx.setVariable("userInfo", userInfo);
        ctx.setVariable("baseUrl", baseUrl);
        try {
            //  为模板文件，注意路径和“/”
            String htmlContext = templateEngine.process(pdfTemplate, ctx);
            // 测试用 downloadPdf(htmlContext, "C:\\Users\\hml\\Desktop\\" + Constance.randNumber(10) + ".pdf", fontStr,response);
            downloadPdf(htmlContext, Constance.randNumber(10) + ".pdf", fontStyle, response);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void downloadPdf(String content, String pdfName, String fontStyle, HttpServletResponse response) throws DocumentException, IOException {
        // response 响应参数设置
        response.setContentType("application/pdf;charset=utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(pdfName, "UTF8"));
        response.setHeader("FileName", URLEncoder.encode(pdfName, "UTF8"));
        ServletOutputStream servletOutputStream = response.getOutputStream();

        ITextRenderer renderer = new ITextRenderer();

        ITextFontResolver fontResolver = renderer.getFontResolver();
        try {
            //设置字体，否则不支持中文,在html中使用字体，html{ font-family: SimSun;}
            fontResolver.addFont(fontStyle, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        renderer.setDocumentFromString(content);
        renderer.layout();
        //测试用 renderer.createPDF(new FileOutputStream(new File(pdfName)));
        renderer.createPDF(servletOutputStream);
        servletOutputStream.flush();
        servletOutputStream.close();
    }


}

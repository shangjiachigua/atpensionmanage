package com.caifu.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.caifu.bean.PageDo;
import com.caifu.bean.Result;
import com.caifu.bean.ResultUtil;
import com.caifu.enums.*;
import com.caifu.excel.userInfoExcel.UserInfoExcel;
import com.caifu.excel.userInfoExcel.UserInfoExcelError;
import com.caifu.mapper.*;
import com.caifu.pojo.*;
import com.caifu.util.AesMyUtil;
import com.caifu.util.CheckIdCardUtil;
import com.caifu.util.Constance;
import com.caifu.util.Utils;
import com.caifu.vo.report.ReportRecordPageVO;
import com.caifu.vo.bmfw.BmUserinfoVo;
import com.caifu.vo.report.ReportRecordVO;
import com.caifu.vo.userinfoVo.UserInfoVO;
import net.sf.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * ClassName : UserInfoService
 *
 * @author :  yb
 * @description ： 老人档案
 * @date : 2020-10-12 14:07
 */
@Service
public class UserInfoService {

    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private UserInfoContactMapper userInfoContactMapper;
    @Resource
    private YsLabelMapper ysLabelMapper;
    @Resource
    private ReportRecordMapper reportRecordMapper;
    @Resource
    private ReportRecordLogMapper reportRecordLogMapper;


    /**
     * 判断表头是否一致
     */
    private boolean isTrueHead = true;


    /**
     * 获取老人档案分页
     *
     * @param status 状态 1-正常 8-去世 9-删除
     * @return Result
     */
    public Result getUserInfoPageList(UserInfoVO vo, String status) {

        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        //状态 1-正常 8-去世 9-删除
        queryWrapper.eq("status", status);
        //列表页 查询条件给queryWrapper 赋值
        setQueryWrapperForQueryUserInfo(queryWrapper, vo);

        IPage<UserInfo> userInfoPage = userInfoMapper.selectPage(new Page<>(vo.getCurrentPage(), vo.getPageSize()), queryWrapper);
        //数据转化 页面展示
        userInfoPage.getRecords().forEach(this::transformUserInfoForPageList);

        List<UserInfo> userInfoList = userInfoPage.getRecords();
        return ResultUtil.getPageDataSuccess(new PageDo<>(vo.getCurrentPage(), vo.getPageSize(), Integer.parseInt(userInfoPage.getTotal() + ""), userInfoList));
    }


    /**
     * 查询条件 赋值
     */
    private void setQueryWrapperForQueryUserInfo(QueryWrapper<UserInfo> queryWrapper, UserInfoVO vo) {
        //户籍
        if (Utils.isNotNull(vo.getHouseholdRegister())) {
            queryWrapper.eq("household_register", vo.getHouseholdRegister());
        }
        //所在地区
        if (Utils.isNotNull(vo.getLocation())) {
            queryWrapper.eq("location", vo.getLocation());
        }
        //年龄段
        int one = 1;
        int two = 2;
        int three = 2;
        int four = 2;
        int five = 2;
        if (Utils.isNotNull(vo.getAge())) {
            //1-60岁以下  2- 60-69岁 3 - 70-79岁 4-90-99岁 5-100岁及以上
            if (one == vo.getAge()) {
                queryWrapper.lt("age", 60);
            } else if (two == vo.getAge()) {
                queryWrapper.between("age", 60, 69);
            } else if (three == vo.getAge()) {
                queryWrapper.between("age", 70, 79);
            } else if (four == vo.getAge()) {
                queryWrapper.between("age", 90, 99);
            } else if (five == vo.getAge()) {
                queryWrapper.ge("age", 100);
            }
        }
        //性别
        if (Utils.isNotNull(vo.getUserSex())) {
            queryWrapper.eq("user_sex", vo.getUserSex());
        }
        //目前养老状况
        if (Utils.isNotNull(vo.getPensionStatus())) {
            queryWrapper.eq("pension_status", vo.getPensionStatus());
        }
        //政治面貌
        if (Utils.isNotNull(vo.getPolitical())) {
            queryWrapper.in("political", Arrays.asList(vo.getPolitical().split(",")));
        }
        //社保类型
        if (Utils.isNotNull(vo.getSocialSecurityType())) {
            queryWrapper.in("social_security_type", Arrays.asList(vo.getSocialSecurityType().split(",")));
        }
        //居家状况
        if (Utils.isNotNull(vo.getLiveHomeStatus())) {
            queryWrapper.in("live_home_status", Arrays.asList(vo.getLiveHomeStatus().split(",")));
        }
        //居家状况
        if (Utils.isNotNull(vo.getLiveHomeStatus())) {
            queryWrapper.in("live_home_status", Arrays.asList(vo.getLiveHomeStatus().split(",")));
        }
        //照顾人群
        if (Utils.isNotNull(vo.getCareCrowd())) {
            queryWrapper.in("care_crowd", Arrays.asList(vo.getCareCrowd().split(",")));
        }
        //居住状况
        if (Utils.isNotNull(vo.getLiveCondition())) {
            queryWrapper.eq("live_condition", vo.getLiveCondition());
        }
        //关键字
        if (Utils.isNotNull(vo.getSearchContent())) {
            queryWrapper.like("user_name", vo.getSearchContent());
        }
        //死亡时间段
        if (Utils.isNotNull(vo.getDeathStartDate()) && Utils.isNotNull(vo.getDeathEndDate())) {
            queryWrapper.between("death_date", vo.getDeathStartDate().replace("-", ""), vo.getDeathEndDate().replace("-", ""));
        }

    }

    private void transformUserInfoForPageList(UserInfo userInfo) {
        //户籍类型
        if (Utils.isNotNull(userInfo.getHouseholdRegister())) {
            userInfo.setHouseholdRegister(getLabelNameByLabelCode(userInfo.getHouseholdRegister()));
        }
        //户籍所在地
        if (Utils.isNotNull(userInfo.getRegisterAreaOfResidence())) {
            userInfo.setRegisterAreaOfResidence(getLabelNameByLabelCode(userInfo.getRegisterAreaOfResidence()));
        }
        //管辖机构
        if (Utils.isNotNull(userInfo.getBelongOrganization())) {
            userInfo.setBelongOrganization(getLabelNameByLabelCode(userInfo.getBelongOrganization()));
        }
        //所在地区
        if (Utils.isNotNull(userInfo.getLocation())) {
            userInfo.setLocation(getLabelNameByLabelCode(userInfo.getLocation()));
        }
        //性别
        if (Utils.isNotNull(userInfo.getUserSex())) {
            userInfo.setUserSex(SexTypeEnum.getName(userInfo.getUserSex()));
        }
        //离世时间
        if (Utils.isNotNull(userInfo.getDeathDate())) {
            userInfo.setDeathDate(transformDateForLoad(userInfo.getDeathDate()));
        }
    }


    private String getLabelNameByLabelCode(String labelCode) {
        return Utils.isNull(getYsLabelByLabelCode(labelCode)) ? "" : getYsLabelByLabelCode(labelCode).getLabelName();
    }

    private YsLabel getYsLabelByLabelCode(String labelCode) {
        QueryWrapper<YsLabel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("label_code", labelCode);
        queryWrapper.eq("status", "1");
        return ysLabelMapper.selectOne(queryWrapper);
    }

    /**
     * 保存老人信息
     *
     * @param userInfo userInfo
     * @return Result
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Result saveUserInfo(UserInfo userInfo) throws Exception {

        if (Utils.isNotNull(getUserInfoByPid(userInfo.getUserPid()))) {
            return ResultUtil.errorMsg("此老人已存在");
        }

        userInfo.setUserNo(Constance.getStringRandom(20));
        //转化部分字段
        transformUserInfoForSaveOrUpdate(userInfo);

        //状态 1-正常 9-删除 8-去世
        userInfo.setStatus("1");
        userInfo.setCreateTime(new Date());
        userInfoMapper.insert(userInfo);

        //保存老人的联系人
        List<UserInfoContact> userInfoContactList = userInfo.getUserInfoContactList();
        if (Utils.isNotNull(userInfoContactList) && userInfoContactList.size() > 0) {
            saveUserInfoContact(userInfo.getUserNo(), userInfoContactList);
        }

        return ResultUtil.success();
    }

    /**
     * 通过证件号 查询userInfo
     */
    private UserInfo getUserInfoByPid(String userPid) throws Exception {
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_pid", AesMyUtil.Encrypt(userPid));
        //        queryWrapper.in("status", Arrays.asList(1, 9));
        return userInfoMapper.selectOne(queryWrapper);
    }


    /**
     * 保存userInfo 部分字段进行转化
     */
    private void transformUserInfoForSaveOrUpdate(UserInfo userInfo) throws Exception {
        if (Utils.isNull(userInfo)) {
            return;
        }
        //加密证件号 和 手机号
        if (Utils.isNotNull(userInfo.getUserPid())) {
            userInfo.setUserPid(AesMyUtil.Encrypt(userInfo.getUserPid()));
        }
        if (Utils.isNotNull(userInfo.getMobile())) {
            userInfo.setMobile(AesMyUtil.Encrypt(userInfo.getMobile()));
        }
        //出生日期
        if (Utils.isNotNull(userInfo.getBirthDate())) {
            userInfo.setBirthDate(transformDateForSaveOrUpdate(userInfo.getBirthDate()));
        }
        //入驻时间
        if (Utils.isNotNull(userInfo.getCheckInTime())) {
            userInfo.setCheckInTime(transformDateForSaveOrUpdate(userInfo.getCheckInTime()));
        }
        //离世时间
        if (Utils.isNotNull(userInfo.getDeathDate())) {
            userInfo.setDeathDate(transformDateForSaveOrUpdate(userInfo.getDeathDate()));
        }
        //退休时间
        if (Utils.isNotNull(userInfo.getRetireTime())) {
            userInfo.setRetireTime(transformDateForSaveOrUpdate(userInfo.getRetireTime()));
        }
        //如果是身份证 计算年龄  不是 出生年月计算
        if (Utils.isNotNull(userInfo.getCertificateType())) {
            if (CertificateTypeEnum.SFZ.getValue().equals(userInfo.getCertificateType())) {
                userInfo.setAge(CheckIdCardUtil.getAgeByCertId(userInfo.getUserPid()));
            } else {
                if (Utils.isNotNull(userInfo.getBirthDate())) {
                    userInfo.setAge(Constance.getAgeByBirthDate(userInfo.getBirthDate()));
                }
            }
        }
        //居住状况 1-人在户在 2-人在户不在 3-户在人不在     计算方式：户籍行政区 居住行政区   A有 B有 人在户在 A有B没有 户在人不在 A没有B有 人在户不在
        //管辖机构    计算方式：户籍行政区 居住行政区   A有 B有 选择A  A有B没有 选择A  A没有B有 选择B
        if (Utils.isNotNull(userInfo.getRegisterAreaOfResidence()) && Utils.isNotNull(userInfo.getLiveAreaOfResidence())) {
            //居住状况
            userInfo.setLiveCondition(UserInfoLiveConditionEnum.RZHZ.getKey());
            //管辖机构
            userInfo.setBelongOrganization(userInfo.getRegisterAreaOfResidence());
        } else if (Utils.isNotNull(userInfo.getRegisterAreaOfResidence()) && Utils.isNull(userInfo.getLiveAreaOfResidence())) {
            //居住状况
            userInfo.setLiveCondition(UserInfoLiveConditionEnum.HZRBZ.getKey());
            //管辖机构
            userInfo.setBelongOrganization(userInfo.getRegisterAreaOfResidence());
        } else if (Utils.isNull(userInfo.getRegisterAreaOfResidence()) && Utils.isNotNull(userInfo.getLiveAreaOfResidence())) {
            //居住状况
            userInfo.setLiveCondition(UserInfoLiveConditionEnum.RZHBZ.getKey());
            //管辖机构
            userInfo.setBelongOrganization(userInfo.getLiveAreaOfResidence());
        }
    }

    /**
     * yyyy年xx月xx日 转换成yyyyMMdd
     */
    private String transformDateForSaveOrUpdate(String date) {
        return date.replace("年", "").replace("月", "").replace("日", "");

    }


    /**
     * 修改老人信息
     *
     * @param userInfo userInfo
     * @return Result
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Result updateUserInfo(UserInfo userInfo) throws Exception {
        if (Utils.isNull(userInfo.getUserNo())) {
            return ResultUtil.errorMsg("无法查询到此档案信息");
        }
        //转化部分字段
        transformUserInfoForSaveOrUpdate(userInfo);

        //状态 1-正常 9-删除 8-去世
        userInfo.setStatus("1");
        userInfo.setUpdateTime(new Date());
        userInfoMapper.updateById(userInfo);

        //保存老人的联系人
        List<UserInfoContact> userInfoContactList = userInfo.getUserInfoContactList();
        if (Utils.isNotNull(userInfoContactList) && userInfoContactList.size() > 0) {
            saveUserInfoContact(userInfo.getUserNo(), userInfoContactList);
        }
        return ResultUtil.success();
    }


    /**
     * 保存老人的 联系人
     *
     * @param userNo              userInfo 主键
     * @param userInfoContactList 联系人集合
     */
    private void saveUserInfoContact(String userNo, List<UserInfoContact> userInfoContactList) {
        if (Utils.isNull(userInfoContactList) && userInfoContactList.size() == 0) {
            return;
        }
        //先删除
        QueryWrapper<UserInfoContact> userInfoContactQueryWrapper = new QueryWrapper<>();
        userInfoContactQueryWrapper.eq("user_no", userNo);
        userInfoContactMapper.delete(userInfoContactQueryWrapper);
        //后添加
        userInfoContactList.forEach(model -> {
            model.setCreateTime(new Date());
            model.setUserNo(userNo);
            if (Utils.isNotNull(model.getMobile())) {
                try {
                    model.setMobile(AesMyUtil.Encrypt(model.getMobile()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            userInfoContactMapper.insert(model);
        });
    }

    /**
     * 获取老人档案 信息
     *
     * @param userNo userNo
     * @return return
     */
    public UserInfo loadUserInfoByUserNo(String userNo) throws Exception {

        UserInfo userInfo = userInfoMapper.selectById(userNo);
        //展示的时候 时间转换 yyyy年MM月dd日
        transformUserInfoForLoad(userInfo);
        //获取联系人
        userInfo.setUserInfoContactList(getUserInfoContactByUserNo(userNo));
        return userInfo;
    }

    /**
     * 查看 老人 信息
     *
     * @param userNo userNo
     * @return UserInfo
     * @throws Exception e
     */
    public UserInfo getUserInfoDetailByUserNo(String userNo) throws Exception {
        UserInfo userInfo = loadUserInfoByUserNo(userNo);
        if (Utils.isNotNull(userInfo)) {
            transformUserInfoForDetail(userInfo);
        }
        return userInfo;
    }

    /**
     * 查看 老人 信息  部分字段转换
     */
    private void transformUserInfoForDetail(UserInfo userInfo) {

        //性别
        if (Utils.isNotNull(userInfo.getUserSex())) {
            userInfo.setUserSex(SexTypeEnum.getName(userInfo.getUserSex()));
        }
        //证件类型
        if (Utils.isNotNull(userInfo.getCertificateType())) {
            userInfo.setCertificateType(CertificateTypeEnum.getName(userInfo.getCertificateType()));
        }
        //户籍
        if (Utils.isNotNull(userInfo.getHouseholdRegister())) {
            userInfo.setHouseholdRegister(getLabelName(userInfo.getHouseholdRegister()));
        }
        //民族
        if (Utils.isNotNull(userInfo.getNation())) {
            userInfo.setNation(getLabelName(userInfo.getNation()));
        }
        //所在地区
        if (Utils.isNotNull(userInfo.getLocation())) {
            userInfo.setLocation(getLabelName(userInfo.getLocation()));
        }
        //婚姻状况
        if (Utils.isNotNull(userInfo.getMarriage())) {
            userInfo.setMarriage(getLabelName(userInfo.getMarriage()));
        }
        //户籍行政区
        if (Utils.isNotNull(userInfo.getRegisterAreaOfResidence())) {
            //要查询组织架构
        }
        //居住行政区
        if (Utils.isNotNull(userInfo.getLiveAreaOfResidence())) {
            //要查询组织架构
        }
        //社保类型
        if (Utils.isNotNull(userInfo.getSocialSecurityType())) {
            userInfo.setSocialSecurityType(getLabelName(userInfo.getSocialSecurityType()));
        }
        //文化程度
        if (Utils.isNotNull(userInfo.getDegreeOfEducation())) {
            userInfo.setDegreeOfEducation(getLabelName(userInfo.getDegreeOfEducation()));
        }
        //政治面貌
        if (Utils.isNotNull(userInfo.getPolitical())) {
            userInfo.setPolitical(getLabelName(userInfo.getPolitical()));
        }
        //退休工资
        if (Utils.isNotNull(userInfo.getRetireSalary())) {
            userInfo.setRetireSalary(userInfo.getRetireSalary() + "元/月");
        }
        //所属管辖机构
        if (Utils.isNotNull(userInfo.getBelongOrganization())) {
            //要查询组织架构
        }
        //居住状态 人在户在 户在人不在 人在户不在
        if (Utils.isNotNull(userInfo.getLiveCondition())) {
            userInfo.setLiveCondition(UserInfoLiveConditionEnum.getName(userInfo.getLiveCondition()));
        }
        //当前状态
        if (Utils.isNotNull(userInfo.getStatus())) {
            userInfo.setStatus(UserInfoStatusEnum.getName(userInfo.getStatus()));
        }
        //养老状况
        if (Utils.isNotNull(userInfo.getPensionStatus())) {
            userInfo.setPensionStatus(getLabelName(userInfo.getPensionStatus()));
        }
        //照顾人群
        if (Utils.isNotNull(userInfo.getCareCrowd())) {
            userInfo.setCareCrowd(getLabelName(userInfo.getCareCrowd()));
        }
        //居家情况
        if (Utils.isNotNull(userInfo.getLiveHomeStatus())) {
            userInfo.setLiveHomeStatus(getLabelName(userInfo.getLiveHomeStatus()));
        }
        //持证情况
        if (Utils.isNotNull(userInfo.getHoldCertificateStatus())) {
            userInfo.setHoldCertificateStatus(getLabelName(userInfo.getHoldCertificateStatus()));
        }
        //认知情况
        if (Utils.isNotNull(userInfo.getCognitionStatus())) {
            userInfo.setCognitionStatus(getLabelName(userInfo.getCognitionStatus()));
        }
        //涉外情况
        if (Utils.isNotNull(userInfo.getForeignIdentityStatus())) {
            userInfo.setForeignIdentityStatus(getLabelName(userInfo.getForeignIdentityStatus()));
        }
        //安全情况
        if (Utils.isNotNull(userInfo.getSafeStatus())) {
            userInfo.setSafeStatus(getLabelName(userInfo.getSafeStatus()));
        }
        //健康情况
        if (Utils.isNotNull(userInfo.getHealthStatus())) {
            userInfo.setHealthStatus(getLabelName(userInfo.getHealthStatus()));
        }
        //其他情况
        if (Utils.isNotNull(userInfo.getOtherStatus())) {
            userInfo.setOtherStatus(getLabelName(userInfo.getOtherStatus()));
        }
        //老人的联系人
        if (Utils.isNotNull(userInfo.getUserInfoContactList()) && userInfo.getUserInfoContactList().size() > 0) {
            userInfo.getUserInfoContactList().forEach(model -> model.setRelationship(getLabelName(model.getRelationship())));
        }

    }


    /**
     * 页面展示  时间 手机号 证件号 转换
     */
    private void transformUserInfoForLoad(UserInfo userInfo) throws Exception {
        //出生日期
        if (Utils.isNotNull(userInfo.getBirthDate())) {
            userInfo.setBirthDate(transformDateForLoad(userInfo.getBirthDate()));
        }
        //入驻时间
        if (Utils.isNotNull(userInfo.getCheckInTime())) {
            userInfo.setCheckInTime(transformDateForLoad(userInfo.getCheckInTime()));
        }
        //离世时间
        if (Utils.isNotNull(userInfo.getDeathDate())) {
            userInfo.setDeathDate(transformDateForLoad(userInfo.getDeathDate()));
        }
        //退休时间
        if (Utils.isNotNull(userInfo.getRetireTime())) {
            userInfo.setRetireTime(transformDateForLoad(userInfo.getRetireTime()));
        }
        //手机号
        if (Utils.isNotNull(userInfo.getMobile())) {
            userInfo.setMobile(AesMyUtil.Decrypt(userInfo.getMobile()));
        }
        //证件号
        if (Utils.isNotNull(userInfo.getUserPid())) {
            userInfo.setUserPid(AesMyUtil.Decrypt(userInfo.getUserPid()));
        }
    }

    /**
     * 给页面展示的时候用
     */
    private String transformDateForLoad(String date) {
        return date.substring(0, 4) + "年" + date.substring(4, 6) + "月" + date.substring(6, 8) + "日";
    }


    /**
     * 通过userNo 获取老人的联系人
     */
    private List<UserInfoContact> getUserInfoContactByUserNo(String userNo) {
        QueryWrapper<UserInfoContact> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_no", userNo);
        List<UserInfoContact> userInfoContacts = userInfoContactMapper.selectList(queryWrapper);
        userInfoContacts.forEach(model -> {
            if (Utils.isNotNull(model.getMobile())) {
                try {
                    model.setMobile(AesMyUtil.Decrypt(model.getMobile()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return userInfoContacts;
    }


    /**
     * 删除联系人
     *
     * @param contactNo 联系人No
     * @return Result
     */
    public Result deleteUserInfoContact(String contactNo) {
        userInfoContactMapper.deleteById(contactNo);
        return ResultUtil.success();
    }


    /**
     * 删除档案
     *
     * @param userNo userNo
     * @return Result
     */
    public Result deleteUserInfoByUserNo(String userNo) {
        UserInfo userInfo = userInfoMapper.selectById(userNo);
        if (Utils.isNull(userInfo)) {
            return ResultUtil.errorMsg("无法查询到此档案");
        }
        //删除
        userInfo.setStatus("9");
        userInfoMapper.updateById(userInfo);
        return ResultUtil.success();
    }

    /**
     * 老人 离世处理
     */
    public Result dealDeathUserInfoByUserNo(UserInfo user) {
        UserInfo userInfo = userInfoMapper.selectById(user.getUserNo());
        if (Utils.isNull(userInfo)) {
            return ResultUtil.errorMsg("无法查询到此档案");
        }
        userInfo.setDeathDate(transformDateForSaveOrUpdate(user.getDeathDate()));
        userInfo.setDeathReason(user.getDeathReason());
        //状态 1-正常 8-去世  9-删除
        userInfo.setStatus("8");
        userInfoMapper.updateById(userInfo);
        return ResultUtil.success();
    }



    /**
     * 获取Excel导入记录列表
     *
     * @return Result
     */
    public Result getReportRecordList(ReportRecordPageVO vo) {
        QueryWrapper<ReportRecord> queryWrapper = new QueryWrapper<>();
        IPage<ReportRecord> recordPage = reportRecordMapper.selectPage(new Page<>(vo.getCurrentPage(), vo.getPageSize()), queryWrapper);
        List<ReportRecord> recordList = recordPage.getRecords();
        //转化数据
        transformReportRecordList(recordList);
        return ResultUtil.getPageDataSuccess(new PageDo<>(recordPage.getCurrent(), recordPage.getSize(),Integer.parseInt( recordPage.getTotal()+""), recordList));
    }

    private void transformReportRecordList(List<ReportRecord> recordList){
        recordList.forEach(model->{
            model.setFormatCreateTime(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.ofInstant(model.getCreateTime().toInstant(), ZoneId.systemDefault())));
            model.setFormatReportTypeName("老人信息表");
            //获取成功或失败的数量
            model.setSuccessCount(getSuccessOrFailCount(model.getId(), 1));
            model.setFailCount(getSuccessOrFailCount(model.getId(), 9));
        });
    }

    /**
     * @param reportId 关联报表记录id(report_record)
     * @param state    1：成功 9：失败
     * @return count
     */
    private Integer getSuccessOrFailCount(Integer reportId, Integer state) {
        QueryWrapper<ReportRecordLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("report_record_id", reportId);
        queryWrapper.eq("state", state);
        return reportRecordLogMapper.selectCount(queryWrapper);
    }


    /**
     * 导入老人报表
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Result importReport(ReportRecordVO reportRecordVO, SysUser sysUser) throws Exception {

        if (Utils.isNull(reportRecordVO.getAddOrCover())) {
            return ResultUtil.errorMsg("导入报表缺少参数");
        } else if (Utils.isNull(reportRecordVO.getFile())) {
            return ResultUtil.errorMsg("缺少文件");
        }
        //导入类型 1-老人档案报表
        reportRecordVO.setReportType(1);
        return importReportOfUserInfo(reportRecordVO, sysUser);
    }

    /**
     * 导入老人报表 -具体实现
     *
     * @param reportRecordVO reportRecordVO
     */
    private Result importReportOfUserInfo(ReportRecordVO reportRecordVO, SysUser sysUser) throws Exception {

        //证件类型
        List<YsLabel> certificateList = getMsLabelList(LabelTypeEnum.ZJLX.getKey());
        //民族
        List<YsLabel> nationList = getMsLabelList(LabelTypeEnum.MZ.getKey());
        //婚姻状况
        List<YsLabel> marriageList = getMsLabelList(LabelTypeEnum.HYQK.getKey());
        //政治面貌
        List<YsLabel> politicalList = getMsLabelList(LabelTypeEnum.ZZMM.getKey());
        //户籍
        List<YsLabel> householdRegisterList = getMsLabelList(LabelTypeEnum.HJ.getKey());
        //所在地区
        List<YsLabel> locationList = getMsLabelList(LabelTypeEnum.SZDQ.getKey());
        //社保类型
        List<YsLabel> socialSecurityList = getMsLabelList(LabelTypeEnum.SBLX.getKey());
        //文化程度
        List<YsLabel> educationList = getMsLabelList(LabelTypeEnum.WHCD.getKey());
        //养老状况
        List<YsLabel> pensionStatusList = getMsLabelList(LabelTypeEnum.YLZK.getKey());
        //居家状况
        List<YsLabel> liveHomeStatusList = getMsLabelList(LabelTypeEnum.JJQK.getKey());
        //照顾人群
        List<YsLabel> careCrowdList = getMsLabelList(LabelTypeEnum.ZGRQ.getKey());
        //与老人关系
        List<YsLabel> relationShipList = getMsLabelList(LabelTypeEnum.YLRGX.getKey());

        //获取所有身份证判断是否重复
        List<String> userPidList = getUserPidList();

        //读取excel中的数据
        List<UserInfoExcel> excelUserInfoList = readExcel(reportRecordVO);

        if (excelUserInfoList.size() <= 0) {
            return ResultUtil.errorMsg("请不要导入空文件");
        }
        if (!isTrueHead) {
            return ResultUtil.errorMsg("表头与模板不一致,请上传正确文件");
        }

        //设置序列号
        setSort(excelUserInfoList);
        //获取 excel中的身份证
        List<String> idCards = new ArrayList<>();
        excelUserInfoList.forEach(excelUserInfo -> {
            try {
                idCards.add(AesMyUtil.Decrypt(excelUserInfo.getUserPid()));
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
        });

        //保存的userInfo
        List<UserInfo> saveMsUserInfoList = new ArrayList<>();
        //成功
        List<ReportRecordLog> saveSuccessLogList = new ArrayList<>();
        //失败
        List<ReportRecordLog> saveFailLogList = new ArrayList<>();

        //校验数据 和 数据处理
        excelUserInfoList.forEach(excelUserInfo -> {
            //数据校验
            String errorMsg = null;
            try {
                errorMsg = excelUserInfoDataCheck(excelUserInfo, userPidList, reportRecordVO.getAddOrCover());
            } catch (Exception e) {
                e.printStackTrace();
            }
            //判断身份证号是否重复
            int count = Collections.frequency(idCards, excelUserInfo.getUserPid());
            if (count > 1) {
                if (Utils.isNotNull(errorMsg)) {
                    errorMsg += (",报表中身份证号重复出现");
                } else {
                    errorMsg += ("报表中身份证号重复出现");
                }
            }

            //*********** 将数据分别放入不同的集合中 **************
            //errorMsg为空 说明数据校验通过
            if (Utils.isNull(errorMsg)) {
                ReportRecordLog successLog = getReportRecordLog(excelUserInfo, errorMsg, 1);
                saveSuccessLogList.add(successLog);
                // 如果保存userInfo    数据处理
                transformForDataExcelUserInfo(excelUserInfo, certificateList, nationList, marriageList, politicalList, householdRegisterList
                        , locationList, socialSecurityList, educationList, pensionStatusList, liveHomeStatusList, careCrowdList);
                UserInfo userInfo = getUserInfo(excelUserInfo);
                saveMsUserInfoList.add(userInfo);
            } else {
                //错误日志  state //1-成功 9-失败
                ReportRecordLog failLog = getReportRecordLog(excelUserInfo, errorMsg, 9);
                saveFailLogList.add(failLog);
            }
        });

        //***********保存userInfo , report_record_log, report_record **************
        String importResult = "成功导入" + saveSuccessLogList.size() + "数据,失败" + saveFailLogList.size() + "笔";
        //保存报表记录
        ReportRecord msReportRecord = getMsReportRecord(reportRecordVO, importResult, sysUser);
        reportRecordMapper.insert(msReportRecord);
        //放入报表的id
        saveSuccessLogList.forEach(successMsReportLog -> successMsReportLog.setReportRecordId(msReportRecord.getId()));
        saveFailLogList.forEach(failMsReportLog -> failMsReportLog.setReportRecordId(msReportRecord.getId()));

        //保存老人 和老人的联系人
        saveUserInfoOfExcel(saveMsUserInfoList, userPidList, reportRecordVO.getAddOrCover(), relationShipList);
        //保存成功和错误日志
        saveSuccessLogList.forEach(successMsReportLog -> reportRecordLogMapper.insert(successMsReportLog));
        saveFailLogList.forEach(failMsReportLog -> reportRecordLogMapper.insert(failMsReportLog));

        return ResultUtil.success(200, importResult);

    }


    //****************************************************************************************************************//


    /**
     * 读取数据
     */
    private List<UserInfoExcel> readExcel(ReportRecordVO reportRecordVO) throws Exception {
        List<UserInfoExcel> excelUserInfoList = new ArrayList<>();
        EasyExcel.read(reportRecordVO.getFile().getInputStream(), UserInfoExcel.class, new AnalysisEventListener<UserInfoExcel>() {
            @Override
            public void invoke(UserInfoExcel userInfoExcel, AnalysisContext analysisContext) {
                excelUserInfoList.add(userInfoExcel);
            }

            @Override
            public void invokeHeadMap(Map headMap, AnalysisContext context) {
                isTrueHead = isExcelHead(JSONObject.fromObject(JSON.toJSONString(headMap)));
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
            }
        }).sheet().doRead();
        return excelUserInfoList;
    }


    /**
     * 报表记录对象
     */
    private ReportRecord getMsReportRecord(ReportRecordVO reportRecordVO, String importResult, SysUser sysUser) {
        ReportRecord msReportRecord = new ReportRecord();
        msReportRecord.setReportName(reportRecordVO.getFile().getOriginalFilename());
        msReportRecord.setImportResult(importResult);
        msReportRecord.setReportType(reportRecordVO.getReportType());
        //操作人员
        if (Utils.isNotNull(sysUser.getUserNo())) {
            msReportRecord.setSysUserNo(sysUser.getUserNo());
        }
        //小区编号
        if (Utils.isNotNull(reportRecordVO.getVillageNo())) {
            msReportRecord.setVillageNo(reportRecordVO.getVillageNo());
        }
        msReportRecord.setCreateTime(new Date());
        return msReportRecord;
    }

    /**
     * 保存 Excel中的 老年居民
     *
     * @param saveMsUserInfoList saveMsUserInfoList
     * @param userPidList        身份证号List
     * @param addOrCover         增加或者覆盖 1-增加 2-覆盖
     * @param relationShipList   与老人关系
     */
    private void saveUserInfoOfExcel(List<UserInfo> saveMsUserInfoList, List<String> userPidList, Integer addOrCover, List<YsLabel> relationShipList) {
        int addNum = 1;
        int coverNum = 2;

        saveMsUserInfoList.forEach(userInfo -> {
            //增加
            if (addNum == addOrCover) {
                userInfoMapper.insert(userInfo);
            } else if (coverNum == addOrCover) {
                if (userPidList.contains(userInfo.getUserPid())) {
                    QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();
                    userInfoQueryWrapper.eq("USER_PID", userInfo.getUserPid());
                    UserInfo existUserInfo = userInfoMapper.selectOne(userInfoQueryWrapper);
                    userInfo.setUserNo(existUserInfo.getUserNo());
                    userInfoMapper.updateById(userInfo);
                } else {
                    userInfoMapper.insert(userInfo);
                }
            }

            List<UserInfoContact> userInfoContacts = userInfo.getUserInfoContactList();

            //紧急联系人
            if (Utils.isNotNull(userInfoContacts) && userInfoContacts.size() > 0) {
                //与老人关系 转化labelCode  手机号加密
                userInfoContacts.forEach(userInfoContact -> userInfoContact.setRelationship(getLabelCodes(relationShipList, userInfoContact.getRelationship())));

                //保存紧急联系人
                saveUserInfoContact(userInfo.getUserNo(), userInfo.getUserInfoContactList());
            }
        });
    }


    /**
     * 导入userInfo 报表数据校验
     *
     * @param excelUserInfo excelUserInfo
     * @param userPidList   身份号List 判断是否重复
     * @param addOrCover    增加或者覆盖 1-增加 2-覆盖
     * @return String 错误信息
     */
    private String excelUserInfoDataCheck(UserInfoExcel excelUserInfo, List<String> userPidList, Integer addOrCover) throws Exception {

        StringBuilder errorMsg = new StringBuilder();
        //姓名
        int nameMaxSize = 20;
        if (Utils.isNull(excelUserInfo.getUserName())) {
            errorMsg.append("姓名不能为空,");
        } else if (excelUserInfo.getUserName().length() > nameMaxSize) {
            errorMsg.append("姓名输入过长(长度超过20位字符),");
            //姓名过长截取 否则记录表无法保存
            excelUserInfo.setUserName(excelUserInfo.getUserName().substring(0, 15) + "...");
        }

        //验证证件类型 和 证件号
        checkCertificateTypeAndUserPid(errorMsg, excelUserInfo, userPidList, addOrCover);

        int limitLength = 100;
        //户籍地址
        if (Utils.isNotNull(excelUserInfo.getRegisterAddress())) {
            if (excelUserInfo.getRegisterAddress().length() > limitLength) {
                errorMsg.append("户籍地址过长(不能超过100位字符),");
                excelUserInfo.setRegisterAddress(excelUserInfo.getRegisterAddress().substring(0, 90) + "......");
            }
        }
        //居住地址
        if (Utils.isNotNull(excelUserInfo.getLiveAddress())) {
            if (excelUserInfo.getLiveAddress().length() > limitLength) {
                errorMsg.append("现居住地址过长(不能超过100位字符),");
                excelUserInfo.setLiveAddress(excelUserInfo.getLiveAddress().substring(0, 90) + "......");
            }
        }
        //手机号码
        int telSize = 20;
        if (Utils.isNotNull(excelUserInfo.getMobile())) {
            if (!CheckIdCardUtil.isChinaPhoneLegal(excelUserInfo.getMobile())) {
                errorMsg.append("手机号码输入有误,");
            }
            if (excelUserInfo.getMobile().length() > telSize) {
                excelUserInfo.setMobile(excelUserInfo.getMobile().substring(0, 15) + "...");
            }
            //手机号加密
            excelUserInfo.setMobile(AesMyUtil.Encrypt(excelUserInfo.getMobile()));
        }
        //固定电话
        if (Utils.isNotNull(excelUserInfo.getTel())) {
            if (excelUserInfo.getTel().length() > telSize) {
                errorMsg.append("固定电话输入过长,");
                excelUserInfo.setTel(excelUserInfo.getTel().substring(0, 15) + "...");
            }
        }
        //职业
        if (Utils.isNotNull(excelUserInfo.getOccupation())) {
            if (excelUserInfo.getOccupation().length() > limitLength) {
                errorMsg.append("职业输入过长(不能超过100位字符),");
                excelUserInfo.setOccupation(excelUserInfo.getOccupation().substring(0, 90) + "......");
            }
        }
        //退休单位
        if (Utils.isNotNull(excelUserInfo.getRetireCompany())) {
            if (excelUserInfo.getRetireCompany().length() > limitLength) {
                errorMsg.append("退休单位输入过长(不能超过100位字符),");
                excelUserInfo.setRetireCompany(excelUserInfo.getRetireCompany().substring(0, 90) + "......");
            }
        }
        //健康描述
        int healthLimitLength = 100;
        if (Utils.isNotNull(excelUserInfo.getHealthDescription())) {
            if (excelUserInfo.getHealthDescription().length() > healthLimitLength) {
                errorMsg.append("健康描述输入过长(不能超过300位字符),");
                excelUserInfo.setHealthDescription(excelUserInfo.getHealthDescription().substring(0, 290) + "......");
            }
        }

        //紧急联系人
        List<UserInfoContact> userInfoContacts = getEmergencyContact(excelUserInfo);
        excelUserInfo.setUserInfoContactList(userInfoContacts);

        return Utils.isNull(errorMsg.toString()) ? "" : errorMsg.toString().substring(0, errorMsg.toString().length() - 1);
    }


    /**
     * 从Excel中 获取紧急联系人
     *
     * @param excelUserInfo excelUserInfo
     */
    private List<UserInfoContact> getEmergencyContact(UserInfoExcel excelUserInfo) {
        //第一联系人
        UserInfoContact oneContact = getUserInfoContactOfExcel(excelUserInfo.getOneContractName(), excelUserInfo.getOneRelationship(), excelUserInfo.getOneTel(), excelUserInfo.getOneAddress(), excelUserInfo.getOneMobile());
        //第二联系人
        UserInfoContact twoContact = getUserInfoContactOfExcel(excelUserInfo.getTwoContractName(), excelUserInfo.getTwoRelationship(), excelUserInfo.getTwoTel(), excelUserInfo.getTwoAddress(), excelUserInfo.getTwoMobile());
        //第三联系人
        UserInfoContact threeContact = getUserInfoContactOfExcel(excelUserInfo.getThreeContractName(), excelUserInfo.getThreeRelationship(), excelUserInfo.getThreeTel(), excelUserInfo.getThreeAddress(), excelUserInfo.getThreeMobile());
        //第四联系人
        UserInfoContact fourContact = getUserInfoContactOfExcel(excelUserInfo.getFourContractName(), excelUserInfo.getFourRelationship(), excelUserInfo.getFourTel(), excelUserInfo.getFourAddress(), excelUserInfo.getFourMobile());

        List<UserInfoContact> userInfoContacts = new ArrayList<>();
        if (Utils.isNotNull(oneContact)) {
            userInfoContacts.add(oneContact);
        }
        if (Utils.isNotNull(twoContact)) {
            userInfoContacts.add(twoContact);
        }
        if (Utils.isNotNull(threeContact)) {
            userInfoContacts.add(threeContact);
        }
        if (Utils.isNotNull(fourContact)) {
            userInfoContacts.add(fourContact);
        }
        return userInfoContacts;
    }

    /**
     * @param contactName  联系人姓名
     * @param relationShip 联系人与老人关系
     * @param tel          固定电话
     * @param address      详细地址
     * @param mobile       手机电话
     */
    private UserInfoContact getUserInfoContactOfExcel(String contactName, String relationShip, String tel, String address, String mobile) {
        if (Utils.isNotNull(contactName) && Utils.isNotNull(relationShip)) {
            UserInfoContact userInfoContact = new UserInfoContact();
            userInfoContact.setContactName(contactName);
            userInfoContact.setRelationship(relationShip);
            userInfoContact.setTel(tel);
            userInfoContact.setAddress(address);
            userInfoContact.setMobile(mobile);
            userInfoContact.setCreateTime(new Date());
            return userInfoContact;
        } else {
            return null;
        }
    }


    /**
     * 验证证件类型 和证件号
     */
    private void checkCertificateTypeAndUserPid(StringBuilder errorMsg, UserInfoExcel excelUserInfo
            , List<String> userPidList, Integer addOrCover) throws Exception {

        //证件类型
        if (Utils.isNull(excelUserInfo.getCertificateType())) {
            errorMsg.append("证件类型不能为空,");
        } else if (CertificateTypeEnum.SFZ.getValue().equals(excelUserInfo.getCertificateType()) && Utils.isNotNull(excelUserInfo.getUserPid())) {
            //如果是身份证进行身份证验证
            if (!CheckIdCardUtil.check(excelUserInfo.getUserPid())) {
                errorMsg.append("身份证号输入有误,");
            } else {
                //  如果是身份证正确 获取生日 和性别
                excelUserInfo.setBirthDate(CheckIdCardUtil.getBirthDayPart(excelUserInfo.getUserPid()));
                excelUserInfo.setUserSex(CheckIdCardUtil.getSexByIdentityNumber(excelUserInfo.getUserPid()));
            }
            //增加的时候判断身份证号是否重复
            if (addOrCover == 1) {
                if (userPidList.contains(excelUserInfo.getUserPid())) {
                    errorMsg.append("身份证号已存在,");
                }
            }
        } else if (Utils.isNull(CertificateTypeEnum.getEnumKey(excelUserInfo.getCertificateType()))) {
            errorMsg.append("证件类型匹配不上,");
        } else if (!CertificateTypeEnum.SFZ.getValue().equals(excelUserInfo.getCertificateType())) {
            //如果证件类型!=身份证 出生日期不能为空  性别不能为空
            //出生日期
            int birthSize = 10;
            if (Utils.isNull(excelUserInfo.getBirthDate())) {
                errorMsg.append("证件类型不是身份证 出生日期不能为空,");
            } else if (excelUserInfo.getBirthDate().length() != birthSize) {
                errorMsg.append("出生日期格式有误,");
                excelUserInfo.setBirthDate(excelUserInfo.getBirthDate().substring(0, excelUserInfo.getBirthDate().length() - 5) + "...");
            }
            //性别
            if (Utils.isNotNull(excelUserInfo.getUserSex())) {
                errorMsg.append("证件类型不是身份证 性别不能为空,");
            }
        }

        //证件号
        int pidMax = 20;
        if (Utils.isNull(excelUserInfo.getUserPid())) {
            errorMsg.append("证件号不能为空,");
        } else if (excelUserInfo.getUserPid().length() > pidMax) {
            errorMsg.append("证件号过长,");
            //证件号
            excelUserInfo.setUserPid(AesMyUtil.Encrypt(excelUserInfo.getUserPid().substring(0, 19) + "..."));
        } else {
            excelUserInfo.setUserPid(AesMyUtil.Encrypt(excelUserInfo.getUserPid()));
        }


    }

    /**
     * 数据处理
     *
     * @param excelUserInfo         excelUserInfo
     * @param certificateList       证件类型
     * @param nationList            民族
     * @param marriageList          婚姻状况
     * @param politicalList         政治面貌
     * @param householdRegisterList 户籍
     * @param locationList          所在地区
     * @param socialSecurityList    社保类型
     * @param educationList         文化程度
     * @param pensionStatusList     养老状况
     * @param liveHomeStatusList    居家状况
     * @param careCrowdList         照顾人群
     */
    private void transformForDataExcelUserInfo(UserInfoExcel excelUserInfo, List<YsLabel> certificateList, List<YsLabel> nationList
            , List<YsLabel> marriageList, List<YsLabel> politicalList, List<YsLabel> householdRegisterList, List<YsLabel> locationList
            , List<YsLabel> socialSecurityList, List<YsLabel> educationList, List<YsLabel> pensionStatusList, List<YsLabel> liveHomeStatusList
            , List<YsLabel> careCrowdList) {

        //证件类型
        if (Utils.isNotNull(excelUserInfo.getCertificateType())) {
            String certificateType = getLabelCodes(certificateList, excelUserInfo.getCertificateType());
            excelUserInfo.setCertificateType(Utils.isNull(certificateType) ? "" : certificateType);
        }
        //民族
        if (Utils.isNotNull(excelUserInfo.getNation())) {
            String getLabelCodes = getLabelCodes(nationList, excelUserInfo.getNation());
            excelUserInfo.setNation(Utils.isNull(getLabelCodes) ? "" : getLabelCodes);
        }
        //婚姻状况
        if (Utils.isNotNull(excelUserInfo.getMarriage())) {
            String marriageType = getLabelCodes(marriageList, excelUserInfo.getMarriage());
            excelUserInfo.setMarriage(Utils.isNull(marriageType) ? "" : marriageType);
        }
        //政治面貌
        if (Utils.isNotNull(excelUserInfo.getPolitical())) {
            String politicalType = getLabelCodes(politicalList, excelUserInfo.getPolitical());
            excelUserInfo.setPolitical(Utils.isNull(politicalType) ? "" : politicalType);
        }
        //户籍
        if (Utils.isNotNull(excelUserInfo.getHouseholdRegister())) {
            String householdRegisterType = getLabelCodes(householdRegisterList, excelUserInfo.getHouseholdRegister());
            excelUserInfo.setHouseholdRegister(Utils.isNull(householdRegisterType) ? "" : householdRegisterType);
        }
        //所在地区
        if (Utils.isNotNull(excelUserInfo.getLocation())) {
            String locationType = getLabelCodes(locationList, excelUserInfo.getLocation());
            excelUserInfo.setLocation(Utils.isNull(locationType) ? "" : locationType);
        }
        //社保类型
        if (Utils.isNotNull(excelUserInfo.getSocialSecurityType())) {
            String socialSecurityType = getLabelCodes(socialSecurityList, excelUserInfo.getSocialSecurityType());
            excelUserInfo.setSocialSecurityType(Utils.isNull(socialSecurityType) ? "" : socialSecurityType);
        }
        //文化程度
        if (Utils.isNotNull(excelUserInfo.getDegreeOfEducation())) {
            String degreeOfEducationType = getLabelCodes(educationList, excelUserInfo.getDegreeOfEducation());
            excelUserInfo.setDegreeOfEducation(Utils.isNull(degreeOfEducationType) ? "" : degreeOfEducationType);
        }
        //养老状况
        if (Utils.isNotNull(excelUserInfo.getPensionStatus())) {
            String pensionStatusType = getLabelCodes(pensionStatusList, excelUserInfo.getPensionStatus());
            excelUserInfo.setPensionStatus(Utils.isNull(pensionStatusType) ? "" : pensionStatusType);
        }
        //居家状况
        if (Utils.isNotNull(excelUserInfo.getLiveHomeStatus())) {
            String liveHomeStatusType = getLabelCodes(liveHomeStatusList, excelUserInfo.getLiveHomeStatus());
            excelUserInfo.setLiveHomeStatus(Utils.isNull(liveHomeStatusType) ? "" : liveHomeStatusType);
        }
        //照顾人群
        if (Utils.isNotNull(excelUserInfo.getCareCrowd())) {
            String careCrowdType = getLabelCodes(careCrowdList, excelUserInfo.getCareCrowd());
            excelUserInfo.setLiveHomeStatus(Utils.isNull(careCrowdType) ? "" : careCrowdType);
        }
        //性别 1-男 2女
        if (Utils.isNotNull(excelUserInfo.getUserSex())) {
            String userSex = SexTypeEnum.getEnumKey(excelUserInfo.getUserSex());
            excelUserInfo.setUserSex(Utils.isNull(userSex) ? "" : userSex);
        }
    }


    /**
     * @param excelUserInfo excelUserInfo
     * @param error         error
     * @param state         state
     * @return MsReportRecordLog
     */
    private ReportRecordLog getReportRecordLog(UserInfoExcel excelUserInfo, String error, int state) {
        ReportRecordLog recordLog = new ReportRecordLog();
        recordLog.setState(state);
        recordLog.setErrorMessage(error);
        recordLog.setCreateTime(new Date());
        BeanUtils.copyProperties(excelUserInfo, recordLog);
        return recordLog;
    }

    /**
     * 报表数据 转成 userInfo 对象
     *
     * @param excelUserInfo excelUserInfo
     * @return UserInfo
     */
    private UserInfo getUserInfo(UserInfoExcel excelUserInfo) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserNo(Constance.getStringRandom(18));
        userInfo.setCreateTime(new Date());
        userInfo.setStatus("1");
        BeanUtils.copyProperties(excelUserInfo, userInfo);
        return userInfo;
    }

    /**
     * 获取居民身份证信息
     *
     * @return List
     */
    private List<String> getUserPidList() {
        QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();
        List<UserInfo> userInfos = userInfoMapper.selectList(userInfoQueryWrapper);
        List<String> userPidList = new ArrayList<>();
        userInfos.forEach(userInfo -> {
            try {
                userPidList.add(AesMyUtil.Decrypt(userInfo.getUserPid()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return userPidList;
    }

    /**
     * @param parentCode parentCode
     * @return YsLabelList
     */
    private List<YsLabel> getMsLabelList(String parentCode) {
        QueryWrapper<YsLabel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("PARENT_CODE", parentCode);
        queryWrapper.eq("STATUS", "1");
        return ysLabelMapper.selectList(queryWrapper);
    }

    /**
     * 判断表头是否正确
     *
     * @param jsonObject 获取的表头
     */
    private Boolean isExcelHead(JSONObject jsonObject) {
        //表头 -为了验证表头是否一致
        String[] excelUserHead = new String[]{"*姓名", "*证件类型(根据下拉框选择)", "*证件号", "出生日期(例:1988-08-22)", "性别(根据下拉框选择)"
                , "民族(根据下拉框选择)", "户籍地址", "居住地址", "婚姻状况(根据下拉框选择)"
                , "政治面貌(根据下拉框选择)", "*户籍(根据下拉框选择)", "*所在地区(根据下拉框选择)"
                , "手机号码", "固定电话", "社保类型(根据下拉框选择)", "退休工资(单位: 元/月)", "退休单位", "退休时间(例:1985-05-01)"
                , "职业", "文化程度(根据下拉框选择)", "社保卡号", "*养老状况(根据下拉框选择)", "居家情况(根据下拉框选择)"
                , "照顾人群(根据下拉框选择)", "养老机构", "入驻时间(例:1985-05-01)", "健康描述(不超过300字)"
                , "第一联系人姓名", "第一联系人与老人关系(根据下拉框选择)", "第一联系人固定电话", "第一联系人详细地址", "第一联系人手机电话"
                , "第二联系人姓名", "第二联系人与老人关系(根据下拉框选择)", "第二联系人固定电话", "第二联系人详细地址", "第二联系人手机电话"
                , "第三联系人姓名", "第三联系人与老人关系(根据下拉框选择)", "第三联系人固定电话", "第三联系人详细地址", "第三联系人手机电话"
                , "第四联系人姓名", "第四联系人与老人关系(根据下拉框选择)", "第四联系人固定电话", "第四联系人详细地址", "第四联系人手机电话"};

        for (int i = 0; i < excelUserHead.length; i++) {
            if (!excelUserHead[i].equals(jsonObject.get(i + ""))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 排序
     */
    private void setSort(List<UserInfoExcel> excelUserInfoList) {
        int sort = 1;
        for (UserInfoExcel excelUserInfo : excelUserInfoList) {
            excelUserInfo.setSort(sort);
            sort++;
        }
    }

    /**
     * 标签类型 name 转成code
     *
     * @param labelList  labelList
     * @param labelNames labelNames
     * @return String
     */
    private String getLabelCodes(List<YsLabel> labelList, String labelNames) {
        String[] arr = labelNames.split(",");
        String labelCodes = "";
        for (String s : arr) {
            if (Utils.isNotNull(s)) {
                for (YsLabel label : labelList) {
                    if (label.getLabelName().equals(s)) {
                        if (Utils.isNotNull(labelCodes)) {
                            labelCodes += "," + label.getLabelCode();
                        } else {
                            labelCodes += label.getLabelCode();
                        }
                    }
                }
            }
        }
        return labelCodes;
    }


    /**
     * 获取 导出报表需要的数据
     *
     * @param reportId 报表 id
     * @return Map
     */
    public Map<String, Object> getErrorLogInfo(Integer reportId) {
        Map<String, Object> result = new HashMap<>(5);
        ReportRecord msReportRecord = reportRecordMapper.selectById(reportId);
        //导入类型 1-老人档案报表
        result.put("type", msReportRecord.getReportType());
        result.put("excelName", msReportRecord.getReportName());
        return result;
    }


    /**
     * 获取原始报表数据  居民信息
     *
     * @param reportId reportId 报表 id
     * @return List
     */
    public List<UserInfoExcel> getOriginalExcelUserInfo(Integer reportId) {
        List<UserInfoExcel> excelUserInfos = new ArrayList<>();
        List<ReportRecordLog> logList = getReportRecordLogList(reportId, null);
        logList.forEach(log -> {
            UserInfoExcel excelUserInfo = new UserInfoExcel();
            //解密手机号 和证件号
            aesMyUtilDecrypt(log);
            BeanUtils.copyProperties(log, excelUserInfo);
            excelUserInfos.add(excelUserInfo);
        });
        return excelUserInfos;
    }

    /**
     * 获取 居民 报表错误日志
     *
     * @param reportId 报表 id
     * @return List
     */
    public List<UserInfoExcelError> getExcelUserInfoError(Integer reportId) {
        List<UserInfoExcelError> excelUserInfoErrorList = new ArrayList<>();
        List<ReportRecordLog> logList = getReportRecordLogList(reportId, 9);
        logList.forEach(errorLog -> {
            UserInfoExcelError excelUserInfoError = new UserInfoExcelError();
            //解密手机号 和证件号
            aesMyUtilDecrypt(errorLog);
            BeanUtils.copyProperties(errorLog, excelUserInfoError);
            excelUserInfoErrorList.add(excelUserInfoError);
        });
        return excelUserInfoErrorList;
    }

    /**
     * 解密手机号 和证件号
     *
     * @param log ReportRecordLog
     */
    private void aesMyUtilDecrypt(ReportRecordLog log) {
        try {
            if (Utils.isNotNull(log.getMobile())) {
                log.setMobile(AesMyUtil.Decrypt(log.getMobile()));
            }
            if (Utils.isNotNull(log.getUserPid())) {
                log.setUserPid(AesMyUtil.Decrypt(log.getUserPid()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * 获取日志集合
     *
     * @param reportId 报表 id
     * @return List
     */
    private List<ReportRecordLog> getReportRecordLogList(Integer reportId, Integer state) {
        QueryWrapper<ReportRecordLog> reportRecordLogQueryWrapper = new QueryWrapper<>();
        reportRecordLogQueryWrapper.eq("report_record_id", reportId);
        if (Utils.isNotNull(state)) {
            reportRecordLogQueryWrapper.eq("state", state);
        }
        reportRecordLogQueryWrapper.orderByAsc("sort");
        return reportRecordLogMapper.selectList(reportRecordLogQueryWrapper);
    }


    private String getLabelName(String labelCode) {
        String labelName = "";
        QueryWrapper<YsLabel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", "1");
        queryWrapper.in("label_code", Arrays.asList(labelCode.split(",")));
        List<YsLabel> ysLabels = ysLabelMapper.selectList(queryWrapper);
        for (YsLabel ysLabel : ysLabels) {
            if (Utils.isNotNull(labelName)) {
                labelName += "，" + ysLabel.getLabelName();
            } else {
                labelName = ysLabel.getLabelName();
            }
        }
        return labelName;
    }


    /**
     * 获取老人档案分页 便民服务
     *
     * @param status 状态 1-正常 8-去世 9-删除
     * @return Result
     */
    public Result getBmUserInfoPageList(BmUserinfoVo vo, String status) {

        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        //状态 1-正常 8-去世 9-删除
        queryWrapper.eq("status", status);
        //行政区域
        //TODO
        if (Utils.isNotNull(vo.getBelongOrganization())) {
            queryWrapper.eq("belong_organization", vo.getBelongOrganization());
        }
        //性别
        if (Utils.isNotNull(vo.getUserSex())) {
            queryWrapper.eq("user_sex", vo.getUserSex());
        }
        //姓名
        if (Utils.isNotNull(vo.getUserName())) {
            queryWrapper.like("user_name", vo.getUserName());
        }
        //年龄
        if (Utils.isNotNull(vo.getAge())) {
            queryWrapper.eq("age", vo.getAge());
        }
        queryWrapper.orderByDesc("created_time");
        IPage<UserInfo> userInfoPage = userInfoMapper.selectPage(new Page<>(Long.parseLong(vo.getCurrentPage()), Long.parseLong(vo.getPageSize())), queryWrapper);
        //数据转化 页面展示
        userInfoPage.getRecords().forEach(this::transformUserInfoForPageList);

        List<UserInfo> userInfoList = userInfoPage.getRecords();
        return ResultUtil.getPageDataSuccess(new PageDo<>(Long.parseLong(vo.getCurrentPage()), Long.parseLong(vo.getPageSize()), Integer.parseInt(userInfoPage.getTotal() + ""), userInfoList));
    }

}

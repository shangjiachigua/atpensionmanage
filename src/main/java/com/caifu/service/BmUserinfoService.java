package com.caifu.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.caifu.bean.PageDo;
import com.caifu.bean.Result;
import com.caifu.bean.ResultUtil;
import com.caifu.mapper.BmCouponsRecordMapper;
import com.caifu.mapper.BmUserinfoMapper;
import com.caifu.mapper.YsLabelMapper;
import com.caifu.pojo.BmCouponsRecord;
import com.caifu.pojo.BmUserinfo;
import com.caifu.pojo.YsLabel;
import com.caifu.util.AesMyUtil;
import com.caifu.util.Utils;
import com.caifu.vo.bmfw.BmUserinfoVo;
import com.caifu.vo.userinfoVo.BmCouponsRecordForUserDetailVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author tianwenwen
 * ClassName: BmUserinfoService.java
 * version: 1.0.0
 * createTime: 2020年10月16日 10:59:58
 **/
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class BmUserinfoService {

    @Resource
    private BmUserinfoMapper bmUserinfoMapper;

    @Resource
    private BmCouponsRecordMapper bmCouponsRecordMapper;

    @Resource
    private YsLabelMapper ysLabelMapper;


    /**
     * description: 添加老人优惠信息
     * param type ids
     * return: void
     * author: Tian
     * time: 2020/10/16 15:52
     */
    public Result addBmUserinfo(String type, String ids, String coupons) {
        if (Utils.isNotNull(ids)) {
            String[] arrId = ids.split(",");
            for (String id : arrId) {
                //添加之前判断老人优惠信息是否存在
                if (bmUserinfoIsExist(id, type)) {
                    continue;
                }
                BmUserinfo bmUserinfo = new BmUserinfo();
                bmUserinfo.setTotalCoupons(Integer.parseInt(coupons));
                bmUserinfo.setUnusedCoupons(Integer.parseInt(coupons));
                bmUserinfo.setUsedCoupons(0);
                bmUserinfo.setType(type);
                bmUserinfo.setCreatedTime(new Date());
                bmUserinfo.setUpdatedTime(new Date());
                bmUserinfo.setUserinfoId(id);
                bmUserinfo.setStatus("1");
                bmUserinfoMapper.insert(bmUserinfo);

                //添加优惠券记录
                BmCouponsRecord bmCouponsRecord = new BmCouponsRecord();
                bmCouponsRecord.setNumber(coupons);
                //TODO
                bmCouponsRecord.setSourceUse("阿三");
                bmCouponsRecord.setBmUserinfoId(bmUserinfo.getUserinfoId());
                bmCouponsRecord.setCreatedTime(new Date());
                bmCouponsRecord.setUpdatedTime(new Date());
                bmCouponsRecordMapper.insert(bmCouponsRecord);

            }
        }
        return ResultUtil.success(200, "添加成功");
    }

    /**
     * 统计老人是否已经有优惠信息
     */
    public boolean bmUserinfoIsExist(String id, String type) {
        boolean flag = false;
        QueryWrapper<BmUserinfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userinfo_id", id).eq("type", type);
        int count = bmUserinfoMapper.selectCount(queryWrapper);
        if (count > 0) {
            flag = true;
        }
        return flag;
    }

    /**
     * description: 获取老人优惠信息列表
     * param param currentPage pageSize
     * return: void
     * author: Tian
     * time: 2020/10/16 16:59
     */
    public PageInfo<BmUserinfoVo> getBmUserinfoList(BmUserinfoVo bmUserinfoVo) {
        PageHelper.startPage(Integer.parseInt(bmUserinfoVo.getCurrentPage()), Integer.parseInt(bmUserinfoVo.getPageSize()));
        Map<String, Object> params = new HashMap<>(15);
        params.put("param", bmUserinfoVo.getParam());
        params.put("orgId", bmUserinfoVo.getOrgId());
        params.put("type", bmUserinfoVo.getType());
        List<BmUserinfoVo> bmUserinfoList = bmUserinfoMapper.getBmUserinfoList(params);
        //1男
        String sex = "1";
        if (Utils.isNotNull(bmUserinfoList) && bmUserinfoList.size() > 0) {
            for (BmUserinfoVo userinfoVo : bmUserinfoList) {
                if (sex.equals(userinfoVo.getUserSex())) {
                    userinfoVo.setUserSex("男");
                } else {
                    userinfoVo.setUserSex("女");
                }
            }
        }
        return new PageInfo<>(bmUserinfoList);
    }

    /**
     * description: 批量发券
     * param type ids coupons
     * return: void
     * author: Tian
     * time: 2020/10/16 17:44
     */
    public void batchCoupons(String type, String ids, String coupons) {
        if (Utils.isNotNull(ids)) {
            String[] idArr = ids.split(",");
            for (String id : idArr) {
                UpdateWrapper<BmUserinfo> updateWrapper = new UpdateWrapper<>();
                QueryWrapper<BmUserinfo> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("userinfo_id", id).eq("type", type).eq("status", 1);
                BmUserinfo bmUserinfo = bmUserinfoMapper.selectOne(queryWrapper);
                if (Utils.isNotNull(bmUserinfo)) {
                    int total = bmUserinfo.getTotalCoupons() + Integer.parseInt(coupons);
                    bmUserinfo.setTotalCoupons(total);
                    int unUseds = bmUserinfo.getUnusedCoupons() + Integer.parseInt(coupons);
                    bmUserinfo.setUnusedCoupons(unUseds);

                    updateWrapper.eq("userinfo_id", id).eq("type", type);
                    bmUserinfoMapper.update(bmUserinfo, updateWrapper);
                }


                //添加发券记录
                BmCouponsRecord bmCouponsRecord = new BmCouponsRecord();
                bmCouponsRecord.setSourceUse("阿三");
                bmCouponsRecord.setNumber(coupons);
                bmCouponsRecord.setUpdatedTime(new Date());
                bmCouponsRecord.setCreatedTime(new Date());
                bmCouponsRecord.setBmUserinfoId(id);
                bmCouponsRecord.setHairCut("1");
                bmCouponsRecord.setType(type);
                bmCouponsRecordMapper.insert(bmCouponsRecord);
            }
        }
    }

    /**
     * description: 批量移除
     * param type ids
     * return: void
     * author: Tian
     * time: 2020/10/16 17:44
     */
    public void batchRemove(String type, String ids) {
        if (Utils.isNotNull(ids)) {
            String[] idArr = ids.split(",");
            for (String id : idArr) {
                UpdateWrapper<BmUserinfo> updateWrapper = new UpdateWrapper<>();
                QueryWrapper<BmUserinfo> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("userinfo_id", id).eq("type", type);
                BmUserinfo bmUserinfo = bmUserinfoMapper.selectOne(queryWrapper);
                if (Utils.isNotNull(bmUserinfo)) {
                    bmUserinfo.setStatus("9");
                    updateWrapper.eq("userinfo_id", id).eq("type", type);
                    bmUserinfoMapper.update(bmUserinfo, updateWrapper);
                }
            }
        }
    }

    /**
     * description: 根据老人id获取老人详细信息
     * param id
     * param type
     * return: com.caifu.pojo.BmUserinfo
     * author: Tian
     * time: 2020/10/27 17:45
     */
    public BmUserinfoVo getBmUserInfoByid(String id, String type) throws Exception {
        Map<String, Object> map = new HashMap<>(10);
        map.put("id", id);
        map.put("type", type);
        BmUserinfoVo bmUserinfoVo = bmUserinfoMapper.selectUserinfoByIdMap(map);
        bmUserinfoVo.setUserPid(AesMyUtil.Decrypt(bmUserinfoVo.getUserPid()));
        bmUserinfoVo.setMobile(AesMyUtil.Decrypt(bmUserinfoVo.getMobile()));
        //证件类型
        if (YsLabelIsNull(bmUserinfoVo.getCertificateType())) {
            bmUserinfoVo.setCertificateType(getYsLabelByLabelCode(bmUserinfoVo.getCertificateType()).getLabelName());
        }
        //民族
        if (YsLabelIsNull(bmUserinfoVo.getNation())) {
            bmUserinfoVo.setNation(getYsLabelByLabelCode(bmUserinfoVo.getNation()).getLabelName());
        }
        //政治面貌
        if (YsLabelIsNull(bmUserinfoVo.getPolitical())) {
            bmUserinfoVo.setPolitical(getYsLabelByLabelCode(bmUserinfoVo.getPolitical()).getLabelName());
        }
        //婚姻状况
        if (YsLabelIsNull(bmUserinfoVo.getMarriage())) {
            bmUserinfoVo.setMarriage(getYsLabelByLabelCode(bmUserinfoVo.getMarriage()).getLabelName());
        }
        //社保类型
        if (YsLabelIsNull(bmUserinfoVo.getSocialSecurityType())) {
            bmUserinfoVo.setSocialSecurityType(getYsLabelByLabelCode(bmUserinfoVo.getSocialSecurityType()).getLabelName());
        }
        //所在地区
        //TODO
        //文化程度
        if (YsLabelIsNull(bmUserinfoVo.getDegreeOfEducation())) {
            bmUserinfoVo.setDegreeOfEducation(getYsLabelByLabelCode(bmUserinfoVo.getDegreeOfEducation()).getLabelName());
        }
        return bmUserinfoVo;
    }

    /**
     * 获取老人优惠券 基本信息
     *
     * @param userInfoId userInfoId
     * @param type       优惠券类型（1.理发2.缝纫）
     * @return BmUserinfo
     */
    public BmUserinfo getBmUserInfoByUserInfoId(String userInfoId, Integer type) {
        QueryWrapper<BmUserinfo> bmUserInfoQueryWrapper = new QueryWrapper<>();
        bmUserInfoQueryWrapper.eq("userinfo_id", userInfoId);
        bmUserInfoQueryWrapper.eq("type", type);
        bmUserInfoQueryWrapper.eq("status", "1");
        return bmUserinfoMapper.selectOne(bmUserInfoQueryWrapper);
    }

    /**
     * 获取老人优惠券 消费信息
     *
     * @param vo 查询条件
     * @return Result
     */
    public Result getBmCouponsRecordListOfUserInfoId(BmCouponsRecordForUserDetailVO vo) {
        //通过userNo 查询bm_userinfo
        QueryWrapper<BmUserinfo> bmUserInfoQueryWrapper = new QueryWrapper<>();
        bmUserInfoQueryWrapper.eq("userinfo_id", vo.getUserNo());
        bmUserInfoQueryWrapper.eq("status", "1");
        List<BmUserinfo> bmUserInfoList = bmUserinfoMapper.selectList(bmUserInfoQueryWrapper);
        //bm_userinfo    中获取到id 再去查询bm_coupons_record
        List<String> ids = bmUserInfoList.stream().map(model -> model.getId() + "").collect(Collectors.toList());

        //查询bm_coupons_record
        QueryWrapper<BmCouponsRecord> bmCouponsRecordQueryWrapper = new QueryWrapper<>();
        if (Utils.isNotNull(vo.getStartTime()) && Utils.isNotNull(vo.getEndTime())) {
            bmCouponsRecordQueryWrapper.between("created_time", vo.getStartTime() + " 00:00:00", vo.getEndTime() + " 23:59:59");
        }
        if (Utils.isNotNull(vo.getType())) {
            bmCouponsRecordQueryWrapper.eq("type", vo.getType());
        }
        bmCouponsRecordQueryWrapper.in("bm_userinfo_id", ids);
        bmCouponsRecordQueryWrapper.orderByDesc("created_time");
        IPage<BmCouponsRecord> iPage = bmCouponsRecordMapper.selectPage(new Page<>(vo.getCurrentPage(), vo.getPageSize()), bmCouponsRecordQueryWrapper);
        List<BmCouponsRecord> resultList = iPage.getRecords();
        //1.理发2.缝纫
        String fairType = "1";
        String sewType = "2";
        for (BmCouponsRecord record : resultList) {
            if (fairType.equals(record.getType())) {
                record.setType("理发券");
            } else if (sewType.equals(record.getType())) {
                record.setType("缝纫券");
            }
        }
        return ResultUtil.getPageDataSuccess(new PageDo<>(iPage.getCurrent(), iPage.getSize(), Integer.parseInt(iPage.getTotal() + ""), resultList));
    }


    private boolean YsLabelIsNull(String labelCode) {
        if (Utils.isNotNull(getYsLabelByLabelCode(labelCode))) {
            return true;
        }
        return false;
    }

    /**
     * description: 根据id获取yslabel
     * param labelCode
     * return: com.caifu.pojo.YsLabel
     * author: Tian
     * time: 2020/11/2 14:59
     */
    private YsLabel getYsLabelByLabelCode(String labelCode) {
        QueryWrapper<YsLabel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("label_code", labelCode);
        queryWrapper.eq("status", "1");
        return ysLabelMapper.selectOne(queryWrapper);
    }
}

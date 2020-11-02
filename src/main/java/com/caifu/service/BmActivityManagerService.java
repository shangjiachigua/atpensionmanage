package com.caifu.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.caifu.bean.Result;
import com.caifu.bean.ResultUtil;
import com.caifu.mapper.*;
import com.caifu.pojo.*;
import com.caifu.util.AesMyUtil;
import com.caifu.util.TokenProccessor;
import com.caifu.util.Utils;
import com.caifu.vo.bmfw.UserinfoVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author tianwenwen
 * ClassName: BmActivityManagerService.java
 * version: 1.0.0
 * createTime: 2020年10月16日 10:59:15
 **/
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class BmActivityManagerService {

    @Resource
    private BmActivityManagerMapper bmActivityManagerMapper;

    @Resource
    private BmTokenMapper bmTokenMapper;

    @Resource
    private BmUserinfoMapper bmUserinfoMapper;

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private BmCouponsRecordMapper bmCouponsRecordMapper;

    /**
     * description: 获取活动室列表
     * param param
     * return: java.util.List<com.caifu.pojo.BmActivityManager>
     * author: Tian
     * time: 2020/10/16 11:47
     */
    public IPage<BmActivityManager> getActivityList(String param, String currentPage, String pageSize) throws Exception {
        QueryWrapper<BmActivityManager> queryWrapper = new QueryWrapper<>();
        if (Utils.isNotNull(param)) {
            queryWrapper.like("name", param).or().like("contacts", param);
        }
        queryWrapper.eq("status", 1).orderByDesc("created_time");
        Page<BmActivityManager> page = new Page<>(Long.parseLong(currentPage), Long.parseLong(pageSize));
        IPage<BmActivityManager> bmActivityManagerIPage = bmActivityManagerMapper.selectPage(page, queryWrapper);
        List<BmActivityManager> records = bmActivityManagerIPage.getRecords();
        String haircut = "1";
        String sewing = "2";
        for (BmActivityManager record : records) {
            record.setPhone(AesMyUtil.Decrypt(record.getPhone()));
            //活动室类型
            String type = record.getType();
            String[] types = type.split(",");
            if (types.length > 1) {
                record.setType("理发室、缝纫室");
            } else if (haircut.equals(type)) {
                record.setType("理发室");
            } else if (sewing.equals(type)) {
                record.setType("缝纫室");
            }
        }
        return bmActivityManagerIPage;
    }

    /**
     * description: 添加活动室
     * param activityManager
     * return: void
     * author: Tian
     * time: 2020/10/16 13:05
     */
    public Result addActivity(BmActivityManager activityManager) throws Exception {
        if (Utils.isNull(activityManager.getId())) {
            //校验手机号是否存在
            if (checkPhone(activityManager.getPhone())) {
                return ResultUtil.error(5050, "手机号已存在");
            }

            //添加
            activityManager.setCreatedTime(new Date());
            activityManager.setUpdatedTime(new Date());
            activityManager.setPhone(AesMyUtil.Encrypt(activityManager.getPhone()));
            activityManager.setStatus("1");
            bmActivityManagerMapper.insert(activityManager);

            //添加token
            BmToken bmToken = new BmToken();
            bmToken.setUpdatedTime(new Date());
            bmToken.setCreatedTime(new Date());
            bmToken.setActivityManagerId(activityManager.getId());
            bmToken.setToken(TokenProccessor.getInstance().makeToken());
            bmTokenMapper.insert(bmToken);
            return ResultUtil.success("200", "保存成功");
        } else {
            //修改
            BmActivityManager bmActivityManager = bmActivityManagerMapper.selectById(activityManager.getId());
            //校验手机号是否存在
            if (!activityManager.getPhone().equals(bmActivityManager.getPhone()) && checkPhone(activityManager.getPhone())) {
                return ResultUtil.error(3333, "手机号已存在");
            }
            bmActivityManager.setUpdatedTime(new Date());
            bmActivityManager.setAddress(activityManager.getAddress());
            bmActivityManager.setContacts(activityManager.getContacts());
            bmActivityManager.setName(activityManager.getName());
            bmActivityManager.setOrgId(activityManager.getOrgId());
            bmActivityManager.setPhone(activityManager.getPhone());
            bmActivityManager.setType(activityManager.getType());
            bmActivityManagerMapper.updateById(bmActivityManager);
            return ResultUtil.success("200", "保存成功");
        }
    }

    /**
     * description: 根据id获取活动室
     * param id
     * return: com.caifu.pojo.BmActivityManager
     * author: Tian
     * time: 2020/10/16 13:14
     */
    public BmActivityManager getActivityById(String id) {
        return bmActivityManagerMapper.selectById(id);
    }


    /**
     * description: 根据id删除活动室
     * param id
     * return: void
     * author: Tian
     * time: 2020/10/16 13:19
     */
    public void deleteActivityById(String id) {
        BmActivityManager bmActivityManager = bmActivityManagerMapper.selectById(id);
        bmActivityManager.setStatus("9");
        bmActivityManager.setUpdatedTime(new Date());
        bmActivityManagerMapper.updateById(bmActivityManager);
    }

    /**
     * description: 校验手机号是否存在
     * param phone
     * return: boolean
     * author: Tian
     * time: 2020/10/21 14:37
     */
    private boolean checkPhone(String phone) throws Exception {
        boolean flag = false;
        QueryWrapper<BmActivityManager> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", AesMyUtil.Encrypt(phone));
        int count = bmActivityManagerMapper.selectCount(queryWrapper);
        if (count > 0) {
            flag = true;
        }
        return flag;
    }

    /**
     * description: 终端机登录
     * param phone request
     * return: java.util.Map<java.lang.String,java.lang.Object>
     * author: Tian
     * time: 2020/10/21 14:37
     */
    public Map<String, Object> bmLogin(String phone, HttpServletRequest request) throws Exception {
        Map<String, Object> map = new HashMap<>(15);
        QueryWrapper<BmActivityManager> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", AesMyUtil.Encrypt(phone)).eq("status", 1);
        BmActivityManager bmActivityManager = bmActivityManagerMapper.selectOne(queryWrapper);
        if (Utils.isNull(bmActivityManager)) {
            map.put("code", "404");
            map.put("msg", "管理员不存在");
            return map;
        }
        String type = bmActivityManager.getType();
        String[] types = type.split(",");
        String lifa = "1";
        String fengren = "2";
        ArrayList<Map<String, Object>> maps = new ArrayList<>();
        Map<String, Object> map1;
        for (String s : types) {
            map1 = new HashMap<>(15);
            if (lifa.equals(s)) {
                map1.put("name", "理发馆");
            } else if (fengren.equals(s)) {
                map1.put("name", "缝纫馆");
            }
            maps.add(map1);
        }
        //查询token
        QueryWrapper<BmToken> bmTokenQueryWrapper = new QueryWrapper<>();
        bmTokenQueryWrapper.eq("activity_manager_id", bmActivityManager.getId());
        BmToken bmToken = bmTokenMapper.selectOne(bmTokenQueryWrapper);
        //修改tokenId
        bmToken.setToken(TokenProccessor.getInstance().makeToken());
        bmTokenMapper.updateById(bmToken);
        map.put("tokenId", bmToken.getToken());
        map.put("code", 200);
        map.put("msg", "请求成功");
        map.put("data", bmActivityManager);
        map.put("types", maps);
        HttpSession session = request.getSession();
        session.setAttribute("phone", phone);
        return map;
    }

    /**
     * description: 根据身份证获取老人信息
     * param userPid tokenId request
     * return: com.caifu.bean.Result
     * author: Tian
     * time: 2020/10/21 14:38
     */
    public Result getUserinfoByUserPid(String type, String userPid, String tokenId, String phone) {
        if (checkToken(tokenId, phone)) {
            return ResultUtil.error(500, "用户登录失效");
        }
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_pid", userPid).eq("status", 1);
        UserInfo userInfo = userInfoMapper.selectOne(queryWrapper);
        UserinfoVo userinfoVo = new UserinfoVo();
        if (Utils.isNull(userInfo)) {
            return ResultUtil.success(404, "您不是安亭镇老人", "");
        } else {
            userinfoVo.setAge(userInfo.getAge());
            userinfoVo.setImgPath(userInfo.getImgPath());
            userinfoVo.setUserName(userInfo.getUserName());
            userinfoVo.setUserSex(userInfo.getUserSex());
            //查询老人优惠券
            QueryWrapper<BmUserinfo> userinfoQueryWrapper = new QueryWrapper<>();
            userinfoQueryWrapper.eq("userinfo_id", userInfo.getUserNo()).eq("type", type);
            BmUserinfo bmUserinfo = bmUserinfoMapper.selectOne(userinfoQueryWrapper);
            if (Utils.isNotNull(bmUserinfo)) {
                userinfoVo.setUnusedCoupons(bmUserinfo.getUnusedCoupons());
            } else {
                userinfoVo.setUnusedCoupons(0);
            }
        }
        return ResultUtil.success(200, "suceess", userinfoVo);
    }

    /**
     * description: 核销使用券
     * param type userPid tokenId request
     * return: com.caifu.bean.Result
     * author: Tian
     * time: 2020/10/21 14:38
     */
    public Result pinCoupon(String type, String userPid, String tokenId, String phone) {
        if (checkToken(tokenId, phone)) {
            return ResultUtil.error(500, "用户登录失效");
        }
        //根据身份证查询老人信息
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_pid", userPid).eq("status", 1);
        UserInfo userInfo = userInfoMapper.selectOne(queryWrapper);
        //查询老人优惠信息表老人
        if (Utils.isNotNull(userInfo)) {
            QueryWrapper<BmUserinfo> bmUserinfoQueryWrapper = new QueryWrapper<>();
            bmUserinfoQueryWrapper.eq("userinfo_id", userInfo.getUserNo()).eq("type", type).eq("status", 1);
            BmUserinfo bmUserinfo = bmUserinfoMapper.selectOne(bmUserinfoQueryWrapper);
            //将老人券减去一
            int unUsecoups = bmUserinfo.getUnusedCoupons() - 1;
            bmUserinfo.setUnusedCoupons(unUsecoups);
            bmUserinfo.setUpdatedTime(new Date());
            bmUserinfoMapper.updateById(bmUserinfo);

            //添加老人优惠券使用记录
            BmCouponsRecord bmCouponsRecord = new BmCouponsRecord();
            bmCouponsRecord.setCreatedTime(new Date());
            bmCouponsRecord.setUpdatedTime(new Date());
            bmCouponsRecord.setNumber("-1");
            bmCouponsRecord.setHairCut("2");
            //获取消费优惠券地点
            QueryWrapper<BmActivityManager> bmActivityManagerQueryWrapper = new QueryWrapper<>();
            bmActivityManagerQueryWrapper.eq("phone", phone).eq("status", 1);
            BmActivityManager bmActivityManager = bmActivityManagerMapper.selectOne(bmActivityManagerQueryWrapper);
            bmCouponsRecord.setSourceUse(bmActivityManager.getName());
            bmCouponsRecord.setBmUserinfoId(bmUserinfo.getId());
            bmCouponsRecordMapper.insert(bmCouponsRecord);
        }
        return ResultUtil.success(200, "操作成功", "");
    }


    /**
     * 验证token
     */
    private boolean checkToken(String tokenId, String phone) {
        boolean flag = false;
        //验证tokenId是否有效
        QueryWrapper<BmActivityManager> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", phone).eq("status", 1);
        BmActivityManager bmActivityManager = bmActivityManagerMapper.selectOne(queryWrapper);
        //查询token
        if (Utils.isNotNull(bmActivityManager)) {
            QueryWrapper<BmToken> bmTokenQueryWrapper = new QueryWrapper<>();
            bmTokenQueryWrapper.eq("activity_manager_id", bmActivityManager.getId());
            BmToken bmToken = bmTokenMapper.selectOne(bmTokenQueryWrapper);
            if (!tokenId.equals(bmToken.getToken())) {
                flag = true;
            }
        }
        return flag;
    }
}

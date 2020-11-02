package com.caifu.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.caifu.mapper.BmCouponsRecordMapper;
import com.caifu.pojo.BmCouponsRecord;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author tianwenwen
 * ClassName: BmCouponsRecordService.java
 * version: 1.0.0
 * createTime: 2020年10月16日 10:59:38
 **/
@Service
public class BmCouponsRecordService {

    @Resource
    private BmCouponsRecordMapper bmCouponsRecordMapper;

    /**
     * description: 根据老人id获取老人优惠券记录 type： 1.理发 2.缝纫
     * param id type
     * return: com.baomidou.mybatisplus.core.metadata.IPage<com.caifu.pojo.BmCouponsRecord>
     * author: Tian
     * time: 2020/10/28 10:11
     */
    public IPage<BmCouponsRecord> getRecordById(String id, String type, String currentPage, String pageSize) {
        QueryWrapper<BmCouponsRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("bm_userinfo_id", id).eq("type", type).orderByDesc("created_time");
        Page<BmCouponsRecord> page = new Page<>(Long.parseLong(currentPage), Long.parseLong(pageSize));
        return bmCouponsRecordMapper.selectPage(page, queryWrapper);
    }
}

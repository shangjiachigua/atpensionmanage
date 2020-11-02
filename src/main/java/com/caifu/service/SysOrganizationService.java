package com.caifu.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.caifu.mapper.SysOrganizationMapper;
import com.caifu.pojo.SysOrganization;
import com.caifu.util.Utils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author tww
 * ClassName: SysOrganizationService.java、
 * createTime: 2020年10月20日 10:48:57
 **/
@Service
public class SysOrganizationService {

    @Resource
    private SysOrganizationMapper sysOrganizationMapper;

    /**
     * description: 添加组织
     * param sysOrganization
     * return: void
     * author: Tian
     * time: 2020/10/23 16:36
     */
    public void addSysOrganization(SysOrganization sysOrganization) {
        if (Utils.isNull(sysOrganization.getId())) {
            //新增
            sysOrganization.setCreatedTime(new Date());
            sysOrganization.setUpdatedTime(new Date());
            sysOrganization.setStatus("1");
            sysOrganizationMapper.insert(sysOrganization);
        } else {
            //编辑
        }
    }

    /**
     * description: 获取所有组织架构
     * param
     * return: java.util.List<com.caifu.pojo.SysOrganization>
     * author: Tian
     * time: 2020/10/23 16:37
     */
    public List<SysOrganization> getSysOrganizationList() {
        QueryWrapper<SysOrganization> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", 1).orderByAsc("id");
        List<SysOrganization> sysOrganizations = sysOrganizationMapper.selectList(queryWrapper);
        //return getFormatOrganizationList(sysOrganizations, 0);
        return getFormatOrganizationList(sysOrganizations, 6);
    }

    /**
     * description: 格式化组织
     * param sysOrganizations pid
     * return: java.util.List<com.caifu.pojo.SysOrganization>
     * author: Tian
     * time: 2020/10/23 16:47
     */
    private List<SysOrganization> getFormatOrganizationList(List<SysOrganization> sysOrganizations, int pid) {
        ArrayList<SysOrganization> organizations = new ArrayList<>();
        for (SysOrganization sysOrganization : sysOrganizations) {
            //判断当前节点是否有子节点 如果节点父id为0一定有子节点
            if (sysOrganization.getPid() == pid) {
                //查询当前节点下的子节点
                getChildOrganizationList(sysOrganizations, sysOrganization);
                organizations.add(sysOrganization);
            }
        }
        return organizations;
    }

    /**
     * description: 递归调用，查询当前节点下的子节点
     * param sysOrganizations organization
     * return: void
     * author: Tian
     * time: 2020/10/23 16:53
     */
    private void getChildOrganizationList(List<SysOrganization> sysOrganizations, SysOrganization organization) {
        List<SysOrganization> childOrganizationListByParentId = getChildOrganizationListByParentId(sysOrganizations, organization);
        //设置当前节点子节点
        organization.setSysOrganizations(childOrganizationListByParentId);
        //判断当前子节点是否有子节点
        for (SysOrganization sysOrganization : childOrganizationListByParentId) {
            if (hasChild(sysOrganizations, sysOrganization)) {
                getChildOrganizationList(sysOrganizations, sysOrganization);
            }
        }
    }

    /**
     * description: 根据父节点id获取子节点
     * return: java.util.List<com.caifu.pojo.SysOrganization>
     * author: Tian
     * time: 2020/10/23 16:58
     */
    private List<SysOrganization> getChildOrganizationListByParentId(List<SysOrganization> sysOrganizations, SysOrganization organization) {
        ArrayList<SysOrganization> list = new ArrayList<>();
        for (SysOrganization sysOrganization : sysOrganizations) {
            //如果当前节点父id等于上级节点id
            if (sysOrganization.getPid() == organization.getId()) {
                list.add(sysOrganization);
            }
        }
        return list;
    }

    /**
     * description: 判断当前节点下是否有子节点
     * return: boolean
     * author: Tian
     * time: 2020/10/23 17:04
     */
    private boolean hasChild(List<SysOrganization> sysOrganizations, SysOrganization sysOrganization) {
        return getChildOrganizationListByParentId(sysOrganizations, sysOrganization).size() > 0 ? true : false;
    }
}

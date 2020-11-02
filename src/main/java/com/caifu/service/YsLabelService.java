package com.caifu.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.caifu.bean.PageDo;
import com.caifu.bean.Result;
import com.caifu.bean.ResultUtil;
import com.caifu.mapper.YsLabelMapper;
import com.caifu.pojo.YsLabel;
import com.caifu.util.Constance;
import com.caifu.util.Utils;
import com.caifu.vo.label.SaveMsLabelVO;
import com.caifu.vo.label.UpdateMsLabelVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName : YsLabelService
 *
 * @author :  yb
 * @description ：
 * @date : 2020-10-12 14:08
 */
@Service
public class YsLabelService {

    @Resource
    private YsLabelMapper ysLabelMapper;

    /**
     * 获取 养老的 所有标签
     *
     * @return Result
     */
    public Result getPensionLabelType() {
        QueryWrapper<YsLabel> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNull("PARENT_CODE").or().eq("PARENT_CODE", "");
        queryWrapper.eq("is_pension", "1");
        queryWrapper.eq("STATUS", "1");
        List<YsLabel> msLabelList = ysLabelMapper.selectList(queryWrapper);
        return ResultUtil.getDataSuccess(msLabelList);
    }

    /**
     * 保存标签
     *
     * @param saveMsLabelVO vo
     * @return Result
     */
    public Result saveLabel(SaveMsLabelVO saveMsLabelVO) {

        if (Utils.isNull(saveMsLabelVO.getParentCode())) {
            return ResultUtil.errorMsg("标签类型不能空");
        }
        int labelNameSize = 20;
        if (Utils.isNull(saveMsLabelVO.getLabelName())) {
            return ResultUtil.errorMsg("标签名称不能为空");
        } else if (saveMsLabelVO.getLabelName().length() > labelNameSize) {
            return ResultUtil.errorMsg("标签名称不能超过20个字符");
        }

        //是否存在相同类型下名称相同的标签
        QueryWrapper<YsLabel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("PARENT_CODE", saveMsLabelVO.getParentCode());
        queryWrapper.eq("LABEL_NAME", saveMsLabelVO.getLabelName());
        YsLabel isExistLabel = ysLabelMapper.selectOne(queryWrapper);
        if (Utils.isNotNull(isExistLabel)) {
            return ResultUtil.errorMsg("已有相同类型的标签名称");
        }
        //是否存在此种类型的标签 labelCode判断
        YsLabel isExistLabelType = ysLabelMapper.selectById(saveMsLabelVO.getParentCode());
        if (Utils.isNull(isExistLabelType)) {
            return ResultUtil.errorMsg("不存在此标签类型");
        }
        //保存标签
        YsLabel ysLabel = new YsLabel();
        BeanUtils.copyProperties(saveMsLabelVO, ysLabel);
        String newLabelCode = ysLabelMapper.getMaxLabelCode(saveMsLabelVO.getParentCode());
        if (Utils.isNull(newLabelCode)) {
            newLabelCode = saveMsLabelVO.getParentCode() + "01";
        } else {
            newLabelCode = String.valueOf(new Integer(newLabelCode) + 1);
        }
        ysLabel.setLabelCode(newLabelCode);
        ysLabel.setCreateTime(Constance.currentDateTime());
        ysLabel.setStatus(1);
        ysLabelMapper.insert(ysLabel);
        return ResultUtil.success();
    }

    /**
     * 修改标签
     *
     * @param updateMsLabelVO vo
     * @return Result
     */
    public Result updateLabel(UpdateMsLabelVO updateMsLabelVO) {

        if (Utils.isNull(updateMsLabelVO.getLabelCode())) {
            return ResultUtil.errorMsg("标签Code 不能为空");
        }
        int labelNameSize = 20;
        if (Utils.isNull(updateMsLabelVO.getLabelName())) {
            return ResultUtil.errorMsg("标签名称不能为空");
        } else if (updateMsLabelVO.getLabelName().length() > labelNameSize) {
            return ResultUtil.errorMsg("标签名称不能超过20个字符");
        }

        YsLabel ysLabel = ysLabelMapper.selectById(updateMsLabelVO.getLabelCode());
        if (Utils.isNull(ysLabel)) {
            return ResultUtil.errorMsg("无法查询到此条记录");
        }

        ysLabel.setLabelName(updateMsLabelVO.getLabelName());
        ysLabelMapper.updateById(ysLabel);
        return ResultUtil.success();
    }

    /**
     * 删除标签
     *
     * @param labelCode 标签Code
     * @return Result
     */
    public Result deleteLabel(String labelCode) {

        if (Utils.isNull(labelCode)) {
            return ResultUtil.errorMsg("标签Code 不能为空");
        }
        YsLabel ysLabel = ysLabelMapper.selectById(labelCode);
        if (Utils.isNull(ysLabel)) {
            return ResultUtil.errorMsg("无法查询到此条记录");
        }
        ysLabelMapper.deleteById(ysLabel);
        return ResultUtil.success();
    }



    /**
     * 获取标签列表 分页
     * @return Result
     */
    public Result getLabelListByLabelCodePagination(String labelCode, Integer currentPage, Integer pageSize) {
        if (Utils.isNull(labelCode)) {
            return ResultUtil.error();
        }
        IPage<YsLabel> page = new Page<>(currentPage,pageSize);
        page.descs();
        QueryWrapper<YsLabel> msLabelQueryWrapper = new QueryWrapper<>();
        if (Utils.isNotNull(labelCode)) {
            msLabelQueryWrapper.eq("PARENT_CODE", labelCode);
            msLabelQueryWrapper.eq("STATUS", "1");
        }
        ysLabelMapper.selectPage(page, msLabelQueryWrapper);

        return ResultUtil.getPageDataSuccess(new PageDo<>(Long.parseLong(currentPage+""), Long.parseLong(pageSize+""), Integer.parseInt(page.getTotal() + ""), page.getRecords()));
    }




    /**
     * @param parentCode parentCode
     * @return MsLabel  Array
     */
    public String[] getLabelNameArray(String parentCode) {
        List<YsLabel> labelList = getYsLabelList(parentCode);
        List<String> labelNameList = new ArrayList<>();
        labelList.forEach(model -> labelNameList.add(model.getLabelName()));
        return labelNameList.toArray(new String[0]);
    }

    /**
     * @param parentCode parentCode
     * @return MsLabelList
     */
    public List<YsLabel> getYsLabelList(String parentCode) {
        QueryWrapper<YsLabel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("PARENT_CODE", parentCode);
        queryWrapper.eq("STATUS", "1");
        return ysLabelMapper.selectList(queryWrapper);
    }

}

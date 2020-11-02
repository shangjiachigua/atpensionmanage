package com.caifu.mapper;

import com.caifu.pojo.YsLabel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 标签表 Mapper 接口
 * </p>
 *
 * @author yb
 * @since 2020-10-12
 */
public interface YsLabelMapper extends BaseMapper<YsLabel> {


    /**
     * 获取标签类型 最新的labelCode
     * @param parentCode parentCode
     * @return labelCode
     */
    String getMaxLabelCode(String parentCode);

}

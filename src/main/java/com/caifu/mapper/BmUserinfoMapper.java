package com.caifu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.caifu.pojo.BmUserinfo;
import com.caifu.vo.bmfw.BmUserinfoVo;
import com.caifu.vo.bmfw.UserinfoVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 优惠老人信息表 Mapper 接口
 * </p>
 *
 * @author tww
 * @since 2020-10-16
 */
public interface BmUserinfoMapper extends BaseMapper<BmUserinfo> {

    List<BmUserinfoVo> getBmUserinfoList(Map<String, Object> map);

    UserinfoVo getUserinfoByUserPid(Map<String, Object> map);

    BmUserinfoVo selectUserinfoByIdMap(Map<String, Object> map);
}

package com.hscs.mapper;

import com.hscs.entity.UserCenter;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author Painter
 * @since 2021-03-04
 */
@Mapper
public interface UserCenterMapper extends BaseMapper<UserCenter> {

    Integer countRegisterDay(String day);
}

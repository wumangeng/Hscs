package com.hscs.mapper;

import com.hscs.entity.EduCourseCollect;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hscs.entity.vo.CourseCollectVo;

import java.util.List;

/**
 * <p>
 * 课程收藏 Mapper 接口
 * </p>
 *
 * @author Painter
 * @since 2021-03-09
 */
public interface EduCourseCollectMapper extends BaseMapper<EduCourseCollect> {
    /**获取课程收藏列表*/
    List<CourseCollectVo> selectPageByMemberId(String memberId);
}

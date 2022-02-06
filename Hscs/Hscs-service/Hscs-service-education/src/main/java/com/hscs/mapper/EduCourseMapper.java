package com.hscs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hscs.entity.EduCourse;
import com.hscs.entity.frontvo.CourseWebVo;
import com.hscs.entity.vo.CoursePublishVO;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author painter
 * @since 2021-01-03
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    /**
     *   根据课程id查询课程确认信息
     */
    CoursePublishVO getCoursePublishInfo(String id);

    CourseWebVo getBaseCourseInfo(String courseId);
}

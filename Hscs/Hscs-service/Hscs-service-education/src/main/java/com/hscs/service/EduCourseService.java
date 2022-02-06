package com.hscs.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hscs.entity.EduCourse;
import com.hscs.entity.frontvo.CourseFrontVo;
import com.hscs.entity.frontvo.CourseWebVo;
import com.hscs.entity.vo.CourseInfoVO;
import com.hscs.entity.vo.CoursePublishVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-01-03
 */
public interface EduCourseService extends IService<EduCourse> {

    /**
     * 添加课程订单 返回主键id
     * */
    String saveCourseInfo(CourseInfoVO courseInfo);

    CourseInfoVO getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVO courseInfoVO);

    CoursePublishVO getPublishCourseInfo(String id);

    void removeCourse(String courseId);

    Map<String, Object> getCourseFrontList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo);

    CourseWebVo getBaseCourseInfo(String courseId);

    List<EduCourse> getHotCourse();
}

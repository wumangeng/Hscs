package com.hscs.service;

import com.hscs.entity.EduCourseCollect;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hscs.entity.vo.CourseCollectVo;

import java.util.List;

/**
 * <p>
 * 课程收藏 服务类
 * </p>
 *
 * @author Painter
 * @since 2021-03-09
 */
public interface EduCourseCollectService extends IService<EduCourseCollect> {
    /**判断是否收藏*/
    boolean isCollect(String courseId, String memberId);

    /**收藏课程  添加一条收藏的记录*/
    void saveCourseCollect(String courseId, String memberId);

    //获取课程收藏列表
    List<CourseCollectVo> selectListByMemberId(String memberId);

    /**.取消收藏  删除一条收藏的记录*/
    boolean removeCourseCollect(String courseId, String memberId);
}

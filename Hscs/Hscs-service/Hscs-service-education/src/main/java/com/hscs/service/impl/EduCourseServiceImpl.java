package com.hscs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hscs.entity.EduCourse;
import com.hscs.entity.EduCourseDescription;
import com.hscs.entity.frontvo.CourseFrontVo;
import com.hscs.entity.frontvo.CourseWebVo;
import com.hscs.entity.vo.CourseInfoVO;
import com.hscs.entity.vo.CoursePublishVO;
import com.hscs.mapper.EduCourseMapper;
import com.hscs.service.EduChapterService;
import com.hscs.service.EduCourseDescriptionService;
import com.hscs.service.EduCourseService;
import com.hscs.service.EduVideoService;
import com.hscs.servicebase.exceptionhandler.HscsException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-01-03
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    /**章节描述*/
    @Autowired
    private EduCourseDescriptionService courseDescriptionService;
    /**章节*/
    @Autowired
    private EduChapterService chapterService;
    /**小节*/
    @Autowired
    private EduVideoService videoService;

    /**
     * 添加课程订单 返回主键id
     * */
    @Override
    public String saveCourseInfo(CourseInfoVO courseInfo) {
        //System.out.println("courseInfo->"+courseInfo.toString());
        // 1、往课程表添加课程基本信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfo, eduCourse);
        int insert = baseMapper.insert(eduCourse);

        if (insert<=0){
            new HscsException(20001,"添加课程失败！");
        }
        //获取课程添加后的主键id ，用于设置课程简介表id 即两张表id相同 一对一的关系
        String cid = eduCourse.getId();
        // 2、往课程简介表添加课程简介
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfo.getDescription());
        eduCourseDescription.setId(cid);
        courseDescriptionService.save(eduCourseDescription);
        return cid;
    }

    @Override
    public CourseInfoVO getCourseInfo(String courseId) {
        CourseInfoVO courseInfoVO = new CourseInfoVO();
        //查询课程表
        EduCourse eduCourse = baseMapper.selectById(courseId);
        BeanUtils.copyProperties(eduCourse,courseInfoVO);
        //查询简介表
        EduCourseDescription courseDescription = courseDescriptionService.getById(courseId);
        courseInfoVO.setDescription(courseDescription.getDescription());
        return courseInfoVO;
    }

    @Override
    public void updateCourseInfo(CourseInfoVO courseInfoVO) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVO,eduCourse);
        baseMapper.updateById(eduCourse);

        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVO,eduCourseDescription);
        courseDescriptionService.updateById(eduCourseDescription);
    }

    @Override
    public CoursePublishVO getPublishCourseInfo(String id) {
        return baseMapper.getCoursePublishInfo(id);
    }

    /**
     * 删除课程相关的信息
     * */
    @Override
    public void removeCourse(String courseId) {
        //1、先删除课程小节和视频
        videoService.removeVideoByCourseId(courseId);
        //2、删除章节
        chapterService.removeChapterByCourseId(courseId);
        //3、删除课程描述
        courseDescriptionService.removeById(courseId);
        //4、删除课程
        baseMapper.deleteById(courseId);
    }

    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo) {
        //2 根据讲师id查询所讲课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        //判断条件值是否为空，不为空拼接
        //一级分类
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())) {
            wrapper.eq("subject_parent_id",courseFrontVo.getSubjectParentId());
        }
        //二级分类
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectId())) {
            wrapper.eq("subject_id",courseFrontVo.getSubjectId());
        }
        //关注度
        if(!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())) {
            wrapper.orderByDesc("buy_count");
        }
        //最新
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) {
            wrapper.orderByDesc("gmt_create");
        }
        //价格
        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())) {
            wrapper.orderByDesc("price");
        }

        baseMapper.selectPage(pageCourse,wrapper);

        List<EduCourse> records = pageCourse.getRecords();
        long current = pageCourse.getCurrent();
        long pages = pageCourse.getPages();
        long size = pageCourse.getSize();
        long total = pageCourse.getTotal();
        //下一页
        boolean hasNext = pageCourse.hasNext();
        //上一页
        boolean hasPrevious = pageCourse.hasPrevious();

        //把分页数据获取出来，放到map集合
        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        //map返回
        return map;
    }

    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }

    @Cacheable(value = "eduCourseList",key = "'IndexeduCourseList'")
    @Override
    public List<EduCourse> getHotCourse() {
        //查询前8条热门课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 8");
        List<EduCourse> eduCourseList = baseMapper.selectList(wrapper);
        return eduCourseList;
    }
}

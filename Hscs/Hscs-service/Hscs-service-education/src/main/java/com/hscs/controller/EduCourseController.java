package com.hscs.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hscs.entity.EduCourse;
import com.hscs.entity.HscsTeacher;
import com.hscs.entity.vo.CourseConditionVO;
import com.hscs.entity.vo.CourseInfoVO;
import com.hscs.entity.vo.CoursePublishVO;
import com.hscs.entity.vo.TeacherSearchVO;
import com.hscs.service.EduCourseService;
import com.hscs.commonutils.R;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author painter
 * @since 2021-01-03
 */

@RestController
@RequestMapping("/edu/course")
public class EduCourseController {
    @Autowired
    private EduCourseService courseService;

    /**
     * 分页获取课程列表
     * */
    @GetMapping("list/{current}/{limit}")
    public R getCourseInfoList(@PathVariable Integer current,@PathVariable Integer limit){
        Page<EduCourse> page = new Page<>(current, limit);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("gmt_create");
        //调用方法实现分页：调用方法时底层会把分页所有数据封装到Page对象里
        courseService.page(page,wrapper);
        long total = page.getTotal();
        //获得数据
        List<EduCourse> list = page.getRecords();
        Map map = new HashMap<>();
        map.put("list",list);
        map.put("total",total);
        return R.ok().data(map);
    }
    /**
     * 条件分页查询
     * */
    @PostMapping("pageCourseCondition/{current}/{limit}")
    public R pageCourseCondition(@RequestBody(required = false) CourseConditionVO conditionVO,
                                       @PathVariable Integer current, @PathVariable Integer limit){
        Page<EduCourse> page = new Page<>(current, limit);
        //构建条件
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        //多条件组合查询  动态sql
        String title = conditionVO.getTitle();
        String status = conditionVO.getStatus();
        String gmtCreate = conditionVO.getGmtCreate();

        if (!StringUtils.isEmpty(title)){
            //第一个字段为数据库列名， 第二个为传入值
            wrapper.like("title",title);
        }
        if(!StringUtils.isEmpty(String.valueOf(status))){
            wrapper.eq("status",status);
        }
        if (!StringUtils.isEmpty(gmtCreate)){
            wrapper.like("gmt_create",gmtCreate);
        }
        //设置根据时间降序排列
        wrapper.orderByDesc("gmt_create");
        courseService.page(page, wrapper);
        long total = page.getTotal();
        List<EduCourse> courseList = page.getRecords();
        return R.ok().data("total",total).data("courseList",courseList);
    }

    /**
     * 添加课程
     * */
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVO courseInfo){
        String id = courseService.saveCourseInfo(courseInfo);
        return R.ok().data("courseId",id);
    }

    /**
     * 删除课程及相关内容
     * */
    @DeleteMapping("{courseId}")
    public R deleteCourse(@PathVariable String courseId){
        courseService.removeCourse(courseId);
        return R.ok();
    }

    /**
     *通过课程id获取课程信息
     * */
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId){
        CourseInfoVO courseInfoVO=courseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVO",courseInfoVO);
    }

    /**
     *修改课程信息
     * */
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVO courseInfoVO){
        courseService.updateCourseInfo(courseInfoVO);
        return R.ok();
    }

    /**
     * 通过课程id获取课程确认信息
     * */
    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id){
        CoursePublishVO courseInfo=courseService.getPublishCourseInfo(id);
        return R.ok().data("publishCourse",courseInfo);
    }

    /**
     *课程最终发布，修改课程状态
     * */
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        courseService.updateById(eduCourse);
        return R.ok();
    }
}


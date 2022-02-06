package com.hscs.controller.front;

import com.hscs.commonutils.R;
import com.hscs.entity.EduCourse;
import com.hscs.entity.HscsTeacher;
import com.hscs.service.EduCourseService;
import com.hscs.service.TeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/edu/indexfront")
@Api("首页教师和课程数据")
public class IndexFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private TeacherService teacherService;

    @ApiOperation("查询前8条热门课程，查询前4条名师")

    @GetMapping("index")
    public R index() {
        //查询前8条热门课程
        List<EduCourse> eduCourseList = courseService.getHotCourse();
        //查询前4条名师
        List<HscsTeacher> teacherList = teacherService.getTeacherList();
        return R.ok().data("eduList",eduCourseList).data("teacherList",teacherList);
    }

}

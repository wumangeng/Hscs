package com.hscs.controller;


import com.hscs.commonutils.JwtInfo;
import com.hscs.commonutils.JwtUtils;
import com.hscs.commonutils.R;
import com.hscs.entity.vo.CourseCollectVo;
import com.hscs.service.EduCourseCollectService;
import com.hscs.servicebase.exceptionhandler.HscsException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 课程收藏 前端控制器
 * </p>
 *
 * @author Painter
 * @since 2021-03-09
 */
@Api(description="课程收藏相关")
@RestController
@RequestMapping("/edu/edu-course-collect")

public class EduCourseCollectController {
    @Autowired
    private EduCourseCollectService courseCollectService;

    @ApiOperation(value = "添加收藏课程")
    @PostMapping("addCourseCollect/{courseId}")
    public R addCourseCollect(@ApiParam(name = "courseId", value = "课程id", required = true)
                              @PathVariable String courseId, HttpServletRequest request) {
        JwtInfo jwtInfo = JwtUtils.getMemberIdByJwtToken(request);
        courseCollectService.saveCourseCollect(courseId, jwtInfo.getId());
        return R.ok();
    }

    @ApiOperation(value = "获取课程收藏列表")
    @GetMapping("courseCollectList")
    public R showCourseCollect(HttpServletRequest request) {
        JwtInfo jwtInfo = JwtUtils.getMemberIdByJwtToken(request);
        List<CourseCollectVo> list = courseCollectService.selectListByMemberId(jwtInfo.getId());
        return R.ok().data("items", list);
    }

    @ApiOperation(value = "取消收藏课程,删除一条收藏的记录")
    @DeleteMapping("remove/{courseId}")
    public R removeCourseCollect(
            @ApiParam(name = "courseId", value = "课程id", required = true)
            @PathVariable String courseId,
            HttpServletRequest request) {

        JwtInfo jwtInfo = JwtUtils.getMemberIdByJwtToken(request);
        boolean result = courseCollectService.removeCourseCollect(courseId, jwtInfo.getId());
        if (result) {
            return R.ok().message("已取消收藏");
        } else {
            return R.error().message("取消收藏失败");
        }
    }

    @ApiOperation(value = "判断是否收藏")
    @GetMapping("is-collect/{courseId}")
    public R isCollect(
            @ApiParam(name = "courseId", value = "课程id", required = true)
            @PathVariable String courseId,
            HttpServletRequest request) {
        try{
            String userId = JwtUtils.getuserIdByJwtToken(request);
            boolean isCollect = courseCollectService.isCollect(courseId, userId);
            return R.ok().data("isCollect", isCollect);
        }catch (Exception e){
            return R.error().message("登录过期，请重新登录！");
        }
    }
}

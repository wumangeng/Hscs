package com.hscs.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hscs.commonutils.R;
import com.hscs.entity.HscsTeacher;
import com.hscs.entity.vo.TeacherSearchVO;
import com.hscs.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-12-20
 */
@RestController
@RequestMapping("edu/teacher")
@Api("教师信息控制器")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @ApiOperation("添加教师")
    @PostMapping("addTeacher")
    public R saveTeacher(@RequestBody HscsTeacher teacher){
        //System.out.println("添加教师传入："+teacher.toString());
        boolean save = teacherService.save(teacher);
        if (save) {
            return R.ok();
        }else {
            return R.error().message("添加教师失败！");
        }
    }
    @ApiOperation("逻辑删除教师")
    @DeleteMapping("{id}")
    public R deleteTeacher(@PathVariable("id") String id){
        boolean remove = teacherService.removeById(id);
        if (remove) {
            return R.ok();
        }else {
            return R.error().message("删除讲师失败！");
        }
    }
    @ApiOperation("修改教师")
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody HscsTeacher teacher){
        boolean update = teacherService.updateById(teacher);
        if (update) {
            return R.ok();
        }else {
            return R.error().message("修改教师失败！");
        }
    }

    /**
     * 查询所有讲师记录
     * */
    @ApiOperation(value = "获取所有讲师列表")
    @GetMapping("findAll")
    public R getAllTeacher() {
        List<HscsTeacher> teacherList = teacherService.list(null);
        return R.ok().data("teacherList",teacherList);
    }

    @ApiOperation("根据id查询教师")
    @GetMapping("getTeacherById/{id}")
    public R getTeacherById(@PathVariable("id")String id){
        HscsTeacher teacher = teacherService.getById(id);
        return R.ok().data("teacher",teacher);
    }

    @ApiOperation("分页查询所有教师")
    @GetMapping("getTeacherList/{current}/{limit}")
    public R getTeacherList(@PathVariable Integer current,@PathVariable Integer limit){
        Page<HscsTeacher> page = new Page<>(current, limit);
        QueryWrapper<HscsTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("gmt_create");
        teacherService.page(page,wrapper);
        long total = page.getTotal();
        //获得数据
        List<HscsTeacher> teacherList = page.getRecords();
        return R.ok().data("total",total).data("teacherList",teacherList);
    }

    @ApiOperation("条件分页查询教师")
    @PostMapping("getTeacherListCondition/{current}/{limit}")
    public R getTeacherListCondition(@RequestBody(required = false) TeacherSearchVO teacherSearchVO,
                                         @PathVariable Integer current,@PathVariable Integer limit){
        //System.out.println("教师条件查询传入teacherSearchVO -》"+teacherSearchVO.toString());
        Page<HscsTeacher> pageTeacher = new Page<>(current, limit);
        //构建条件
        QueryWrapper<HscsTeacher> wrapper = new QueryWrapper<>();

        String name = teacherSearchVO.getName();
        Integer level = teacherSearchVO.getLevel();
        String begin = teacherSearchVO.getBegin();
        String end = teacherSearchVO.getEnd();

        // 多条件组合查询
        if (!StringUtils.isEmpty(name)){
            //第一个字段为数据库列名， 第二个为传入值
            wrapper.like("name",name);
        }
        if(level!=null){
            wrapper.eq("level",level);
        }
        if (!StringUtils.isEmpty(begin)){
            //大于等于这个时间
            wrapper.ge("gmt_create",begin);
        }
        if (!StringUtils.isEmpty(end)){
            //小于等于
            wrapper.le("gmt_modified",end);
        }
        wrapper.orderByDesc("gmt_create");

        teacherService.page(pageTeacher,wrapper);

        long total = pageTeacher.getTotal();
        //获得数据
        List<HscsTeacher> teacherList = pageTeacher.getRecords();

        //System.out.println("教师条件分页查询结果teacherList -》"+teacherList.toString());
        return R.ok().data("total",total).data("teacherList",teacherList);
    }

}


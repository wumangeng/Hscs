package com.hscs.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hscs.commonutils.R;
import com.hscs.entity.EduStudent;
import com.hscs.entity.vo.StudentSearchVO;
import org.apache.commons.lang.StringUtils;
import com.hscs.service.EduStudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Painter
 * @since 2021-03-01
 */
@Api("学生信息控制器")
@RestController
@RequestMapping("/edu/student")
public class EduStudentController {

    @Autowired
    private EduStudentService studentService;

    @ApiOperation("添加学生信息")
    @PostMapping("addStudent")
    public R saveStudent(@RequestBody EduStudent student){
        boolean save = studentService.save(student);
        if (save) {
            return R.ok();
        }else {
            return R.error().message("添加学生信息失败！");
        }
    }

    @ApiOperation("逻辑删除学生信息")
    @DeleteMapping("{id}")
    public R deleteStudent(@PathVariable("id") String id){
        boolean remove = studentService.removeById(id);
        if (remove) {
            return R.ok();
        }else {
            return R.error().message("删除学生信息失败！");
        }
    }

    @ApiOperation("修改学生信息")
    @PostMapping("updateStudent")
    public R updateStudent(@RequestBody EduStudent student){
        boolean update = studentService.updateById(student);
        if (update) {
            return R.ok();
        }else {
            return R.error().message("修改学生信息失败！");
        }
    }

    @ApiOperation(value = "获取所有学生信息列表")
    @GetMapping("findAll")
    public R getAllStudent() {
        List<EduStudent> eduStudentList = studentService.list(null);
        return R.ok().data("studentList",eduStudentList);
    }

    @ApiOperation("根据id查询学生信息")
    @GetMapping("getStudentById/{id}")
    public R getStudentById(@PathVariable("id")String id){
        EduStudent student = studentService.getById(id);
        return R.ok().data("student",student);
    }

    @ApiOperation("分页查询所有学生信息")
    @GetMapping("getStudentList/{current}/{limit}")
    public R getStudentList(@PathVariable Integer current,@PathVariable Integer limit){
        Page<EduStudent> page = new Page<>(current, limit);
        QueryWrapper<EduStudent> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("gmt_create");

        studentService.page(page,wrapper);
        //获得数据
        List<EduStudent> studentList = page.getRecords();
        long total = page.getTotal();
        return R.ok().data("total",total).data("studentList",studentList);
    }

    @ApiOperation("条件分页查询学生信息")
    @PostMapping("getStudentListCondition/{current}/{limit}")
    public R getStudentListCondition(@RequestBody(required = false) StudentSearchVO studentSearchVO,
                                     @PathVariable Integer current,@PathVariable Integer limit){
        Page<EduStudent> pageTeacher = new Page<>(current, limit);
        //构建条件
        QueryWrapper<EduStudent> wrapper = new QueryWrapper<>();
        String name = studentSearchVO.getName();
        String className = studentSearchVO.getClassName();
        String studentNumber = studentSearchVO.getStudentNumber();
        String teacherName = studentSearchVO.getTeacherName();
        LocalDate beginDate = studentSearchVO.getBeginDate();
        // 多条件组合查询
        if (!StringUtils.isEmpty(name)){
            //第一个字段为数据库列名， 第二个为传入值
            wrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(className)){
            wrapper.like("class_name",className);
        }
        if (beginDate!=null){
            wrapper.ge("begin_date",beginDate);
        }
        if (!StringUtils.isEmpty(studentNumber)){
            wrapper.like("student_number",studentNumber);
        }
        if (!StringUtils.isEmpty(teacherName)){
            wrapper.like("teacher_name",teacherName);
        }
        wrapper.orderByDesc("gmt_create");

        studentService.page(pageTeacher,wrapper);
        long total = pageTeacher.getTotal();
        //获得数据
        List<EduStudent> studentList = pageTeacher.getRecords();

        return R.ok().data("total",total).data("studentList",studentList);
    }

}

package com.hscs.controller;

import com.hscs.entity.excel.OneSubject;
import com.hscs.service.EduSubjectService;
import com.hscs.commonutils.R;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-12-27
 */
@Api("课程分类管理")
@RestController
@RequestMapping("/edu/subject")
public class EduSubjectController {
    @Autowired
    private EduSubjectService subjectService;

    //添加课程分类
    /**
     * 获取上传过来的文件，读取文件内容
     * */
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file){
        subjectService.saveSubject(file,subjectService);
        return R.ok();
    }

    /**
     * 课程分类列表数据（树形）
     * */
    @GetMapping("getAllSubjects")
    public R getAllSubjects() {
        List<OneSubject> list=subjectService.getAllSubjects();
        return R.ok().data("list",list);
    }
}


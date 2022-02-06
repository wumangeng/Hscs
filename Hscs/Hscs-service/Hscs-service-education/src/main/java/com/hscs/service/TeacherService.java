package com.hscs.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hscs.entity.HscsTeacher;

import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @author ：Carina
 * @method : HscsTeacherService
 * @date : 2020/12/20 15:52
 */
public interface TeacherService extends IService<HscsTeacher> {

    Map<String, Object> getTeacherFrontList(Page<HscsTeacher> pageTeacher);

    List<HscsTeacher> getTeacherList();
}

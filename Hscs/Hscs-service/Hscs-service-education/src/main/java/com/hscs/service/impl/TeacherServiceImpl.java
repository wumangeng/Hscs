package com.hscs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hscs.entity.HscsTeacher;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hscs.mapper.TeacherMapper;
import com.hscs.service.TeacherService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-12-20
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, HscsTeacher> implements TeacherService {
    /** 分页查询讲师的方法*/
    @Override
    public Map<String, Object> getTeacherFrontList(Page<HscsTeacher> pageParam) {
        QueryWrapper<HscsTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("level");
        //把分页数据封装到pageTeacher对象
        baseMapper.selectPage(pageParam,wrapper);

        List<HscsTeacher> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        //下一页
        boolean hasNext = pageParam.hasNext();
        //上一页
        boolean hasPrevious = pageParam.hasPrevious();

        //把分页数据获取出来，放到map集合
        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }

    @Cacheable(value = "teacherList",key = "'indexTeacherList'")
    @Override
    public List<HscsTeacher> getTeacherList() {
        //查询前4条名师
        QueryWrapper<HscsTeacher> wrapperTeacher = new QueryWrapper<>();
        wrapperTeacher.orderByDesc("teacher_id");
        wrapperTeacher.last("limit 4");
        List<HscsTeacher> teacherList = baseMapper.selectList(wrapperTeacher);
        return teacherList;
    }
}

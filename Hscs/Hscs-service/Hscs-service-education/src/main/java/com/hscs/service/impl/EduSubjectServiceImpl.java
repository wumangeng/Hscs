package com.hscs.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hscs.entity.EduSubject;
import com.hscs.entity.excel.ExcelSubjectData;
import com.hscs.entity.excel.OneSubject;
import com.hscs.entity.excel.TwoSubject;
import com.hscs.listener.SubjectExcelListener;
import com.hscs.mapper.EduSubjectMapper;
import com.hscs.service.EduSubjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-12-27
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    //添加课程分类
    @Override
    public void saveSubject(MultipartFile file,EduSubjectService subjectService) {
        try {
            //获取文件输入流
            InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream, ExcelSubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();
        }catch (Exception e){
            System.out.println("文件读取异常");
            e.printStackTrace();
        }
    }

    @Override
    public List<OneSubject> getAllSubjects() {
        // 1、查询所有一级分类
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
            //等于0
        wrapperOne.eq("parent_id","0");
        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapperOne);
            // List<EduSubject> oneSubjectList = this.list(wrapperOne);  等同于上面一行代码

        // 2、查询所有二级分类
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
            //不等于0
        wrapperTwo.ne("parent_id","0");
        List<EduSubject> twoSubjectList = this.list(wrapperTwo);

        List<OneSubject> finalSubjectsList = new ArrayList<>();


        oneSubjectList.forEach(eduSubject->{
            // 3、封装一级分类
            OneSubject oneSubject = new OneSubject();
            oneSubject.setId(eduSubject.getId());
            oneSubject.setTitle(eduSubject.getTitle());
            //BeanUtils.copyProperties(eduSubject, oneSubject);  //eduSubject 值复制到 oneSubject里面
            finalSubjectsList.add(oneSubject);

            // 4、封装二级分类
            List<TwoSubject> twoFinaSubjects = new ArrayList<>();
            twoSubjectList.forEach(twoSubject -> {
                TwoSubject subject = new TwoSubject();
                if (eduSubject.getId().equals(twoSubject.getParentId())){
                    BeanUtils.copyProperties(twoSubject,subject);
                    twoFinaSubjects.add(subject);
                }
            });
            oneSubject.setChildren(twoFinaSubjects);
        });


        return finalSubjectsList;
    }
}

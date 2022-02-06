package com.hscs.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hscs.entity.EduSubject;
import com.hscs.entity.excel.ExcelSubjectData;
import com.hscs.service.EduSubjectService;

/**
 * 此监听器无法自动注入spring容器所以得提供构造器，手动管理对象
 *
 * @author ：Carina
 * @method : SubjectExcelListener
 * @date : 2020/12/27 20:48
 */
public class SubjectExcelListener extends AnalysisEventListener<ExcelSubjectData> {
    public EduSubjectService subjectService;
    public SubjectExcelListener(){}
    public SubjectExcelListener(EduSubjectService subjectService){
        this.subjectService=subjectService;
    }

    /**
     * 逐行读取Excel内容
     * */
    @Override
    public void invoke(ExcelSubjectData excelSubjectData, AnalysisContext analysisContext) {
        if (excelSubjectData==null){
            throw new IllegalArgumentException();
        }
        //逐行读取，每行有两个值分别为一级分类和二级分类
        //判断一级分类是否存在，不存在则添加
        EduSubject exitOneSubject = this.exitOneSubject(subjectService, excelSubjectData.getOneSubjectName());
        if (exitOneSubject==null){
            exitOneSubject = new EduSubject();
            exitOneSubject.setParentId("0");
            exitOneSubject.setTitle(excelSubjectData.getOneSubjectName());
            subjectService.save(exitOneSubject);
        }
        String pid = exitOneSubject.getId();
        //判断二级分类是否存在  第三个参数为一级分类的id
        EduSubject exitTwoSubject = this.exitTwoSubject(subjectService, excelSubjectData.getTwoSubjectName(),pid);
        if (exitTwoSubject==null){
            exitTwoSubject = new EduSubject();
            exitTwoSubject.setParentId(pid);
            exitTwoSubject.setTitle(excelSubjectData.getTwoSubjectName());
            subjectService.save(exitTwoSubject);
        }
    }

    /**
     * 判断一级分类不能重复添加
     * */
    private EduSubject exitOneSubject(EduSubjectService subjectService,String name){
        //从数据库查询是否存在
        //select * from edu_subjects where title=? and parent_id=0
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id","0");
        EduSubject oneSubject = subjectService.getOne(wrapper);
        return oneSubject;
    }

    /**
     * 判断二级分类不能重复添加
     * */
    private EduSubject exitTwoSubject(EduSubjectService subjectService,String name,String pid){
        //从数据库查询是否存在
        //select * from edu_subjects where title=? and parent_id=?
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",pid);
        EduSubject twoSubject = subjectService.getOne(wrapper);
        return twoSubject;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}

package com.hscs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hscs.entity.EduSubject;
import com.hscs.entity.excel.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-12-27
 */
public interface EduSubjectService extends IService<EduSubject> {

    void saveSubject(MultipartFile file,EduSubjectService subjectService);

    List<OneSubject> getAllSubjects();
}

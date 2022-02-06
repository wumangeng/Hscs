package com.hscs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hscs.entity.EduChapter;
import com.hscs.entity.chapter.ChapterVO;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-01-03
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVO> getChapterVideoByCourseId(String courseId);

    /**
     * 如果章节内部有小节就不能删除章节
     * */
    int deleteChapter(String chapterId);

    void removeChapterByCourseId(String courseId);
}

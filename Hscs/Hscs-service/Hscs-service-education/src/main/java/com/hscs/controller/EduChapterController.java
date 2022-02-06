package com.hscs.controller;


import com.hscs.entity.EduChapter;
import com.hscs.entity.chapter.ChapterVO;
import com.hscs.service.EduChapterService;
import com.hscs.commonutils.R;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 章节和小节 前端控制器
 *
 * @author testjava
 * @since 2021-01-03
 */

@Api("课程章节管理")
@RestController
@RequestMapping("/edu/chapter")
public class EduChapterController {

    @Autowired
    private EduChapterService chapterService;

    /**
     * 根据课程id查询大纲列表
     * */
    @GetMapping("getAllChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId){
        List<ChapterVO> list=chapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("allChapterVideo",list);
    }

    /**
     * 添加章节
     * */
    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter){
        chapterService.save(eduChapter);
        return R.ok();
    }

    /**
     * 根据章节id查询
     * */
    @GetMapping("getChapterInfo/{chapterId}")
    public R getChapter(@PathVariable String chapterId){
        EduChapter eduChapter = chapterService.getById(chapterId);
        return R.ok().data("chapter",eduChapter);
    }
    /**
     * 修改章节信息
     * */
    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter){
        chapterService.updateById(eduChapter);
        return R.ok();
    }

    /**
     * 删除章节
     * */
    @DeleteMapping("{courseId}")
    public R deleteChapter(@PathVariable String courseId){
        int flag = chapterService.deleteChapter(courseId);
        if (flag > 0){
            return R.ok();
        }else{
            return R.error();
        }
    }
}


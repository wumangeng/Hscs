package com.hscs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hscs.entity.EduChapter;
import com.hscs.entity.EduVideo;
import com.hscs.entity.chapter.ChapterVO;
import com.hscs.entity.chapter.VideoVO;
import com.hscs.mapper.EduChapterMapper;
import com.hscs.service.EduChapterService;
import com.hscs.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-01-03
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService videoService;

    @Override
    public List<ChapterVO> getChapterVideoByCourseId(String courseId) {
        //1、根据课程id查询里面的所有章节
        QueryWrapper<EduChapter> chapterWeapper = new QueryWrapper<>();
        chapterWeapper.eq("course_id",courseId);
        List<EduChapter> chapterList = baseMapper.selectList(chapterWeapper);

        //2、根据课程id查询所有的小节
        QueryWrapper<EduVideo> videoWeapper = new QueryWrapper<>();
        videoWeapper.eq("course_id",courseId);
        List<EduVideo> videoList = videoService.list(videoWeapper);

        List<ChapterVO> finallChapterVOList=new ArrayList<>();
        //3、遍历查询的章节进行数据封装
        chapterList.forEach(eduChapter -> {
            ChapterVO ChapterVO = new ChapterVO();
            BeanUtils.copyProperties(eduChapter,ChapterVO);
            finallChapterVOList.add(ChapterVO);

            List<VideoVO> videoVOList=new ArrayList<>();
            //4、遍历小节，判断属于哪个章节
            videoList.forEach(eduVideo -> {
                if (eduVideo.getChapterId().equals(ChapterVO.getId())){
                    VideoVO videoVO = new VideoVO();
                    BeanUtils.copyProperties(eduVideo,videoVO);
                    videoVOList.add(videoVO);
                }
            });
            ChapterVO.setChildren(videoVOList);
        });

        return finallChapterVOList;
    }

    @Override
    public int deleteChapter(String chapterId) {
        // 根据章节id查询是否含有小节
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        // 返回记录数
        int count = videoService.count(wrapper);
        if (count > 0){
            // 章节包含小节不进行删除
            return 0;
        }else {
            return baseMapper.deleteById(chapterId);
        }
    }

    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId);
        baseMapper.delete(queryWrapper);
    }
}

package com.hscs.controller;


import com.hscs.client.VideoClient;
import com.hscs.entity.EduVideo;
import com.hscs.service.EduVideoService;
import com.hscs.commonutils.R;
import com.hscs.servicebase.exceptionhandler.HscsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-01-03
 */
@RestController
@RequestMapping("/edu/video")
public class EduVideoController {
    @Autowired
    private EduVideoService videoService;

    @Autowired
    private VideoClient videoClient;

    /**
     * 添加小节
     * */
    @PostMapping("addVideo")
    public R addChapterVideo (@RequestBody EduVideo eduVideo){
        videoService.save(eduVideo);
        return R.ok();
    }

    /**
     * 删除小节(小节id)
     * */
    @DeleteMapping("{id}")
    public R deleteChapterVideo(@PathVariable String id){
        //获取小节内对应的视频id
        EduVideo eduVideo = videoService.getById(id);
        String videoSourceId = eduVideo.getVideoSourceId();
        //删除视频
        if(!StringUtils.isEmpty(videoSourceId)) {
            //根据视频id，远程调用实现视频删除
            R result = videoClient.removeALiYunVideo(videoSourceId);
            if(result.getCode() == 20001) {
                throw new HscsException(20001,"删除视频失败，熔断器开启...");
            }
        }
        //删除小节
        videoService.removeById(id);
        return R.ok();
    }

    /**
     * 修改小节
     * */
    @PostMapping("updateVideo")
    public R updateChapterVideo(@RequestBody EduVideo eduVideo){
        videoService.updateById(eduVideo);
        return R.ok();
    }

    /**
     * 根据主键id查询小节
     * */
    @GetMapping("/getVideo/{id}")
    public R getVideoById(@PathVariable String id){
        EduVideo eduVideo = videoService.getById(id);
        return R.ok().data("eduVideo",eduVideo);
    }

}


package com.hscs.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.hscs.servicebase.exceptionhandler.HscsException;
import com.hscs.utils.InitVideoCilent;
import com.hscs.utils.ConstantVideoUtils;
import com.hscs.commonutils.R;
import com.hscs.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author Painter
 * @Description 阿里云视频点播，视频上传控制器
 * @Date 2021/1/26 9:38
 **/

@RestController
@RequestMapping("/videoupload/video")
@Api("视频上传控制器")
public class VideoController {

    @Autowired
    private VideoService vodService;

    @ApiModelProperty("本地上传视频到阿里云")
    @PostMapping("uploadVideo")
    public R uploadALiYunVideo(MultipartFile file){
        //返回上传视频后的id
        String videoId= vodService.uploadALiYunVideo(file);
        return R.ok().data("videoId",videoId);
    }


    @ApiModelProperty("删除视频")
    @DeleteMapping("{id}")
    public R removeALiYunVideo(@PathVariable String id){
        String accessKeyId = ConstantVideoUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantVideoUtils.ACCESS_KEY_SECRET;
        try{
            //初始化对象
            DefaultAcsClient client = InitVideoCilent.initVodClient(accessKeyId, accessKeySecret);
            //创建删除视频request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //向request设置视频id
            request.setVideoIds(id);
            client.getAcsResponse(request);
            return R.ok();
        }catch (Exception e){
            e.printStackTrace();
            return R.error();
        }

    }

    /**参数多个视频id  List videoIdList*/
    @ApiModelProperty("删除多个阿里云视频的方法,")
    @DeleteMapping("delete-batch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList) {
        vodService.removeMoreALiYunVideo(videoIdList);
        return R.ok();
    }

    @ApiModelProperty("根据视频id获取视频凭证")
    @GetMapping("getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable String id) {
        try {
            //创建初始化对象
            DefaultAcsClient client =
                    InitVideoCilent.initVodClient(ConstantVideoUtils.ACCESS_KEY_ID, ConstantVideoUtils.ACCESS_KEY_SECRET);
            //创建获取凭证request和response对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            //向request设置视频id
            request.setVideoId(id);
            //调用方法得到凭证
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return R.ok().data("playAuth",playAuth);
        }catch(Exception e) {
            throw new HscsException(20001,"获取凭证失败");
        }
    }

}

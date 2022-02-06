package com.hscs.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.hscs.servicebase.exceptionhandler.HscsException;
import com.hscs.utils.ConstantVideoUtils;
import com.hscs.service.VideoService;
import com.hscs.utils.InitVideoCilent;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * @Author Painter
 * @Description 上传方法实现(流式上传)
 * @Date 2021/1/26 9:44
 **/

@Service
public class VideoServiceImpl implements VideoService {

    @Override
    public String uploadALiYunVideo(MultipartFile file) {
        String accessKeyId = ConstantVideoUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantVideoUtils.ACCESS_KEY_SECRET;

        try {
            //accessKeyId, accessKeySecret
            //fileName：上传文件原始名称
            // 01.03.09.mp4
            String fileName = file.getOriginalFilename();
            //title：上传之后显示名称 01.03.09
            String title = fileName.substring(0, fileName.lastIndexOf("."));
            //inputStream：上传文件输入流
            InputStream inputStream = file.getInputStream();

            UploadStreamRequest request = new UploadStreamRequest(accessKeyId,accessKeySecret, title, fileName, inputStream);

            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

            String videoId = null;
            if (response.isSuccess()) {
                videoId = response.getVideoId();
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoId = response.getVideoId();
            }
            return videoId;
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public void removeMoreALiYunVideo(List<String> videoIdList) {
        try {
            //初始化对象
            DefaultAcsClient client = InitVideoCilent.initVodClient(ConstantVideoUtils.ACCESS_KEY_ID, ConstantVideoUtils.ACCESS_KEY_SECRET);
            //创建删除视频request对象
            DeleteVideoRequest request = new DeleteVideoRequest();

            //videoIdList值转换成 1,2,3
            String videoIds = StringUtils.join(videoIdList.toArray(), ",");

            //向request设置视频id
            request.setVideoIds(videoIds);
            //调用初始化对象的方法实现删除
            client.getAcsResponse(request);
        }catch(Exception e) {
            e.printStackTrace();
            throw new HscsException(20001,"删除视频失败");
        }
    }
}

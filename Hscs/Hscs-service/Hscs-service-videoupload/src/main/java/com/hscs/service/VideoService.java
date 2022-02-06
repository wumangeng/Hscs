package com.hscs.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author Painter
 * @Description 阿里云视频相关接口
 * @Date 2021/1/26 9:44
 **/

public interface VideoService {

    /**上传视频到阿里云*/
    String uploadALiYunVideo(MultipartFile file);

    /**删除多个阿里云视频的方法*/
    void removeMoreALiYunVideo(List<String> videoIdList);
}

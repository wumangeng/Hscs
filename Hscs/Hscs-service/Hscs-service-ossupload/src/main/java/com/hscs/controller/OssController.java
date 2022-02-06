package com.hscs.controller;

import com.hscs.commonutils.R;
import com.hscs.service.OssService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("ossupload/fileoss")
@Api("图片上传控制器")
public class OssController {

    @Autowired
    private OssService ossService;

    /**
     * 上传头像
     * */
    @PostMapping
    public R uploadFileOss(MultipartFile file){
        String url=ossService.uploadFileAvatar(file);
        return R.ok().data("url",url);
    }

}

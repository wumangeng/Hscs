package com.hscs.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.hscs.utils.ConstantPropertiesUtlis;
import com.hscs.service.OssService;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

/**
 * @author wmg
 *  文件上传业务实现
 */
@Service
public class OssServiceImpl implements OssService {
    @Override
    public String uploadFileAvatar(MultipartFile file) {
        String endpoint = ConstantPropertiesUtlis.END_POINT;
        String accessKeyId = ConstantPropertiesUtlis.KEY_ID;
        String accessKeySecret = ConstantPropertiesUtlis.KEY_SECRET;
        String bucketName=ConstantPropertiesUtlis.BUCKET_NAME;

        try{
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            //获取文件名称
            String fileName = file.getOriginalFilename();
            //在文件里面添加随机唯一的id值，避免名字重复造成文件覆盖
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            fileName=uuid+fileName;
            //将文件按日期分类
            String datePath = new DateTime().toString("yyyy/MM/dd");
            fileName=datePath+"/"+fileName;
            // 上传文件流。
            InputStream inputStream = file.getInputStream();
            //第一个参数为bucket名  第二个参数为上传文件路径
            ossClient.putObject(bucketName, fileName, inputStream);

            //将上传路径返回  https://home-school-communication.oss-cn-guangzhou.aliyuncs.com/image/1.jpg
            String url="https://"+bucketName+"."+endpoint+"/"+fileName;


            // 关闭OSSClient。
            ossClient.shutdown();
            return url;
        }catch (Exception e){
            System.out.println("图片上传失败！");
            e.printStackTrace();
            return null;
        }

    }
}

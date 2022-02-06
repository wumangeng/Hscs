package com.hscs.client.fallback;

import com.hscs.client.VideoClient;
import com.hscs.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VideoFileDegradeFeignClient implements VideoClient {
   //出错之后会执行
    @Override
    public R removeALiYunVideo(String id) {
        return R.error().message("删除视频出错了");
    }

    @Override
    public R deleteBatch(List<String> videoIdList) {
        return R.error().message("删除多个视频出错了");
    }
}

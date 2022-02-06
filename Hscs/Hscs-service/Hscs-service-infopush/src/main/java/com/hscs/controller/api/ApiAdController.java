package com.hscs.controller.api;

import com.hscs.commonutils.R;
import com.hscs.entity.CmsAd;
import com.hscs.service.CmsAdService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(description = "广告推荐")
@RestController
@RequestMapping("/infopush/front-cms")
@Slf4j
public class ApiAdController {

    @Autowired
    private CmsAdService adService;

    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation("根据推荐位id显示广告推荐")
    @GetMapping("list/{adTypeId}")
    public R listByAdTypeId(@ApiParam(value = "推荐位id", required = true) @PathVariable String adTypeId) {

        List<CmsAd> adList = adService.selectByAdTypeId(adTypeId);
        return R.ok().data("items", adList);
    }


    @PostMapping("save-test")
    public R saveAd(@RequestBody CmsAd ad){

        redisTemplate.opsForValue().set("index::myad", ad);
        return R.ok();
    }

    @GetMapping("get-test/{key}")
    public R getAd(@PathVariable String key){

        CmsAd ad = (CmsAd)redisTemplate.opsForValue().get(key);
        return R.ok().data("ad", ad);
    }

    @DeleteMapping("remove-test/{key}")
    public R removeAd(@PathVariable String key){

        Boolean delete = redisTemplate.delete(key);
        System.out.println(delete);
        Boolean aBoolean = redisTemplate.hasKey(key);
        System.out.println(aBoolean);
        return R.ok();
    }
}

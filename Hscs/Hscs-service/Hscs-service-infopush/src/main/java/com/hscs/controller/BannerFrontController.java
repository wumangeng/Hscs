package com.hscs.controller;


import com.hscs.commonutils.R;
import com.hscs.entity.InfoBanner;
import com.hscs.service.InfoBannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前台bannber显示
 * </p>
 *
 * @author testjava
 * @since 2020-03-07
 */
@Api("用户获取门户前台banner控制器")
@RestController
@RequestMapping("/infopush/bannerfront")

public class BannerFrontController {

    @Autowired
    private InfoBannerService bannerService;

    @ApiOperation(value = "查询所有banner")
    @GetMapping("getAllBanner")
    public R getAllBanner() {
        List<InfoBanner> list = bannerService.selectAllBanner();
        return R.ok().data("list",list);
    }
}


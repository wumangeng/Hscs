package com.hscs.service;

import com.hscs.entity.InfoBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author Painter
 * @since 2021-03-03
 */
public interface InfoBannerService extends IService<InfoBanner> {
    /**
     * 查询所有banner图
     * */
    List<InfoBanner> selectAllBanner();
}

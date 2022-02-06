package com.hscs.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hscs.entity.CmsAd;
import com.hscs.entity.vo.AdVo;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 */
public interface CmsAdService extends IService<CmsAd> {

    IPage<AdVo> selectPage(Long page, Long limit);

    boolean removeAdImageById(String id);

    List<CmsAd> selectByAdTypeId(String adTypeId);
}

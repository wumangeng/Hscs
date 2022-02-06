package com.hscs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hscs.entity.InfoBanner;
import com.hscs.mapper.InfoBannerMapper;
import com.hscs.service.InfoBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author Painter
 * @since 2021-03-03
 */
@Service
public class InfoBannerServiceImpl extends ServiceImpl<InfoBannerMapper, InfoBanner> implements InfoBannerService {
    /**
     * 查询所有banner并放入缓存
     * */
    @Cacheable(value = "bannerList",key = "'selectIndexList'")
    @Override
    public List<InfoBanner> selectAllBanner() {

        //根据id进行降序排列，显示排列之后前3条记录
        QueryWrapper<InfoBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        //last方法，拼接sql语句
        wrapper.last("limit 3");
        List<InfoBanner> bannerList = baseMapper.selectList(null);
        return bannerList;
    }
}

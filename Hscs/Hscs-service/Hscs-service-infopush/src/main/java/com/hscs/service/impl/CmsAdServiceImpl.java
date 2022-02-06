package com.hscs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hscs.entity.CmsAd;
import com.hscs.entity.vo.AdVo;
import com.hscs.feign.OssFileService;
import com.hscs.mapper.CmsAdMapper;
import com.hscs.service.CmsAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 */
@Service
public class CmsAdServiceImpl extends ServiceImpl<CmsAdMapper, CmsAd> implements CmsAdService {

    @Autowired
    private OssFileService ossFileService;

    @Override
    public IPage<AdVo> selectPage(Long page, Long limit) {

        QueryWrapper<AdVo> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("a.type_id", "a.sort");

        Page<AdVo> pageParam = new Page<>(page, limit);

        List<AdVo> records = baseMapper.selectPageByQueryWrapper(pageParam, queryWrapper);
        pageParam.setRecords(records);
        return pageParam;
    }

    @Override
    public boolean removeAdImageById(String id) {
        CmsAd ad = baseMapper.selectById(id);
        if(ad != null) {
            String imagesUrl = ad.getImageUrl();
            if(!StringUtils.isEmpty(imagesUrl)){
                //删除图片
                ossFileService.removeFile(imagesUrl);
                return true;
            }
        }
        return false;
    }

    /**
     * 1、查询redis缓存中是否存在需要的数据  hasKey
     * 2、如果缓存不存在从数据库中取出数据、并将数据存入缓存  set
     * 3、如果缓存存在则从缓存中读取数据  get
     * @param adTypeId
     * @return
     */
    @Cacheable(value = "index", key = "'selectByAdTypeId'")
    @Override
    public List<CmsAd> selectByAdTypeId(String adTypeId) {

        QueryWrapper<CmsAd> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort", "id");
        queryWrapper.eq("type_id", adTypeId);
        return baseMapper.selectList(queryWrapper);
    }
}

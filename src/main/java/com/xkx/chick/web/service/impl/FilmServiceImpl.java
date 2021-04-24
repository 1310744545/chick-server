package com.xkx.chick.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xkx.chick.common.util.StringUtils;
import com.xkx.chick.web.mapper.FilmMapper;
import com.xkx.chick.web.pojo.entity.Film;
import com.xkx.chick.web.service.IFilmService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 影视表 服务实现类
 * </p>
 *
 * @author 肖可欣
 * @since 2021-02-28
 */
@Service
public class FilmServiceImpl extends ServiceImpl<FilmMapper, Film> implements IFilmService {
    /**
     * 获取电影列表
     *
     * @param validPage 分页
     * @param keyword   关键字
     * @param type      关键字
     * @param delFlag   删除标记
     * @return R
     */
    @Override
    public Page<Film> list(Page<Film> validPage, String keyword, String type, String delFlag) {
        LambdaQueryWrapper<Film> wrapper = Wrappers.<Film>lambdaQuery()
                .select(Film::getName, Film::getId, Film::getScore, Film::getCoverUrl, Film::getUpdateDate)
                .eq(Film::getDelFlag, delFlag)
                .orderByDesc(Film::getCreateDate);
        if (StringUtils.isNotBlank(keyword)) {
            wrapper.and(wr -> wr.like(Film::getName, keyword));
        }
        if (StringUtils.isNotBlank(type)) {
            wrapper.and(wr -> wr.like(Film::getTag, type));
        }
        return baseMapper.selectPage(validPage, wrapper);
    }
}

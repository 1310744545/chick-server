package com.xkx.chick.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xkx.chick.web.mapper.FilmContentMapper;
import com.xkx.chick.web.mapper.FilmMapper;
import com.xkx.chick.web.pojo.entity.Film;
import com.xkx.chick.web.pojo.entity.FilmContent;
import com.xkx.chick.web.service.IFilmContentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 影视内容 服务实现类
 * </p>
 *
 * @author 肖可欣
 * @since 2021-02-28
 */
@Service
public class FilmContentServiceImpl extends ServiceImpl<FilmContentMapper, FilmContent> implements IFilmContentService {

    @Resource
    private FilmMapper filmMapper;
    @Override
    public Film detail(String filmId) {
        return filmMapper.selectById(filmId);
    }

    @Override
    public List<FilmContent> getContentListByFilmId(String filmId) {
        return baseMapper.getContentListByFilmId(filmId);
    }
}

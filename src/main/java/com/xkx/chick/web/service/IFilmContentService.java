package com.xkx.chick.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xkx.chick.web.pojo.entity.Film;
import com.xkx.chick.web.pojo.entity.FilmContent;

import java.util.List;

/**
 * <p>
 * 影视内容 服务类
 * </p>
 *
 * @author 肖可欣
 * @since 2021-02-28
 */
public interface IFilmContentService extends IService<FilmContent> {

    /**
     * 获取软件详细信息
     *
     * @param filmId 电影id
     * @return R
     */
    Film detail(String filmId);

    /**
     * 获取软件详细信息
     *
     * @param filmId 电影id
     * @return R
     */
    List<FilmContent> getContentListByFilmId(String filmId);
}

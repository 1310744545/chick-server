package com.xkx.chick.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xkx.chick.web.pojo.entity.FilmContent;

import java.util.List;

/**
 * <p>
 * 影视内容 Mapper 接口
 * </p>
 *
 * @author 肖可欣
 * @since 2021-02-28
 */
public interface FilmContentMapper extends BaseMapper<FilmContent> {

    List<FilmContent> getContentListByFilmId(String filmId, String type);
}

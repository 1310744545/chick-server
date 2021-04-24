package com.xkx.chick.web.service.impl;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xkx.chick.common.base.R;
import com.xkx.chick.common.util.StringUtils;
import com.xkx.chick.web.mapper.FilmContentMapper;
import com.xkx.chick.web.mapper.FilmMapper;
import com.xkx.chick.web.pojo.entity.Film;
import com.xkx.chick.web.pojo.entity.FilmContent;
import com.xkx.chick.web.service.IFilmContentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringJoiner;

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
        Film film = filmMapper.selectById(filmId);
        StringJoiner stringJoiner = new StringJoiner(" ", "", "");
        if (ObjectUtils.isNotEmpty(film)) {
            for (int i = 0; i <= 20; i++) {
                if (StringUtils.isNotEmpty(film.getPlayerSupport())) {
                    if (film.getPlayerSupport().contains("" + i)) {
                        stringJoiner.add("小鸡" + i + "线");
                    }
                }
            }
        }
        film.setPlayerSupport(stringJoiner.toString());
        return film;
    }

    @Override
    public R getContentListByFilmId(String filmId, String type) {
        List<FilmContent> contentListByFilmId = baseMapper.getContentListByFilmId(filmId, type);
        HashMap<String, List<FilmContent>> result = new HashMap<>();
        for (FilmContent filmContent : contentListByFilmId) {
            if (ObjectUtils.isNotEmpty(filmContent) && StringUtils.isNotEmpty(filmContent.getType())) {
                if (result.size() < 1) {
                    ArrayList<FilmContent> list = new ArrayList<>();
                    list.add(filmContent);
                    result.put(filmContent.getType(), list);
                } else {
                    boolean flag = true;
                    for (String key : result.keySet()) {
                        if (filmContent.getType().equals(key)) {
                            flag = false;
                        }
                    }
                    if (flag) {
                        ArrayList<FilmContent> filmContents = new ArrayList<>();
                        filmContents.add(filmContent);
                        result.put(filmContent.getType(), filmContents);
                    } else {
                        result.get(filmContent.getType()).add(filmContent);
                    }
                }
            }
        }
        return R.ok(result);
    }

    @Override
    public R contentById(String filmContentId) {
        FilmContent filmContent = baseMapper.selectById(filmContentId);
        if (ObjectUtils.isEmpty(filmContent)){
            return R.failed("视频内容不见了");
        }
        return R.ok(filmContent);
    }
}

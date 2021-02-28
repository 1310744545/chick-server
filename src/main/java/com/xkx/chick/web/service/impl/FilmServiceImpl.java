package com.xkx.chick.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

}

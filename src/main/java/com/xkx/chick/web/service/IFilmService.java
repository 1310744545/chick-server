package com.xkx.chick.web.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xkx.chick.web.pojo.entity.Film;

/**
 * <p>
 * 影视表 服务类
 * </p>
 *
 * @author 肖可欣
 * @since 2021-02-28
 */
public interface IFilmService extends IService<Film> {
    /**
     * 获取电影列表
     *
     * @param validPage 分页
     * @param keyword   关键字
     * @param type      关键字
     * @param delFlag   删除标记
     * @return R
     */
    Page<Film> list(Page<Film> validPage, String keyword, String type, String delFlag);
}

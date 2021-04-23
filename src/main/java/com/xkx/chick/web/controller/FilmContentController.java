package com.xkx.chick.web.controller;


import com.xkx.chick.common.base.R;
import com.xkx.chick.common.controller.BaseController;
import com.xkx.chick.common.util.StringUtils;
import com.xkx.chick.web.service.IFilmContentService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 影视内容 前端控制器
 * </p>
 *
 * @author 肖可欣
 * @since 2021-02-28
 */
@RestController
@RequestMapping("/chick/filmContent")
public class FilmContentController extends BaseController {

    @Resource
    private IFilmContentService filmContentService;

    @ApiOperation(value = "电影详情", position = 1, httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "filmId", name = "filmId", value = "电影id", required = true),
    })
    @GetMapping("/detail")
    public R detail(String filmId) {
        if (!StringUtils.isNotBlank(filmId)) {
            return R.failed("电影id为空");
        }
        return R.ok(filmContentService.detail(filmId));
    }

    @ApiOperation(value = "电影内容", position = 1, httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "filmId", name = "filmId", value = "电影id", required = true),
    })
    @GetMapping("/getContentListByFilmId")
    public R getContentListByFilmId(String filmId) {
        if (!StringUtils.isNotBlank(filmId)) {
            return R.failed("电影id为空");
        }
        return filmContentService.getContentListByFilmId(filmId);
    }


}

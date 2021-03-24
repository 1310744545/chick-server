package com.xkx.chick.web.controller;


import com.xkx.chick.common.base.R;
import com.xkx.chick.common.controller.BaseController;
import com.xkx.chick.web.service.IWritingService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 帖子 前端控制器
 * </p>
 *
 * @author 肖可欣
 * @since 2021-02-28
 */
@RestController
@RequestMapping("/chick/writing")
public class WritingController extends BaseController {

    @Resource
    private IWritingService writingService;

    @ApiOperation(value = "保存文章", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "标题", required = true, paramType = "query"),
            @ApiImplicitParam(name = "type", value = "类型id", required = true, paramType = "query"),
            @ApiImplicitParam(name = "content", value = "内容", required = true, paramType = "query"),
    })
    @PostMapping("/saveWrite")
    public R saveWrite(String title, String type, String content) {
        if (StringUtils.isAnyBlank(title, type, content)) {
            return R.failed("有必填项为空");
        }
        return writingService.saveWrite(title, type, content);
    }
}

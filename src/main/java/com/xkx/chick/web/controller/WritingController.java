package com.xkx.chick.web.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xkx.chick.common.base.R;
import com.xkx.chick.common.controller.BaseController;
import com.xkx.chick.common.util.PageUtils;
import com.xkx.chick.web.pojo.vo.WritingVO;
import com.xkx.chick.web.service.IWritingService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
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
        return writingService.saveWrite(title, type, content, getUserId());
    }

    @ApiOperation(value = "获取文章", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "current", value = "当前页码", required = true),
            @ApiImplicitParam(paramType = "query", name = "size", value = "分页数量", required = true),
            @ApiImplicitParam(paramType = "query", name = "keyword", value = "关键字", required = true),
            @ApiImplicitParam(paramType = "query", name = "delFlag", value = "删除标记", required = true),
    })
    @GetMapping("/indexList")
    public R<Page<WritingVO>> indexList(Integer current, Integer size, String keyword, String delFlag) {
        if (current == null || size == null) {
            return R.failed("分页信息为空");
        }
        return R.ok(writingService.indexList(PageUtils.validPage(current, size), keyword, delFlag));
    }

    @ApiOperation(value = "获取文章内容", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "文章id", required = true),
    })
    @GetMapping("/getWriteContent")
    public R<WritingVO> getWriteContent(String writingId) {
        if (writingId == null || "".equals(writingId)) {
            return R.failed("文章id为空");
        }
        return R.ok(writingService.getWriteContent(writingId));
    }
}

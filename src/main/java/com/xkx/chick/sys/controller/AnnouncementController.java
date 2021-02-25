package com.xkx.chick.sys.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xkx.chick.common.base.R;
import com.xkx.chick.common.constant.CommonConstants;
import com.xkx.chick.common.controller.BaseController;
import com.xkx.chick.common.util.PageUtils;
import com.xkx.chick.common.util.StringUtils;
import com.xkx.chick.sys.pojo.entity.Announcement;
import com.xkx.chick.sys.service.IAnnouncementManagerService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName AnnouncementController
 * @Author xiaokexin
 * @Date 2021/2/15 13:49
 * @Description AnnouncementController
 * @Version 1.0
 */
@RestController
@RequestMapping("/announcement/manager")
public class AnnouncementController extends BaseController {

    @Resource
    private IAnnouncementManagerService announcementManagerService;

    @ApiOperation(value = "公告列表(分页)", position = 1, httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "current", value = "当前页码", required = true),
            @ApiImplicitParam(paramType = "query", name = "size", value = "分页数量", required = true),
            @ApiImplicitParam(name = "keyword", value = "关键字", paramType = "query"),
            @ApiImplicitParam(name = "delFlag", value = "是否删除", paramType = "query"),
    })
    @GetMapping("/list")
    public R<Page<Announcement>> list(Integer current, Integer size, String keyword, String delFlag) {
        if (StringUtils.isNotBlank(keyword) && keyword.length() > CommonConstants.MAX_NAME_LENGTH) {
            return R.failed("关键字过长");
        }
        if (!StringUtils.isNotBlank(delFlag)) {
            return R.failed("是否删除标记为空");
        }
        return R.ok(announcementManagerService.list(PageUtils.validPage(current, size), keyword, delFlag));
    }


    @ApiOperation(value = "删除或恢复公告", position = 1, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = true),
            @ApiImplicitParam(paramType = "query", name = "delFlag", value = "当前删除状态", required = true),
    })
    @PostMapping("/deleteOrRenew")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public R deleteOrRenew(String id, String delFlag) {
        if (id == null){
            return R.failed("禁用标记或用户id为空");
        }
        return announcementManagerService.deleteOrRenew(id, delFlag);
    }


    @ApiOperation(value = "删除或恢复公告", position = 1, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "公告id", required = true),
            @ApiImplicitParam(paramType = "query", name = "title", value = "公告标题", required = true),
            @ApiImplicitParam(paramType = "query", name = "content", value = "公告内容", required = true),
            @ApiImplicitParam(paramType = "query", name = "type", value = "公告类型", required = true),
    })
    @PostMapping("/edit")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public R edit(String id, String title, String content, String type) {
        if (StringUtils.isAllEmpty(title, content, type)){
            return R.failed("有必填项为空");
        }
        return announcementManagerService.edit(id, title, content, type);
    }
}

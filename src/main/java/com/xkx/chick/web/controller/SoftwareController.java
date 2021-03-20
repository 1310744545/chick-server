package com.xkx.chick.web.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xkx.chick.common.base.R;
import com.xkx.chick.common.constant.CommonConstants;
import com.xkx.chick.common.controller.BaseController;
import com.xkx.chick.common.util.PageUtils;
import com.xkx.chick.common.util.StringUtils;
import com.xkx.chick.web.pojo.entity.Software;
import com.xkx.chick.web.pojo.vo.SoftwareContentDetailVO;
import com.xkx.chick.web.pojo.vo.SoftwareDetailVO;
import com.xkx.chick.web.service.ISoftwareService;
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
 * <p>
 * 软件表 前端控制器
 * </p>
 *
 * @author 肖可欣
 * @since 2021-02-28
 */
@RestController
@RequestMapping("/chick/software")
public class SoftwareController extends BaseController {
    @Resource
    private ISoftwareService softwareService;

    @ApiOperation(value = "软件(分页)", position = 1, httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "current", value = "当前页码", required = true),
            @ApiImplicitParam(paramType = "query", name = "size", value = "分页数量", required = true),
            @ApiImplicitParam(name = "keyword", value = "关键字", paramType = "query"),
            @ApiImplicitParam(name = "delFlag", value = "是否删除", paramType = "query"),
    })
    @PostMapping("/list")
    @PreAuthorize(CommonConstants.HAS_ROLE_ADMIN)
    public R<Page<Software>> list(Integer current, Integer size, String keyword, String delFlag) {
        if (StringUtils.isNotBlank(keyword) && keyword.length() > CommonConstants.MAX_NAME_LENGTH) {
            return R.failed("关键字过长");
        }
        if (!StringUtils.isNotBlank(delFlag)) {
            return R.failed("是否删除标记为空");
        }
        return R.ok(softwareService.list(PageUtils.validPage(current, size), keyword, delFlag));
    }

    @ApiOperation(value = "更新或添加软件", position = 1, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "软件id", required = true),
            @ApiImplicitParam(paramType = "query", name = "name", value = "软件名", required = true),
            @ApiImplicitParam(paramType = "query", name = "company", value = "开发商", required = true),
            @ApiImplicitParam(paramType = "query", name = "description", value = "描述", required = true),
    })
    @PostMapping("/editSoftware")
    @PreAuthorize(CommonConstants.HAS_ROLE_ADMIN)
    public R editSoftware(String id, String name, String company, String description) {
        if (StringUtils.isAllEmpty(name)){
            return R.failed("软件名为空");
        }
        return softwareService.editSoftware(id, name, company, description);
    }

    @ApiOperation(value = "删除或恢复工具", position = 1, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "toolId", value = "文件id", required = true),
            @ApiImplicitParam(paramType = "query", name = "delFlag", value = "当前删除状态", required = true),
    })
    @PostMapping("/deleteOrRenew")
    @PreAuthorize(CommonConstants.HAS_ROLE_ADMIN)
    public R deleteOrRenew(String softwareId, String delFlag) {
        if (softwareId == null){
            return R.failed("删除标记或文件id为空");
        }
        return softwareService.deleteOrRenew(softwareId, delFlag);
    }

    @ApiOperation(value = "软件下载前台页(分页)", position = 1, httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "current", value = "当前页码", required = true),
            @ApiImplicitParam(paramType = "query", name = "size", value = "分页数量", required = true),
            @ApiImplicitParam(name = "keyword", value = "关键字", paramType = "query"),
            @ApiImplicitParam(name = "delFlag", value = "是否删除", paramType = "query"),
    })
    @GetMapping("/softwareAndContentList")
    public R<Page<SoftwareDetailVO>> softwareAndContentList(Integer current, Integer size, String keyword, String delFlag) {
        if (StringUtils.isNotBlank(keyword) && keyword.length() > CommonConstants.MAX_NAME_LENGTH) {
            return R.failed("关键字过长");
        }
        if (!StringUtils.isNotBlank(delFlag)) {
            return R.failed("是否删除标记为空");
        }
        return R.ok(softwareService.softwareAndContentList(PageUtils.validPage(current, size), keyword, delFlag));
    }

    @ApiOperation(value = "软件所有内容", position = 1, httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "current", value = "softwareId", required = true),
    })
    @GetMapping("/softwareAllContentList")
    public R<SoftwareContentDetailVO> softwareAllContentList(String softwareId) {
        if (!StringUtils.isNotBlank(softwareId)) {
            return R.failed("软件id为空");
        }
        return R.ok(softwareService.softwareAllContentList(softwareId));
    }
}

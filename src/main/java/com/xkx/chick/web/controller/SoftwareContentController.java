package com.xkx.chick.web.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xkx.chick.common.base.R;
import com.xkx.chick.common.constant.CommonConstants;
import com.xkx.chick.common.controller.BaseController;
import com.xkx.chick.common.util.PageUtils;
import com.xkx.chick.common.util.StringUtils;
import com.xkx.chick.web.pojo.vo.ToolsVO;
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
 * 软件内容 前端控制器
 * </p>
 *
 * @author 肖可欣
 * @since 2021-03-13
 */
@RestController
@RequestMapping("/chick/softwareContent")
public class SoftwareContentController extends BaseController {

    @Resource
    private ISoftwareService softwareService;

    @ApiOperation(value = "工具列表(分页)", position = 1, httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "current", value = "当前页码", required = true),
            @ApiImplicitParam(paramType = "query", name = "size", value = "分页数量", required = true),
            @ApiImplicitParam(name = "keyword", value = "关键字", paramType = "query"),
            @ApiImplicitParam(name = "delFlag", value = "是否删除", paramType = "query"),
    })
    @GetMapping("/list")
    public R<Page<ToolsVO>> list(Integer current, Integer size, String keyword, String delFlag) {
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
    })
    @PostMapping("/editSoftware")
    @PreAuthorize(CommonConstants.HAS_ROLE_ADMIN)
    public R editSoftware(String id, String name, String company) {
        if (StringUtils.isAllEmpty(name, company)){
            return R.failed("有必填项为空");
        }
        return softwareService.editSoftware(id, name, company);
    }
}

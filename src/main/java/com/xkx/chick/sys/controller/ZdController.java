package com.xkx.chick.sys.controller;


import com.xkx.chick.common.base.R;
import com.xkx.chick.common.controller.BaseController;
import com.xkx.chick.common.util.StringUtils;
import com.xkx.chick.sys.pojo.entity.Zdx;
import com.xkx.chick.sys.service.IZdService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 字典 前端控制器
 * </p>
 *
 * @author 肖可欣
 * @since 2021-02-28
 */
@RestController
@RequestMapping("/chick/sysZd")
public class ZdController extends BaseController {
    @Autowired
    IZdService zdService;

    @ApiOperation(value = "获取字典项", position = 1, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "current", value = "zdName", required = true),
    })
    @PostMapping("/getZdxByZdName")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public R<List<Zdx>> getZdxByZdName(String zdName) {
        if (!StringUtils.isNotBlank(zdName)) {
            return R.failed("是否删除标记为空");
        }
        return R.ok(zdService.getZdxByZdName(zdName));
    }

    @ApiOperation(value = "获取字典项", position = 1, httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "current", value = "zdName", required = true),
    })
    @GetMapping("/getZdx")
    public R<List<Zdx>> getZdx(String zdName) {
        if (!StringUtils.isNotBlank(zdName)) {
            return R.failed("是否删除标记为空");
        }
        return R.ok(zdService.getZdxByZdName(zdName));
    }
}

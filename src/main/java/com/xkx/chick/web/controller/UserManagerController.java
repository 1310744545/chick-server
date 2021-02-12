package com.xkx.chick.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xkx.chick.common.base.R;
import com.xkx.chick.common.constant.CommonConstants;
import com.xkx.chick.common.util.PageUtils;
import com.xkx.chick.sys.pojo.entity.User;
import com.xkx.chick.web.service.IUserManagerService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName UserManagerController
 * @Author xiaokexin
 * @Date 2021/2/12 19:49
 * @Description UserManagerController
 * @Version 1.0
 */
@RestController
@RequestMapping("/user/manager")
public class UserManagerController {
    @Autowired
    private IUserManagerService userManagerService;


    @ApiOperation(value = "管理用户列表(分页)", position = 1, httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "current", value = "当前页码", required = true),
            @ApiImplicitParam(paramType = "query", name = "size", value = "分页数量", required = true),
            @ApiImplicitParam(name = "keyword", value = "关键字", paramType = "query"),
            @ApiImplicitParam(name = "delFlag", value = "是否删除", paramType = "query"),
    })
    @GetMapping("/list")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public R<Page<User>> list(Integer current, Integer size, String keyword, String delFlag) {
        if (StringUtils.isNotBlank(keyword) && keyword.length() > CommonConstants.MAX_NAME_LENGTH) {
            return R.failed("关键字过长");
        }
        return R.ok(userManagerService.list(PageUtils.validPage(current, size), keyword, delFlag));
    }
}

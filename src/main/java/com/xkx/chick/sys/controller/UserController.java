package com.xkx.chick.sys.controller;


import com.xkx.chick.common.base.R;
import com.xkx.chick.sys.service.IUserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author 肖可欣
 * @since 2021-01-21
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @ApiOperation(value = "登录", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, paramType = "query"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "query"),
    })
    @PostMapping("/login")
    public R login(String username, String password) {
        if (StringUtils.isAnyBlank(username, password)) {
            return R.failed("用户名和密码不能为空");
        }
        return userService.login(username, password);
    }

    @ApiOperation(value = "退出登录", httpMethod = "GET")
    @GetMapping("/logout")
    public R logout() {
        return userService.logout();
    }

}

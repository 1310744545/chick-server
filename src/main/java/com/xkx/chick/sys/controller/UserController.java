package com.xkx.chick.sys.controller;


import com.google.code.kaptcha.Constants;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.xkx.chick.common.base.R;
import com.xkx.chick.common.controller.BaseController;
import com.xkx.chick.sys.pojo.dto.LoginUserDTO;
import com.xkx.chick.sys.service.IUserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

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
public class UserController extends BaseController {
    @Autowired
    private IUserService userService;
    @Autowired
    DefaultKaptcha defaultKaptcha;

    @ApiOperation(value = "登录", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginUserDTO", value = "登录用户主体", required = true, paramType = "query"),
            @ApiImplicitParam(name = "request", value = "请求体", required = true, paramType = "query"),
    })
    @PostMapping("/login")
    public R login(@Valid @RequestBody LoginUserDTO loginUserDTO, HttpServletRequest request) {
        if (StringUtils.isAnyBlank(loginUserDTO.getUsername(), loginUserDTO.getPassword())) {
            return R.failed("用户名和密码不能为空");
        }
        if (StringUtils.isAnyBlank(loginUserDTO.getCode())) {
            return R.failed("验证码不能为空");
        }
        return userService.login(loginUserDTO.getUsername(), loginUserDTO.getPassword(), loginUserDTO.getCode(), request);
    }

    @ApiOperation(value = "验证码", httpMethod = "GET")
    @GetMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        byte[] captcha = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            // 将生成的验证码保存在session中
            String createText = defaultKaptcha.createText();
            request.getSession().setAttribute("rightCode", createText);
            BufferedImage bi = defaultKaptcha.createImage(createText);
            ImageIO.write(bi, "jpg", out);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        captcha = out.toByteArray();
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        ServletOutputStream sout = response.getOutputStream();
        sout.write(captcha);
        sout.flush();
        sout.close();
    }

    @ApiOperation(value = "退出登录", httpMethod = "GET")
    @GetMapping("/logout")
    public R logout() {
        return userService.logout();
    }

}
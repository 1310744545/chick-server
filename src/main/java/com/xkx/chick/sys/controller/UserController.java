package com.xkx.chick.sys.controller;


import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.xkx.chick.common.base.R;
import com.xkx.chick.common.constant.CommonConstants;
import com.xkx.chick.common.controller.BaseController;
import com.xkx.chick.common.util.JwtUtils;
import com.xkx.chick.common.util.SecurityUtils;
import com.xkx.chick.sys.service.IUserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    @Resource
    private IUserService userService;
    @Resource
    DefaultKaptcha defaultKaptcha;
    @Resource
    private JwtUtils jwtUtils;

    /**
     * 登录
     * @param username 用户名
     * @param password 密码
     * @param code 验证码
     * @param request 请求头
     * @return
     */
    @ApiOperation(value = "登录", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, paramType = "query"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "query"),
            @ApiImplicitParam(name = "code", value = "验证码", required = true, paramType = "query"),
    })
    @PostMapping("/login")
    public R login(String username, String password, String code, HttpServletRequest request) {
        if (StringUtils.isAnyBlank(username, password)) {
            return R.failed("用户名和密码不能为空");
        }
        if (StringUtils.isAnyBlank(code)) {
            return R.failed("验证码不能为空");
        }
        return userService.login(username, password, code, request);
    }

    /**
     * 注册
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @ApiOperation(value = "注册", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, paramType = "query"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "query"),
    })
    @PostMapping("/register")
    public R register(String username, String password) {
        if (StringUtils.isAnyBlank(username, password)) {
            return R.failed("用户名和密码不能为空");
        }
        return userService.register(username, password);
    }

    /**
     * 退出登录,暂时没用,后期将token存到redis中时需要用这里先不用
     * @return
     */
    @ApiOperation(value = "退出登录", httpMethod = "GET")
    @GetMapping("/logout")
    public R logout() {
        return userService.logout();
    }

    /**
     * 获取验证码
     * @param request 请求头
     * @param response  响应头
     * @throws IOException
     */
    @ApiOperation(value = "验证码", httpMethod = "GET")
    @GetMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        byte[] captcha;
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

    /**
     * 通过jwt获取用户信息
     * @return
     */
    @ApiOperation(value = "获取用户信息", httpMethod = "POST")
    @PostMapping("/getUserByJwt")
    public R getUserByJwt(HttpServletRequest request) {
        if (StringUtils.isBlank(request.getHeader(jwtUtils.getHeader()))){
            return R.failed("令牌不能为空");
        }
        return R.ok(userService.getUserByJwt(request.getHeader(jwtUtils.getHeader())));
    }

    @ApiOperation(value = "更换头像", position = 1, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "文件类型id", paramType = "query"),
    })
    @PostMapping("/uploadHeadPortrait")
    @PreAuthorize(CommonConstants.HAS_ROLE_ADMIN)
    public R uploadHeadPortrait(@RequestParam(name = "file") MultipartFile file){
        if (ObjectUtils.isEmpty(file)){
            return R.failed("请上传文件");
        }
        return userService.uploadHeadPortrait(file, SecurityUtils.getUserId());
    }

    @ApiOperation(value = "更换头像", position = 1, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "文件类型id", paramType = "query"),
    })
    @PostMapping("/updateUser")
    @PreAuthorize(CommonConstants.HAS_ROLE_ADMIN)
    public R updateUser(String userId, String sex, String phone, String name, String email, String birthday){
        if (StringUtils.isBlank(userId)){
            return R.failed("用户id为空");
        }
        if (!getUserId().equals(userId)){
            return R.failed("无权操作");
        }
        return userService.updateUser(userId, sex, phone, name, email, birthday);
    }
}

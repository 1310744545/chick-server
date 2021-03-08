package com.xkx.chick.sys.controller;


import com.aliyun.oss.OSSClient;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.xkx.chick.common.base.R;
import com.xkx.chick.common.controller.BaseController;
import com.xkx.chick.common.util.JwtUtils;
import com.xkx.chick.sys.service.IUserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;

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
    @ApiOperation(value = "注册", httpMethod = "POST")
    @PostMapping("/getUserByJwt")
    public R getUserByJwt(HttpServletRequest request) {
        if (StringUtils.isBlank(request.getHeader(jwtUtils.getHeader()))){
            return R.failed("令牌不能为空");
        }
        return R.ok(userService.getUserByJwt(request.getHeader(jwtUtils.getHeader())));
    }

    /**
     * 上传文件
     * @param file
     * @throws IOException
     */
    @PostMapping("/upload")
    public void upload(@RequestParam(name = "file") MultipartFile file) throws IOException {
        System.out.println("进来了");
        String endpoint = "oss-cn-beijing.aliyuncs.com";
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录
        // https://ram.console.aliyun.com 创建
        String accessKeyId = "LTAI4GASAoWcBMCDJgYuhcva";
        String accessKeySecret = "0MWmVQ0ek1JEbALQKarDxucYqrvp7J";
        // 创建OSSClient实例
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        // 上传

        ossClient.putObject("chickweb", "headPortrait/"+file.getOriginalFilename(), new ByteArrayInputStream(file.getBytes()));
        // 关闭client
        ossClient.shutdown();
        Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
        String url = ossClient.generatePresignedUrl("chickweb", "headPortrait/"+file.getOriginalFilename(), expiration).toString();
        System.out.println(url);
    }
}

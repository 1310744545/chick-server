package com.xkx.chick.common.security.handle;

import cn.hutool.json.JSONUtil;
import com.xkx.chick.common.base.R;
import com.xkx.chick.common.constant.CommonConstants;
import com.xkx.chick.common.constant.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

/**
 * @ClassName JwtAuthenticationEntryPoint
 * @Description 认证失败处理类
 * @Author 肖可欣
 * @Date 2020/10/25 18:14
 * @Version 1.0
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        //验证为未登陆状态会进入此方法，用户未登录
        System.out.println("用户未登录：" + authException.getMessage());
        response.setStatus(200);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.write(JSONUtil.toJsonStr(R.failed(HttpStatus.UNAUTHORIZED, CommonConstants.UNAUTHORIZED)));
        printWriter.flush();
    }
}

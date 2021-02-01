package com.xkx.chick.common.security.filter;

import com.xkx.chick.common.security.UserInfoDetail;
import com.xkx.chick.common.util.JwtUtils;
import io.jsonwebtoken.Jwt;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import sun.plugin.liveconnect.SecurityContextHelper;


import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName JwtAuthenticationTokenFilter
 * @Author 肖可欣
 * @Descrition 鉴权过滤器
 * @Create 2021-01-23 16:45
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Resource
    JwtUtils jwtUtils;
    @Value("${jwt.head}")
    private String head;


    //每次请求都会执行一次,进行鉴权
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String JwtToken = request.getHeader(jwtUtils.getHeader());
        //token不为空,未过期,以head开头
        if (!StringUtils.isEmpty(JwtToken) && jwtUtils.isTokenExpired(JwtToken) && JwtToken.startsWith(head)){
            //token减去头部分
            String username = jwtUtils.getUsernameFromToken(JwtToken.substring(head.length()));
            //用户名不为空,且还没有进行认证的
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                //获取token中的权限
                List<SimpleGrantedAuthority> authorities = jwtUtils.getUserroleFromToken(JwtToken);
                //创建用户
                UserInfoDetail userInfoDetail = new UserInfoDetail();
                //添加ID用户名和权限
                userInfoDetail.setUserId(jwtUtils.getIDFromToken(JwtToken));
                userInfoDetail.setUserName(username);
                userInfoDetail.setAuthorities(authorities);
                //给使用改令牌的用户进行授权
                UsernamePasswordAuthenticationToken authenticationToken
                        = new UsernamePasswordAuthenticationToken(userInfoDetail, null, authorities);
                //交给SpringSecurity
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}

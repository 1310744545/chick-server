package com.xkx.chick.common.config;

import com.xkx.chick.common.security.filter.JwtAuthenticationTokenFilter;
import com.xkx.chick.common.security.handle.JwtAuthenticationEntryPoint;
import com.xkx.chick.common.security.service.UserDetailServiceImpl;
import com.xkx.chick.sys.pojo.entity.Menu;
import com.xkx.chick.sys.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import java.util.List;

/**
 * @Author 肖可欣
 * @Create 2021-01-20 21:26
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailServiceImpl userDetailService;
    @Autowired
    private IMenuService menuService;
    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;
    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    /**
     * 添加授权账户
     */
    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                //  设置userDetailService
                .userDetailsService(userDetailService)
                //  使用BCrypt进行密码hash
                .passwordEncoder(passwordEncoder());
    }

    /**
     * 配置密码加密编码
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置协议
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        //设置权限
        List<Menu> menuList = menuService.list();
        for (Menu menu : menuList) {
            httpSecurity.authorizeRequests().antMatchers(menu.getPath()).hasAnyAuthority(menu.getPermission());
        }

        httpSecurity
                //关闭session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf().disable()
                //不用登录即可访问的资源,放行
                .authorizeRequests()
                .antMatchers(
                        HttpMethod.GET,
//                        HttpMethod.POST,
                        "/*.html",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/**/*.ico",
                        "/**",
                        "/**/**",
                        "/**/**/**"
                ).permitAll()
                .antMatchers(
                        HttpMethod.POST,
                        "/chick/tools/**"
                ).permitAll()
                .and().authorizeRequests()
//                .antMatchers(HttpMethod.GET,"/**").permitAll()
//                .antMatchers("/user/login","/user/register").anonymous()
                .antMatchers("/user/login", "/user/register").permitAll()
                .and()
                //解决匿名用户访问无权限资源时的异常
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                //解决认证过的用户访问无权限资源时的异常
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler).and()
                .authorizeRequests()
                //其余资源需要鉴权
                .anyRequest().authenticated().and();

        //禁用缓存
        httpSecurity.headers().cacheControl();

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}

package com.xkx.chick.sys.service.impl;


import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xkx.chick.common.base.R;
import com.xkx.chick.common.constant.CommonConstants;
import com.xkx.chick.common.security.UserInfoDetail;
import com.xkx.chick.common.security.service.UserDetailServiceImpl;
import com.xkx.chick.common.util.JwtUtils;
import com.xkx.chick.common.util.SecurityUtils;
import com.xkx.chick.sys.entity.User;
import com.xkx.chick.sys.mapper.UserMapper;
import com.xkx.chick.sys.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author 肖可欣
 * @since 2021-01-21
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    UserDetailServiceImpl userDetailService;
    @Autowired
    private JwtUtils jwtUtils;

    /**
     * 查询是否存在该用户
     * @param username
     * @return user
     */
    @Override
    public User getUserByUserName(String username) {
        User user = baseMapper.selectOne(Wrappers.<User>lambdaQuery()
                .eq(User::getUsername, username)
                .eq(User::getDelFlag, CommonConstants.NO));
        if (ObjectUtils.isNull(user)){
            return null;
        }
        return user;
    }

    /**
     * 查询用户的所有权限
     * @param username
     * @return
     */
    @Override
    public List<String> getPermissionByUserName(String username) {
        return userMapper.getPermissionByUserName(username);
    }

    /**
     * 登录
     *
     * @param username 账号
     * @param password 密码
     */
    @Override
    public R<String> login(String username, String password) {

        //构造token
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);

        //验证用户信息
        try{
            Authentication authentication = authenticationManager.authenticate(upToken);
        }catch (AuthenticationException e){
            return R.failed("用户名或密码不正确");
        }

        //通过自己的UserDetailService获取用户信息
        UserDetails userDetails = userDetailService.loadUserByUsername(username);
        //生成token
        String token = jwtUtils.generateToken((UserInfoDetail) userDetails);

        return R.ok(token);
    }

    /**
     * 退出登录
     */
    @Override
    public R logout() {
        //获取用户登录信息
        String username = SecurityUtils.getUsername();
        //删除Token信息
        if (StringUtils.isNotBlank(username)) {
//            jwtUtils.deleteToken(username);
        }
        return R.ok();
    }
}

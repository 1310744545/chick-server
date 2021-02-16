package com.xkx.chick.sys.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xkx.chick.common.base.R;
import com.xkx.chick.common.constant.CommonConstants;
import com.xkx.chick.common.security.UserInfoDetail;
import com.xkx.chick.common.security.service.UserDetailServiceImpl;
import com.xkx.chick.common.util.JwtUtils;
import com.xkx.chick.common.util.SecurityUtils;
import com.xkx.chick.common.util.StringUtils;
import com.xkx.chick.sys.pojo.entity.Role;
import com.xkx.chick.sys.pojo.entity.User;
import com.xkx.chick.sys.mapper.UserMapper;
import com.xkx.chick.sys.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

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

    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private UserMapper userMapper;
    @Resource
    UserDetailServiceImpl userDetailService;
    @Resource
    private JwtUtils jwtUtils;
    @Value("${jwt.head}")
    private String head;
    @Resource
    private PasswordEncoder passwordEncoder;

    /**
     * 查询是否存在该用户
     *
     * @param username
     * @return user
     */
    @Override
    public User getUserByUserName(String username) {
        User user = baseMapper.selectOne(Wrappers.<User>lambdaQuery()
                .eq(User::getUsername, username)
                .eq(User::getDelFlag, CommonConstants.NO));
        if (ObjectUtils.isNull(user)) {
            return null;
        }
        return user;
    }

    /**
     * 查询用户的所有权限
     *
     * @param username
     * @return
     */
    @Override
    public List<String> getPermissionByUserName(String username) {
        return userMapper.getPermissionByUserName(username);
    }

    /**
     * 根据用户ID获取用户角色
     *
     * @param username 用户名
     * @return 用户角色
     */
    @Override
    public List<Role> getUserRole(String username) {
        return baseMapper.selectUserRole(username);
    }

    /**
     * 登录
     *
     * @param username 账号
     * @param password 密码
     * @param request
     */
    @Override
    public R<HashMap> login(String username, String password, String code, HttpServletRequest request) {
        //校验验证码
        String rightCode = (String) request.getSession().getAttribute("rightCode");
        if (StringUtils.isEmpty(rightCode) || !rightCode.equalsIgnoreCase(code)) {
            return R.failed("验证码输入错误,请重新输入");
        }
        //构造token
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);

        //验证用户信息
        try {
            Authentication authentication = authenticationManager.authenticate(upToken);
        } catch (DisabledException e) {
            return R.failed("账户已被禁用");
        } catch (LockedException e) {
            return R.failed("账户已被锁定");
        } catch (AuthenticationException e) {
            return R.failed("用户名或密码不正确");
        }

        //通过自己的UserDetailService获取用户信息
        UserDetails userDetails = userDetailService.loadUserByUsername(username);
        //生成token
        String token = jwtUtils.generateToken((UserInfoDetail) userDetails);

        HashMap<String, String> tokenCarry = new HashMap<>();
        tokenCarry.put("head", head);
        tokenCarry.put("token", token);
        return R.ok(tokenCarry, "登陆成功");
    }

    @Override
    public R register(String username, String password) {
        List<User> userCount = baseMapper.selectList(Wrappers.<User>lambdaQuery()
                .eq(User::getUsername, username));
        if (userCount.size() > 0){
            return R.failed("该用户名已存在");
        }
        String encode = passwordEncoder.encode(password);
        User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setUsername(username);
        user.setPassword(encode);
        int insert = baseMapper.insert(user);
        if (insert > 0) {
            return R.ok("注册成功");
        } else {
            return R.failed("注册失败,系统错误");
        }
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

    /**
     * 通过用户id获取用户名字
     */
    @Override
    public String getNameByUserId(String userId) {
        return userMapper.getNameByUserId(userId);
    }

    /**
     * 通过jwt令牌获取用户信息
     */
    @Override
    public User getUserByJwt(String JwtToken) {
        String id = jwtUtils.getIDFromToken(JwtToken.split(" ")[1]);
        return baseMapper.selectOne(Wrappers.<User>lambdaQuery()
                .select(User::getUsername, User::getName, User::getBirthday, User::getSex, User::getPhone,
                        User::getEmail, User::getLockFlag, User::getEnabledFlag, User::getLastLoginTime,
                        User::getDelFlag, User::getHeadPortraitUrl)
                .eq(User::getUserId, id));
    }
}

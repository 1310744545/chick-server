package com.xkx.chick.sys.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xkx.chick.common.base.R;
import com.xkx.chick.sys.pojo.entity.Role;
import com.xkx.chick.sys.pojo.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author 肖可欣
 * @since 2021-01-21
 */
public interface IUserService extends IService<User> {

    /**
     * 查询用户是否存在
     * @param username
     * @return
     */
    User getUserByUserName(String username);

    /**
     * 根据用户ID获取用户角色
     *
     * @param username 用户名
     * @return 用户角色
     */
    List<Role> getUserRole(String username);

    /**
     * 查询用户的所有权限
     * @param username
     * @return
     */
    List<String> getPermissionByUserName(String username);

    /**
     * 用户登录
     *
     * @param username 账号
     * @param password 密码
     * @param request
     * @return
     */
    R login(String username, String password, String code, HttpServletRequest request);

    /**
     * 退出登录
     *
     * @return
     */
    R logout();

    /**
     * 根据用户id获取用户名
     * @param userId
     * @return
     */
    String getUserNameByUserId(String userId);

}

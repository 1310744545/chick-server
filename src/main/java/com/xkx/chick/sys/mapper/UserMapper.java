package com.xkx.chick.sys.mapper;

import com.xkx.chick.sys.pojo.entity.Role;
import com.xkx.chick.sys.pojo.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author 肖可欣
 * @since 2021-01-21
 */
@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {

    /**
     * 查询用户的所有权限
     * @param username
     * @return
     */
    List<String> getPermissionByUserName(String username);

    /**
     * 根据用户ID获取用户角色
     *
     * @param username 用户名
     * @return 用户角色
     */
    List<Role> selectUserRole(String username);

    /**
     * 根据用户ID获取用户名
     *
     * @param userId 用户ID
     * @return 用户角色
     */
    String getNameByUserId(String userId);
}

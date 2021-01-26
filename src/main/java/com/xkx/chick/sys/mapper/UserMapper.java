package com.xkx.chick.sys.mapper;

import com.xkx.chick.sys.entity.User;
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

}

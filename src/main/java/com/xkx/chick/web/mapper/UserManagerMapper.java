package com.xkx.chick.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xkx.chick.sys.pojo.entity.Role;
import com.xkx.chick.sys.pojo.entity.User;
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
public interface UserManagerMapper extends BaseMapper<User> {

}

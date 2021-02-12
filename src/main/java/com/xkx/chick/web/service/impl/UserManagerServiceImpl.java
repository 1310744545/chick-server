package com.xkx.chick.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xkx.chick.common.util.SecurityUtils;
import com.xkx.chick.common.util.StringUtils;
import com.xkx.chick.sys.mapper.UserMapper;
import com.xkx.chick.sys.pojo.entity.User;
import com.xkx.chick.web.mapper.UserManagerMapper;
import com.xkx.chick.web.service.IUserManagerService;
import org.springframework.stereotype.Service;

/**
 * @ClassName UserManagerServiceImpl
 * @Author xiaokexin
 * @Date 2021/2/12 20:23
 * @Description UserManagerServiceImpl
 * @Version 1.0
 */
@Service
public class UserManagerServiceImpl extends ServiceImpl<UserManagerMapper, User> implements IUserManagerService {
    @Override
    public Page<User> list(Page<User> validPage, String keyword, String delFlag) {
        System.out.println(SecurityUtils.getAuthentication());
        //1.获取当前用户登录ID
        Integer userId = SecurityUtils.getUserId();
        //2.获取分页任务信息
        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery()
                .select(User::getUserId, User::getUsername, User::getName, User::getBirthday, User::getSex,
                        User::getPhone, User::getEmail, User::getLockFlag, User::getEnabledFlag, User::getLastLoginTime,
                        User::getCreateDate, User::getDelFlag, User::getHeadPortraitUrl)
                .ne(User::getUserId, userId)
                .eq(User::getDelFlag, delFlag)
                .orderByDesc(User::getCreateDate);
        if (StringUtils.isNotBlank(keyword)) {
            wrapper.and(wr -> wr.like(User::getName, keyword)
                    .or().like(User::getUsername, keyword)
                    .or().like(User::getEmail, keyword)
                    .or().like(User::getPhone, keyword));
        }
        Page<User> userList = baseMapper.selectPage(validPage, wrapper);
        return userList;
    }
}

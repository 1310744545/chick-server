package com.xkx.chick.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xkx.chick.common.base.R;
import com.xkx.chick.common.constant.CommonConstants;
import com.xkx.chick.common.util.SecurityUtils;
import com.xkx.chick.common.util.StringUtils;
import com.xkx.chick.sys.pojo.entity.User;
import com.xkx.chick.sys.mapper.UserManagerMapper;
import com.xkx.chick.sys.service.IUserManagerService;
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
        //3.添加关键字
        if (StringUtils.isNotBlank(keyword)) {
            wrapper.and(wr -> wr.like(User::getName, keyword)
                    .or().like(User::getUsername, keyword)
                    .or().like(User::getEmail, keyword)
                    .or().like(User::getPhone, keyword));
        }
        return baseMapper.selectPage(validPage, wrapper);
    }

    @Override
    public R luckOrUnlock(String userId, String lockFlag) {
        int update = baseMapper.update(null, Wrappers.<User>lambdaUpdate()
                .eq(User::getUserId, userId)
                .set(User::getLockFlag, CommonConstants.LOCK_FLAG.equals(lockFlag) ? CommonConstants.UNLOCK_FLAG:CommonConstants.LOCK_FLAG));

        if (update > 0 && CommonConstants.LOCK_FLAG.equals(lockFlag)){
            return R.ok("锁定成功");
        }else if (update > 0 && CommonConstants.UNLOCK_FLAG.equals(lockFlag)){
            return R.ok("解锁成功");
        }else {
            return R.failed("系统错误,请联系站长");
        }
    }

    @Override
    public R enabledOrUnEnabled(String userId, String enabledFlag) {
        int update = baseMapper.update(null, Wrappers.<User>lambdaUpdate()
                .eq(User::getUserId, userId)
                .set(User::getEnabledFlag, CommonConstants.ENABLED_FLAG.equals(enabledFlag) ? CommonConstants.UN_ENABLED_FLAG:CommonConstants.ENABLED_FLAG));

        if (update > 0 && CommonConstants.ENABLED_FLAG.equals(enabledFlag)){
            return R.ok("禁用成功");
        }else if (update > 0 && CommonConstants.UN_ENABLED_FLAG.equals(enabledFlag)){
            return R.ok("解禁成功");
        }else {
            return R.failed("系统错误,请联系站长");
        }
    }


    @Override
    public R deleteOrRenew(String userId, String delFlag) {
        int update = baseMapper.update(null, Wrappers.<User>lambdaUpdate()
                .eq(User::getUserId, userId)
                .set(User::getDelFlag, CommonConstants.DELETE_FLAG.equals(delFlag) ? CommonConstants.UN_DELETE_FLAG:CommonConstants.DELETE_FLAG));

        if (update > 0 && CommonConstants.DELETE_FLAG.equals(delFlag)){
            return R.ok("删除成功");
        }else if (update > 0 && CommonConstants.UN_DELETE_FLAG.equals(delFlag)){
            return R.ok("恢复成功");
        }else {
            return R.failed("系统错误,请联系站长");
        }
    }
}

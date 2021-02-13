package com.xkx.chick.web.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xkx.chick.common.base.R;
import com.xkx.chick.sys.pojo.entity.User;

/**
 * @ClassName IUserManagerService
 * @Author xiaokexin
 * @Date 2021/2/12 19:53
 * @Description IUserManagerService
 * @Version 1.0
 */
public interface IUserManagerService extends IService<User> {
    /**
     * 获取用户列表
     *
     * @param validPage 分页信息
     * @param keyword   关键字
     * @return 分页列表
     */
    Page<User> list(Page<User> validPage, String keyword, String delFlag);

    /**
     * 锁定或解锁
     *
     * @param userId 用户id
     * @param lockFlag 锁定标记
     * @return R
     */
    R luckOrUnlock(Integer userId, String lockFlag);

    /**
     * 禁用与解禁
     *
     * @param userId 用户id
     * @param enabledFlag 禁用标记
     * @return R
     */
    R enabledOrUnEnabled(Integer userId, String enabledFlag);

}

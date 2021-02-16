package com.xkx.chick.sys.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xkx.chick.common.base.R;
import com.xkx.chick.sys.pojo.entity.Announcement;
import com.xkx.chick.sys.pojo.entity.User;

/**
 * @ClassName IAnnouncementManagerService
 * @Author xiaokexin
 * @Date 2021/2/15 13:51
 * @Description IAnnouncementManagerService
 * @Version 1.0
 */
public interface IAnnouncementManagerService extends IService<Announcement> {

    /**
     * 获取公告列表
     *
     * @param validPage 分页信息
     * @param keyword   关键字
     * @param delFlag   删除标记
     * @return 分页列表
     */
    Page<Announcement> list(Page<Announcement> validPage, String keyword, String delFlag);

    /**
     * 删除或恢复公告
     *
     * @param id 公告
     * @return R
     */
    R deleteOrRenew(String id, String delFlag);

    /**
     * 更新或添加公告
     *
     * @param id 公告
     * @return R
     */
    R edit(String id, String title, String content, String type);
}

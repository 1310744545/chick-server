package com.xkx.chick.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xkx.chick.common.base.R;
import com.xkx.chick.common.constant.CommonConstants;
import com.xkx.chick.common.util.StringUtils;
import com.xkx.chick.sys.mapper.AnnouncementManager;
import com.xkx.chick.sys.pojo.entity.Announcement;
import com.xkx.chick.sys.pojo.entity.User;
import com.xkx.chick.sys.service.IAnnouncementManagerService;
import com.xkx.chick.sys.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * @ClassName AnnouncementManagerImpl
 * @Author xiaokexin
 * @Date 2021/2/15 13:52
 * @Description AnnouncementManagerImpl
 * @Version 1.0
 */
@Service
public class AnnouncementManagerImpl extends ServiceImpl<AnnouncementManager, Announcement> implements IAnnouncementManagerService {

    @Resource
    IUserService userService;

    @Override
    public Page<Announcement> list(Page<Announcement> validPage, String keyword, String delFlag) {
        //1.获取分页任务信息
        LambdaQueryWrapper<Announcement> wrapper = Wrappers.<Announcement>lambdaQuery()
                .eq(Announcement::getDelFlag, delFlag)
                .orderByDesc(Announcement::getCreateDate);
        //3.添加关键字
        if (StringUtils.isNotBlank(keyword)) {
            wrapper.and(wr -> wr.like(Announcement::getTitle, keyword));
        }
        Page<Announcement> announcementPage = baseMapper.selectPage(validPage, wrapper);
        List<Announcement> list = announcementPage.getRecords();
        ArrayList<Announcement> newList = new ArrayList<>();
        for (Announcement announcement : list){
            announcement.setCreateBy(userService.getNameByUserId(announcement.getCreateBy()));
            newList.add(announcement);
        }
        return announcementPage.setRecords(newList);
    }

    @Override
    public R deleteOrRenew(String id, String delFlag) {
        int update = baseMapper.update(null, Wrappers.<Announcement>lambdaUpdate()
                .eq(Announcement::getId, id)
                .set(Announcement::getDelFlag, CommonConstants.DELETE_FLAG.equals(delFlag) ? CommonConstants.UN_DELETE_FLAG:CommonConstants.DELETE_FLAG));

        if (update > 0 && CommonConstants.DELETE_FLAG.equals(delFlag)){
            return R.ok("删除成功");
        }else if (update > 0 && CommonConstants.UN_DELETE_FLAG.equals(delFlag)){
            return R.ok("恢复成功");
        }else {
            return R.failed("系统错误,请联系站长");
        }
    }

    @Override
    public R edit(String id, String title, String content, String type) {
        Announcement announcement = new Announcement(title, content, type);
        if (StringUtils.isEmpty(id)){
            announcement.setId(UUID.randomUUID().toString());
            if (baseMapper.insert(announcement) > 0){
                return R.ok("新增成功");
            }else {
                return R.ok("系统错误,新增失败");
            }
        }else {
            announcement.setId(id);
            if (baseMapper.updateById(announcement) > 0){
                return R.ok("更新成功");
            }else {
                return R.ok("系统错误,更新失败");
            }
        }
    }
}

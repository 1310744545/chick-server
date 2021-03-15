package com.xkx.chick.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xkx.chick.common.base.R;
import com.xkx.chick.common.constant.CommonConstants;
import com.xkx.chick.common.util.StringUtils;
import com.xkx.chick.web.mapper.SoftwareMapper;
import com.xkx.chick.web.pojo.entity.Software;
import com.xkx.chick.web.service.ISoftwareService;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * <p>
 * 软件表 服务实现类
 * </p>
 *
 * @author 肖可欣
 * @since 2021-02-28
 */
@Service
public class SoftwareServiceImpl extends ServiceImpl<SoftwareMapper, Software> implements ISoftwareService {
    /**
     * 获取软件列表
     *
     * @param validPage 分页
     * @param keyword   关键字
     * @param delFlag   删除标记
     * @return R
     */
    @Override
    public Page<Software> list(Page<Software> validPage, String keyword, String delFlag) {
        LambdaQueryWrapper<Software> wrapper = Wrappers.<Software>lambdaQuery()
                .eq(Software::getDelFlag, delFlag)
                .orderByAsc(Software::getCreateDate);
        if (StringUtils.isNotBlank(keyword)) {
            wrapper.and(wr -> wr.like(Software::getName, keyword));
        }
        return baseMapper.selectPage(validPage, wrapper);
    }

    /**
     * 删除或恢复软件
     *
     * @param softwareId 软件id
     * @return R
     */
    @Override
    public R deleteOrRenew(String softwareId, String delFlag) {
        int update = baseMapper.update(null, Wrappers.<Software>lambdaUpdate()
                .eq(Software::getId, softwareId)
                .set(Software::getDelFlag, CommonConstants.DELETE_FLAG.equals(delFlag) ? CommonConstants.UN_DELETE_FLAG:CommonConstants.DELETE_FLAG));
        if (update > 0 && CommonConstants.DELETE_FLAG.equals(delFlag)){
            return R.ok("删除成功");
        }else if (update > 0 && CommonConstants.UN_DELETE_FLAG.equals(delFlag)){
            return R.ok("恢复成功");
        }else {
            return R.failed("系统错误,请联系站长");
        }
    }

    /**
     * 更新或添加软件
     *
     * @param id 公告
     * @return R
     */
    @Override
    public R editSoftware(String id, String name, String company, String description) {
        Software software = new Software(name, company, description);
        if (StringUtils.isEmpty(id)) {
            software.setId(UUID.randomUUID().toString());
            if (baseMapper.insert(software) > 0) {
                return R.ok("新增成功");
            } else {
                return R.ok("系统错误,新增失败");
            }
        } else {
            software.setId(id);
            if (baseMapper.updateById(software) > 0) {
                return R.ok("更新成功");
            } else {
                return R.ok("系统错误,更新失败");
            }
        }
    }
}

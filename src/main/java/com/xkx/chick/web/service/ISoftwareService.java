package com.xkx.chick.web.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xkx.chick.common.base.R;
import com.xkx.chick.web.pojo.entity.Software;
import com.xkx.chick.web.pojo.vo.SoftwareContentDetailVO;
import com.xkx.chick.web.pojo.vo.SoftwareDetailVO;

/**
 * <p>
 * 软件表 服务类
 * </p>
 *
 * @author 肖可欣
 * @since 2021-02-28
 */
public interface ISoftwareService extends IService<Software> {

    /**
     * 获取软件列表
     *
     * @param validPage 分页
     * @param keyword   关键字
     * @param delFlag   删除标记
     * @return R
     */
    Page<Software> list(Page<Software> validPage, String keyword, String delFlag);

    /**
     * 删除或恢复软件
     *
     * @param softwareId 软件id
     * @return R
     */
    R deleteOrRenew(String softwareId, String delFlag);

    /**
     * 更新或添加软件
     *
     * @param id 公告
     * @return R
     */
    R editSoftware(String id, String title, String content, String description);


    /**
     * 获取软件和软件内容
     *
     * @param validPage 分页
     * @param keyword   关键字
     * @param delFlag   删除标记
     * @return R
     */
    Page<SoftwareDetailVO> softwareAndContentList(Page<SoftwareDetailVO> validPage, String keyword, String delFlag);

    /**
     * 获取软件的所有内容
     *
     * @param softwareId 软件id
     * @return R
     */
    SoftwareContentDetailVO softwareAllContentList(String softwareId);
}

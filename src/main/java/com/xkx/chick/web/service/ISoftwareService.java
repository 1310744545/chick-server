package com.xkx.chick.web.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xkx.chick.common.base.R;
import com.xkx.chick.web.pojo.entity.Software;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xkx.chick.web.pojo.vo.ToolsVO;

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
     * @param keyword 关键字
     * @param delFlag 删除标记
     * @return R
     */
    Page<ToolsVO> list(Page<ToolsVO> validPage, String keyword, String delFlag);

    /**
     * 更新或添加软件
     *
     * @param id 公告
     * @return R
     */
    R editSoftware(String id, String title, String content);
}

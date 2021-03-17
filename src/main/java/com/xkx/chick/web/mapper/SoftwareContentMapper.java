package com.xkx.chick.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xkx.chick.web.pojo.entity.SoftwareContent;
import com.xkx.chick.web.pojo.vo.SoftwareContentVO;

/**
 * <p>
 * 软件内容 Mapper 接口
 * </p>
 *
 * @author 肖可欣
 * @since 2021-03-13
 */
public interface SoftwareContentMapper extends BaseMapper<SoftwareContent> {

    Page<SoftwareContentVO> listById(Page<SoftwareContentVO> validPage, String softwareId);

    int realDelete(String softwareContentId);
}

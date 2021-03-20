package com.xkx.chick.web.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xkx.chick.web.pojo.entity.Software;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xkx.chick.web.pojo.vo.SoftwareDetailVO;

/**
 * <p>
 * 软件表 Mapper 接口
 * </p>
 *
 * @author 肖可欣
 * @since 2021-02-28
 */
public interface SoftwareMapper extends BaseMapper<Software> {

    Page<SoftwareDetailVO> softwareAndContentList(Page<SoftwareDetailVO> validPage, String keyword, String delFlag);
}

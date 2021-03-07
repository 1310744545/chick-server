package com.xkx.chick.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xkx.chick.sys.pojo.entity.SysFile;
import com.xkx.chick.sys.pojo.vo.FileVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 肖可欣
 * @since 2021-03-06
 */
public interface FileMapper extends BaseMapper<SysFile> {

    Page<FileVO> getList(Page<SysFile> validPage, @Param("keyword") String keyword, @Param("type")String type, @Param("delFlag")String delFlag);
}

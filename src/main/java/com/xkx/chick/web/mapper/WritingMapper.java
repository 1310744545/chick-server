package com.xkx.chick.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xkx.chick.web.pojo.entity.Writing;
import com.xkx.chick.web.pojo.vo.WritingVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 帖子 Mapper 接口
 * </p>
 *
 * @author 肖可欣
 * @since 2021-02-28
 */
public interface WritingMapper extends BaseMapper<Writing> {

    Page<WritingVO> indexList(Page<WritingVO> validPage, @Param("keyword") String keyword,@Param("delFlag") String delFlag);

    WritingVO getWriteContent(String writingId);
}

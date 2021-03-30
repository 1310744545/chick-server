package com.xkx.chick.web.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xkx.chick.common.base.R;
import com.xkx.chick.web.pojo.entity.Writing;
import com.xkx.chick.web.pojo.vo.WritingVO;

/**
 * <p>
 * 帖子 服务类
 * </p>
 *
 * @author 肖可欣
 * @since 2021-02-28
 */
public interface IWritingService extends IService<Writing> {
    /**
     *  发布文章
     *  @param title 标题
     *  @param type 类型
     *  @param content 内容
     *  @return 字典项列表
     */
    R saveWrite(String title, String type, String content, String userId);

    /**
     *  获取首页文章列表
     *  @param validPage 分页信息
     *  @return 字典项列表
     */
    Page<WritingVO> indexList(Page<WritingVO> validPage, String keyword, String delFlag);

    /**
     *  获取首页文章列表
     *  @param writingId 文章idz
     *  @return 字典项列表
     */
    WritingVO getWriteContent(String writingId);
}

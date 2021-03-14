package com.xkx.chick.web.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xkx.chick.common.domin.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 帖子
 * </p>
 *
 * @author 肖可欣
 * @since 2021-02-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("chick_writing")
public class Writing extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private String id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 评论数
     */
    private String comment;

    /**
     * 点赞数
     */
    private String like;

    /**
     * 阅读数
     */
    private String read;

    /**
     * 分类标签id,以逗号分割
     */
    private String tag;

    /**
     * 是否置顶 0否 1是
     */
    private String top;

    /**
     * 是否推荐 及顺序 0未推荐 123456789排序越小排名越靠前
     */
    private String recommend;

    /**
     * 是否加精 0否 1是
     */
    private String essence;



}

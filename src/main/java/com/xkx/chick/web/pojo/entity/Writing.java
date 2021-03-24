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
    private int commentAmount;

    /**
     * 点赞数
     */
    private int likeAmount;

    /**
     * 阅读数
     */
    private int readAmount;

    /**
     * 收藏数
     */
    private int collectAmount;

    /**
     * 分类标签id,以逗号分割
     */
    private String type;

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

    public Writing() {
    }

    public Writing(String id, String title, String content, String type, String top, String recommend, String essence) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.type = type;
        this.top = top;
        this.recommend = recommend;
        this.essence = essence;
    }
}

package com.xkx.chick.web.pojo.vo;

import com.xkx.chick.common.domin.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName WritingVO
 * @Author xiaokexin
 * @Date 2021/3/25 22:00
 * @Description WritingVO
 * @Version 1.0
 */
@Data
public class WritingVO extends BaseEntity implements Serializable {

    /**
     * id
     */
    private String id;

    /**
     * userId
     */
    private String userId;

    /**
     * username
     */
    private String name;

    /**
     * userHeadPortraitUrl
     */
    private String userHeadPortraitUrl;

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
     * 标签名称
     */
    private String typeName;

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

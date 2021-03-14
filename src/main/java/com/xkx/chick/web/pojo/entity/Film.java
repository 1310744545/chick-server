package com.xkx.chick.web.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xkx.chick.common.domin.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 影视表
 * </p>
 *
 * @author 肖可欣
 * @since 2021-02-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("chick_film")
public class Film extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private String id;

    /**
     * 名字
     */
    private String name;

    /**
     * 封面图片地址
     */
    @TableField("coverUrl")
    private String coverUrl;

    /**
     * 导演
     */
    private String director;

    /**
     * 编剧
     */
    private String scriptwriter;

    /**
     * 主演
     */
    private String protagonist;

    /**
     * 影视类型
     */
    private String type;

    /**
     * 国家
     */
    private String country;

    /**
     * 语言
     */
    private String language;

    /**
     * 首播时间
     */
    private String debut;

    /**
     * 集数或部数
     */
    private String count;

    /**
     * 单集时常
     */
    private String singleSetOfOften;

    /**
     * 又名
     */
    private String otherName;

    /**
     * IMDb编码
     */
    @TableField("IMDB")
    private String imdb;

    /**
     * 评分
     */
    private String score;

    /**
     * 剧情简介
     */
    private String intro;

    /**
     * 标签 关联字典表 以逗号分割
     */
    private String tag;



}

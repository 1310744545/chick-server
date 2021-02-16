package com.xkx.chick.sys.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xkx.chick.common.domin.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @ClassName Announcement
 * @Author xiaokexin
 * @Date 2021/2/15 13:35
 * @Description Announcement
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_announcement")
public class Announcement extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private String id;

    /**
     * 公告内容
     */
    private String content;

    /**
     * 公告标题
     */
    private String title;

    /**
     * 公告类型
     */
    private String type;

    public Announcement(String title, String content, String type) {
        this.title = title;
        this.content = content;
        this.type = type;
    }

    public Announcement() {
    }

    public Announcement(String id, String content, String title, String type) {
        this.id = id;
        this.content = content;
        this.title = title;
        this.type = type;
    }
}

package com.xkx.chick.web.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 消息
 * </p>
 *
 * @author 肖可欣
 * @since 2021-02-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("chick_message")
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 接收消息者
     */
    private String receptionUserId;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 消息类型 0用户消息 1回复我的 2系统通知
     */
    private String type;

    /**
     * 是否逻辑删除 1是0否
     */
    private String delFlag;

    /**
     * 状态 1未读 0已读
     */
    private String status;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createDate;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private LocalDateTime updateDate;


}

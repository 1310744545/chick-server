package com.xkx.chick.web.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xkx.chick.common.domin.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

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
public class Message extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private String id;

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
     * 状态 1未读 0已读
     */
    private String status;




}

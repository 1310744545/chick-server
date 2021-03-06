package com.xkx.chick.sys.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 字典
 * </p>
 *
 * @author 肖可欣
 * @since 2021-02-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_zd")
public class Zd implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 字典id
     */
    private String zdid;

    /**
     * 字典名称
     */
    private String zdmc;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 是否逻辑删除 0否 1是
     */
    private String delFlag;

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

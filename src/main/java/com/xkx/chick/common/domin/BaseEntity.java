package com.xkx.chick.common.domin;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @ClassName BaseEntity
 * @Author xiaokexin
 * @Date 2021/2/5 18:23
 * @Description 基础实体类
 * @Version 1.0
 */

@Data
@Accessors(chain = true)
public class BaseEntity {

    /**
     * '创建者'
     */
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private String createBy;

    /**
     * '创建时间'
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField(value = "create_Date", fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    /**
     * '更新者'
     */
    @TableField(value = "update_by", fill = FieldFill.UPDATE)
    private String updateBy;

    /**
     * '更新时间'
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField(value = "update_Date", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateDate;

    /**
     * 是否逻辑删除 1是0否
     */
    private String delFlag;
}

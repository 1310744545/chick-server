package com.xkx.chick.sys.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xkx.chick.common.domin.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 字典项
 * </p>
 *
 * @author 肖可欣
 * @since 2021-02-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_zdx")
public class Zdx extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private String id;

    /**
     * 字典
     */
    private String zdid;

    /**
     * 字典名称
     */
    private String name;

    /**
     * 数据值
     */
    private String value;

    /**
     * 类型
     */
    private String type;

    /**
     * 描述
     */
    private String description;

    /**
     * 排序（升序）
     */
    private BigDecimal sort;

    /**
     * 父级编号
     */
    private String parentId;

    /**
     * 备注信息
     */
    private String remarks;

}

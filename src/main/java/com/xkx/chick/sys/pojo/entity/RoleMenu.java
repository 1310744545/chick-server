package com.xkx.chick.sys.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 角色菜单配置表
 * </p>
 *
 * @author 肖可欣
 * @since 2021-01-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_role_menu")
public class RoleMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 菜单ID
     */
    private String menuId;


}

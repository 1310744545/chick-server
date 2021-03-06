package com.xkx.chick.web.pojo.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xkx.chick.common.domin.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 软件内容
 * </p>
 *
 * @author 肖可欣
 * @since 2021-03-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("chick_software_content")
public class SoftwareContentVO extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private String id;

    /**
     * 所属文件id
     */
    private String softwareId;

    /**
     * 文件id
     */
    private String fileId;

    /**
     * 系统
     */
    private String system;

    /**
     * 版本号
     */
    private String versions;

    private String fileName;
    private String url;
}

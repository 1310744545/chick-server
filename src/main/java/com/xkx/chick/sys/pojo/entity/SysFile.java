package com.xkx.chick.sys.pojo.entity;

import com.xkx.chick.common.domin.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 
 * </p>
 *
 * @author 肖可欣
 * @since 2021-03-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysFile extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private String id;

    /**
     * md5唯一id
     */
    private String md5key;

    /**
     * 文件名称
     */
    private String name;

    /**
     * 文件类型 字典 附件类型 图片
     */
    private String type;

    /**
     * URL地址
     */
    private String url;

    /**
     * 文件原名
     */
    private String originalFilename;

    /**
     * 附件大小单位K
     */
    private BigDecimal size;

    public SysFile() {
    }

    public SysFile(String id, String md5key, String name, String type, String url, String originalFilename, BigDecimal size) {
        this.id = id;
        this.md5key = md5key;
        this.name = name;
        this.type = type;
        this.url = url;
        this.originalFilename = originalFilename;
        this.size = size;
    }
}

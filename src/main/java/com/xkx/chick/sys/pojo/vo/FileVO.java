package com.xkx.chick.sys.pojo.vo;

import com.xkx.chick.common.domin.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @ClassName FileVO
 * @Author xiaokexin
 * @Date 2021/3/7 18:34
 * @Description FileVO
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class FileVO extends BaseEntity implements Serializable {

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
    private Long size;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 文件类型名称
     */
    private String typeName;

    /**
     * 上传人名字
     */
    private String createName;

}

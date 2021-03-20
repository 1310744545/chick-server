package com.xkx.chick.web.pojo.vo;

import com.xkx.chick.common.domin.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 软件表
 * </p>
 *
 * @author 肖可欣
 * @since 2021-02-28
 */
@Data
public class SoftwareDetailVO extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private String id;
    /**
     * 文件名
     */
    private String name;

    /**
     * 开发商
     */
    private String company;

    /**
     * 软件描述
     */
    private String description;

    private String mostNewWindowsVersions;
    private String mostNewLinuxVersions;
    private String mostNewMacVersions;
    private String windows;
    private String linux;
    private String mac;

}

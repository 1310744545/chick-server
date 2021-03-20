package com.xkx.chick.web.pojo.vo;

import com.xkx.chick.web.pojo.entity.Software;
import lombok.Data;

import java.util.List;

/**
 * @ClassName SoftwareContentDetailVO
 * @Author xiaokexin
 * @Date 2021/3/20 18:40
 * @Description SoftwareContentDetailVO
 * @Version 1.0
 */
@Data
public class SoftwareContentDetailVO {
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

    List<VersionAndContentVO> versionAndContentVOS;

    public SoftwareContentDetailVO() {
    }

    public SoftwareContentDetailVO(Software software) {
        this.id = software.getId();
        this.name = software.getName();
        this.company = software.getCompany();
        this.description = software.getDescription();
    }
}

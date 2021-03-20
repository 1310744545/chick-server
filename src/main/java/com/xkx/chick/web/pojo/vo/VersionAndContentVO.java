package com.xkx.chick.web.pojo.vo;

import lombok.Data;

/**
 * @ClassName VersionAndContentVO
 * @Author xiaokexin
 * @Date 2021/3/20 18:41
 * @Description VersionAndContentVO
 * @Version 1.0
 */
@Data
public class VersionAndContentVO {

    /**
     * 版本号
     */
    private String versions;
    private String windows;
    private String linux;
    private String mac;
}

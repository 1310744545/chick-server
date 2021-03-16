package com.xkx.chick.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xkx.chick.common.base.R;
import com.xkx.chick.web.pojo.entity.SoftwareContent;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 软件内容 服务类
 * </p>
 *
 * @author 肖可欣
 * @since 2021-03-13
 */
public interface ISoftwareContentService extends IService<SoftwareContent> {
    /**
     * 上传软件
     *
     * @param file 文件
     * @return R
     */
    R uploadFile(MultipartFile file, String softwareId, String versions, String system);
}

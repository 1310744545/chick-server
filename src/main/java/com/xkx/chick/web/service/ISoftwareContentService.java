package com.xkx.chick.web.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xkx.chick.common.base.R;
import com.xkx.chick.web.pojo.entity.SoftwareContent;
import com.xkx.chick.web.pojo.vo.SoftwareContentVO;
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

    /**
     * 获取软件内容列表
     *
     * @param validPage 分页
     * @param softwareId 关键字
     * @return R
     */
    Page<SoftwareContentVO> list(Page<SoftwareContentVO> validPage, String softwareId);


    /**
     * 硬删除,都删掉
     *
     * @param softwareContentId 软件内容id
     * @param fileId 软件内容文件id
     * @param url 软件地址
     * @return R
     */
    R deleteAll(String softwareContentId, String fileId, String url);
}

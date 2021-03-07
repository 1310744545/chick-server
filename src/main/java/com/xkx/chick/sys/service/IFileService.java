package com.xkx.chick.sys.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xkx.chick.common.base.R;
import com.xkx.chick.sys.pojo.entity.SysFile;
import com.xkx.chick.sys.pojo.vo.FileVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 肖可欣
 * @since 2021-03-06
 */
public interface IFileService extends IService<SysFile> {
    /**
     * 上传文件
     *
     * @param file 文件
     * @return R
     */
    R managerUploadFile(MultipartFile file, String type, String remarks);

    /**
     * 获取文件列表
     *
     * @param validPage 分页信息
     * @param keyword   关键字
     * @param delFlag   删除标记
     * @return 分页列表
     */
    Page<FileVO> list(Page<SysFile> validPage, String keyword, String type, String delFlag);

    /**
     * 删除或恢复文件
     *
     * @param fileId 文件id
     * @return R
     */
    R deleteOrRenew(String fileId, String delFlag);

    /**
     * 批量删除
     *
     * @param fileIds 文件id
     * @return R
     */
    R batchRemove(String fileIds);


}

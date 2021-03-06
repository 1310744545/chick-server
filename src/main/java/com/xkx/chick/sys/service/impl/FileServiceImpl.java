package com.xkx.chick.sys.service.impl;

import com.aliyun.oss.OSSClient;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xkx.chick.common.base.R;
import com.xkx.chick.common.util.AliyunOSSClientUtil;
import com.xkx.chick.common.util.StringUtils;
import com.xkx.chick.sys.mapper.FileMapper;
import com.xkx.chick.sys.pojo.entity.SysFile;
import com.xkx.chick.sys.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

import static com.xkx.chick.common.constant.OSSClientConstants.BACKET_NAME;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 肖可欣
 * @since 2021-03-06
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, SysFile> implements IFileService {

    @Autowired
    private AliyunOSSClientUtil aliyunOSSClientUtil;

    /**
     * 上传文件
     *
     * @param file 文件
     * @return R
     */
    @Override
    public R uploadFile(MultipartFile file) {
        //初始化OSSClient
        OSSClient ossClient = aliyunOSSClientUtil.getOSSClient();

        String md5key = AliyunOSSClientUtil.uploadObject2OSS(ossClient, file, BACKET_NAME, "files/");
        Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
        String url = ossClient.generatePresignedUrl(BACKET_NAME, "files/" + file.getName(), expiration).toString();
        System.out.println(md5key);
        System.out.println(url);
        SysFile sysfile = new SysFile();
        int insert = baseMapper.insert(sysfile);
        if (insert > 0) {
            return R.ok("上传成功");
        }
        return R.failed();
    }

    /**
     * 获取文件列表
     *
     * @param validPage 分页信息
     * @param keyword   关键字
     * @param delFlag   删除标记
     * @return 分页列表
     */
    @Override
    public Page<SysFile> list(Page<SysFile> validPage, String keyword, String type, String delFlag) {
        //获取分页任务信息
        LambdaQueryWrapper<SysFile> wrapper = Wrappers.<SysFile>lambdaQuery()
                .eq(SysFile::getDelFlag, delFlag)
                .orderByDesc(SysFile::getCreateDate);
        //添加关键字
        if (StringUtils.isNotBlank(keyword)) {
            wrapper.and(wr -> wr.like(SysFile::getName, keyword)
                    .or().like(SysFile::getOriginalFilename, keyword));

        }
        //添加文件类型那个
        if (StringUtils.isNotEmpty(type)){
            wrapper.and(wr -> wr.eq(SysFile::getType, type));
        }
        return baseMapper.selectPage(validPage, wrapper);
    }
}

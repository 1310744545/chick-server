package com.xkx.chick.web.service.impl;

import com.aliyun.oss.OSSClient;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xkx.chick.common.base.R;
import com.xkx.chick.common.constant.CommonConstants;
import com.xkx.chick.common.constant.OSSClientConstants;
import com.xkx.chick.common.util.AliyunOSSClientUtil;
import com.xkx.chick.sys.mapper.FileMapper;
import com.xkx.chick.sys.pojo.entity.SysFile;
import com.xkx.chick.web.constant.ChickConstant;
import com.xkx.chick.web.mapper.SoftwareContentMapper;
import com.xkx.chick.web.mapper.SoftwareMapper;
import com.xkx.chick.web.pojo.entity.Software;
import com.xkx.chick.web.pojo.entity.SoftwareContent;
import com.xkx.chick.web.service.ISoftwareContentService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.UUID;

import static com.xkx.chick.common.constant.OSSClientConstants.BACKET_NAME;

/**
 * <p>
 * 软件内容 服务实现类
 * </p>
 *
 * @author 肖可欣
 * @since 2021-03-13
 */
@Service
public class SoftwareContentServiceImpl extends ServiceImpl<SoftwareContentMapper, SoftwareContent> implements ISoftwareContentService {

    @Resource
    private AliyunOSSClientUtil aliyunOSSClientUtil;
    @Resource
    private SoftwareMapper softwareMapper;
    @Resource
    private FileMapper fileMapper;

    @Override
    public R uploadFile(MultipartFile file, String softwareId, String versions, String system) {

        Software software = softwareMapper.selectById(softwareId);

        //初始化OSSClient
        OSSClient ossClient = aliyunOSSClientUtil.getOSSClient();
        //上传文件,返回唯一key
        String md5key = AliyunOSSClientUtil.uploadObject2OSS(ossClient, file, BACKET_NAME, "software/"+ software.getName()+"/" + system +"/");
        //关闭OSSClient
        ossClient.shutdown();
        //获取地址
        String url  = OSSClientConstants.URL_Head + "software/"+ software.getName()+"/" + system +"/" +file.getOriginalFilename();

        //先通过md5ket去数据库中检查是否已经上传,如果是则不在向数据库中插入
        SysFile sysFile = fileMapper.selectOne(Wrappers.<SysFile>lambdaQuery()
                .eq(SysFile::getMd5key, md5key)
                .eq(SysFile::getDelFlag, CommonConstants.DELETE_FLAG));
        if (ObjectUtils.isEmpty(sysFile)){
            String uuid= UUID.randomUUID().toString();
            sysFile = new SysFile(uuid, md5key, file.getOriginalFilename(), ChickConstant.COMMON_TYPE, url, file.getOriginalFilename(), file.getSize());
            if (fileMapper.insert(sysFile) < 1) {
                return R.failed("上传失败");
            }
        }
        String uuid= UUID.randomUUID().toString();
        SoftwareContent softwareContent = new SoftwareContent(uuid, softwareId, sysFile.getId(), system, versions);
        if (baseMapper.insert(softwareContent) < 1){
            return R.failed("上传失败");
        }
        return R.ok("上传");
    }
}

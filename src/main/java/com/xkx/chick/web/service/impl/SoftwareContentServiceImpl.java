package com.xkx.chick.web.service.impl;

import com.aliyun.oss.OSSClient;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xkx.chick.common.base.R;
import com.xkx.chick.common.util.AliyunOSSClientUtil;
import com.xkx.chick.sys.mapper.FileMapper;
import com.xkx.chick.sys.pojo.entity.SysFile;
import com.xkx.chick.web.constant.ChickConstant;
import com.xkx.chick.web.mapper.SoftwareContentMapper;
import com.xkx.chick.web.mapper.SoftwareMapper;
import com.xkx.chick.web.pojo.entity.Software;
import com.xkx.chick.web.pojo.entity.SoftwareContent;
import com.xkx.chick.web.pojo.vo.SoftwareContentVO;
import com.xkx.chick.web.service.ISoftwareContentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.UUID;

import static com.xkx.chick.common.constant.OSSClientConstants.*;

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
    @Transactional(rollbackFor = Exception.class)
    public R uploadFile(MultipartFile file, String softwareId, String versions, String system) {

        Software software = softwareMapper.selectById(softwareId);

        //初始化OSSClient
        OSSClient ossClient = aliyunOSSClientUtil.getOSSClient();
        //上传文件,返回唯一key
        String md5key = AliyunOSSClientUtil.uploadObject2OSS(ossClient, file, BACKET_NAME, SOFTWARE + software.getName()+"/" + system +"/");
        //关闭OSSClient
        ossClient.shutdown();
        //获取地址
        String url  = URL_HEAD + SOFTWARE+ software.getName()+"/" + system +"/" +file.getOriginalFilename();

        //先通过md5ket去数据库中检查是否已经上传,如果是则不在向数据库中插入
//        SysFile sysFile = fileMapper.selectOne(Wrappers.<SysFile>lambdaQuery()
//                .eq(SysFile::getMd5key, md5key)
//                .eq(SysFile::getDelFlag, CommonConstants.DELETE_FLAG));
//        if (ObjectUtils.isEmpty(sysFile)){
//        }
        String uuid= UUID.randomUUID().toString();
        SysFile sysFile = new SysFile(uuid, md5key, file.getOriginalFilename(), ChickConstant.COMMON_TYPE, url, file.getOriginalFilename(), file.getSize());
        if (fileMapper.insert(sysFile) < 1) {
            return R.failed("上传失败");
        }
        String uuid2= UUID.randomUUID().toString();
        SoftwareContent softwareContent = new SoftwareContent(uuid2, softwareId, sysFile.getId(), system, versions);
        if (baseMapper.insert(softwareContent) < 1){
            return R.failed("上传失败");
        }
        return R.ok("上传");
    }

    /**
     * 获取软件列表
     *
     * @param validPage 分页
     * @param softwareId 关键字
     * @return R
     */
    @Override
    public Page<SoftwareContentVO> list(Page<SoftwareContentVO> validPage, String softwareId) {
        return baseMapper.listById(validPage, softwareId);
    }

    /**
     * 硬删除,都删掉
     *
     * @param softwareContentId 软件内容id
     * @param fileId 软件内容文件id
     * @param url 软件地址
     * @return R
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public R deleteAll(String softwareContentId, String fileId, String url){
        //删除软件内容
        int i = baseMapper.realDelete(softwareContentId);
        if (i < 1){
            return R.failed("删除失败,软件已被删除");
        }

        //删除文件
        int j = fileMapper.realDelete(fileId);
        if (j < 1){
            return R.failed("删除失败,软件已被删除");
        }

        //删除aliyu的文件
        OSSClient ossClient = aliyunOSSClientUtil.getOSSClient();
        ossClient.deleteObject(BACKET_NAME, url.replace(URL_HEAD, ""));
        ossClient.shutdown();
        return R.ok("删除成功");
    }
}

package com.xkx.chick.sys.service.impl;

import com.aliyun.oss.OSSClient;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xkx.chick.common.base.R;
import com.xkx.chick.common.constant.CommonConstants;
import com.xkx.chick.common.constant.OSSClientConstants;
import com.xkx.chick.common.util.AliyunOSSClientUtil;
import com.xkx.chick.common.util.StringUtils;
import com.xkx.chick.sys.mapper.FileMapper;
import com.xkx.chick.sys.mapper.ZdxMapper;
import com.xkx.chick.sys.pojo.entity.SysFile;
import com.xkx.chick.sys.pojo.entity.Zdx;
import com.xkx.chick.sys.pojo.vo.FileVO;
import com.xkx.chick.sys.service.IFileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

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

    @Resource
    private AliyunOSSClientUtil aliyunOSSClientUtil;
    @Resource
    private ZdxMapper zdxMapper;

    /**
     * 上传文件
     *
     * @param file 文件
     * @return R
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R managerUploadFile(MultipartFile file, String type, String remarks) {
        //获取对应类型的存储路径
        Zdx zdx = zdxMapper.selectById(type);
        //初始化OSSClient
        OSSClient ossClient = aliyunOSSClientUtil.getOSSClient();
        //上传文件,返回唯一key
        String md5key = AliyunOSSClientUtil.uploadObject2OSS(ossClient, file, BACKET_NAME, zdx.getValue()+"manager/");
        //关闭OSSClient
        ossClient.shutdown();
        //获取地址
        String url  = OSSClientConstants.URL_HEAD + zdx.getValue()+"manager/" + file.getOriginalFilename();

        //先通过md5ket去数据库中检查是否已经上传,如果是则不在向数据库中插入
        SysFile sysFile = baseMapper.selectOne(Wrappers.<SysFile>lambdaQuery()
                .eq(SysFile::getMd5key, md5key)
                .eq(SysFile::getDelFlag, CommonConstants.DELETE_FLAG));
        if (ObjectUtils.isEmpty(sysFile)){
            String uuid= UUID.randomUUID().toString();
            SysFile sysfile = new SysFile(uuid, md5key, file.getOriginalFilename(), type, url, file.getOriginalFilename(), file.getSize());
            if (StringUtils.isNotEmpty(remarks)){
                sysfile.setRemarks(remarks);
            }
            if (baseMapper.insert(sysfile) > 0) {
                return R.ok("上传成功");
            }
        } else {
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
    public Page<FileVO> list(Page<SysFile> validPage, String keyword, String type, String delFlag) {
        return baseMapper.getList(validPage, keyword, type, delFlag);
    }

    /**
     * 删除或恢复文件
     *
     * @param fileId 文件id
     * @return R
     */
    @Override
    public R deleteOrRenew(String fileId, String delFlag) {
        int update = baseMapper.update(null, Wrappers.<SysFile>lambdaUpdate()
                .eq(SysFile::getId, fileId)
                .set(SysFile::getDelFlag, CommonConstants.DELETE_FLAG.equals(delFlag) ? CommonConstants.UN_DELETE_FLAG:CommonConstants.DELETE_FLAG));

        if (update > 0 && CommonConstants.DELETE_FLAG.equals(delFlag)){
            return R.ok("删除成功");
        }else if (update > 0 && CommonConstants.UN_DELETE_FLAG.equals(delFlag)){
            return R.ok("恢复成功");
        }else {
            return R.failed("系统错误,请联系站长");
        }
    }

    /**
     * 批量删除
     *
     * @param fileIds 文件id
     * @return R
     */
    @Override
    public R batchRemove(String fileIds) {
        int update = baseMapper.update(null, Wrappers.<SysFile>lambdaUpdate()
                .set(SysFile::getDelFlag, CommonConstants.UN_DELETE_FLAG)
                .in(SysFile::getId, fileIds.split(",")));
        if (update > 0){
            return R.ok("删除成功");
        }
        return R.failed("删除失败或已删除");
    }

    @Override
    public List<SysFile> listAllByType(String type) {
        return baseMapper.selectList(Wrappers.<SysFile>lambdaQuery()
            .eq(SysFile::getType, type)
            .eq(SysFile::getDelFlag, CommonConstants.DELETE_FLAG));
    }
}

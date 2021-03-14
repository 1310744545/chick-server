package com.xkx.chick.web.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xkx.chick.common.base.R;
import com.xkx.chick.common.util.StringUtils;
import com.xkx.chick.web.mapper.SoftwareMapper;
import com.xkx.chick.web.pojo.entity.Software;
import com.xkx.chick.web.pojo.vo.ToolsVO;
import com.xkx.chick.web.service.ISoftwareService;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * <p>
 * 软件表 服务实现类
 * </p>
 *
 * @author 肖可欣
 * @since 2021-02-28
 */
@Service
public class SoftwareServiceImpl extends ServiceImpl<SoftwareMapper, Software> implements ISoftwareService {
    /**
     * 获取软件列表
     *
     * @param validPage 分页
     * @param keyword 关键字
     * @param delFlag 删除标记
     * @return R
     */
    @Override
    public Page<ToolsVO> list(Page<ToolsVO> validPage, String keyword, String delFlag) {
        return null;
    }

    /**
     * 更新或添加软件
     *
     * @param id 公告
     * @return R
     */
    @Override
    public R editSoftware(String id, String name, String company) {
        Software software = new Software(name, company);
        if (StringUtils.isEmpty(id)){
            software.setId(UUID.randomUUID().toString());
            if (baseMapper.insert(software) > 0){
                return R.ok("新增成功");
            }else {
                return R.ok("系统错误,新增失败");
            }
        }else {
            software.setId(id);
            if (baseMapper.updateById(software) > 0){
                return R.ok("更新成功");
            }else {
                return R.ok("系统错误,更新失败");
            }
        }
    }
}

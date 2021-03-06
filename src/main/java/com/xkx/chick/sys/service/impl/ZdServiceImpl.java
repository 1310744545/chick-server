package com.xkx.chick.sys.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xkx.chick.common.constant.CommonConstants;
import com.xkx.chick.sys.mapper.ZdMapper;
import com.xkx.chick.sys.mapper.ZdxMapper;
import com.xkx.chick.sys.pojo.entity.Zd;
import com.xkx.chick.sys.pojo.entity.Zdx;
import com.xkx.chick.sys.service.IZdService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 字典 服务实现类
 * </p>
 *
 * @author 肖可欣
 * @since 2021-03-06
 */
@Service
public class ZdServiceImpl extends ServiceImpl<ZdMapper, Zd> implements IZdService {

    @Resource
    ZdMapper zdMapper;
    @Resource
    ZdxMapper zdxMapper;

    @Override
    public List<Zdx> getZdxByZdName(String zdName) {
        //获取id
        String id = zdMapper.selectIdByName(zdName);
        //获取所有字典项
        List<Zdx> zdxList = zdxMapper.selectList(Wrappers.<Zdx>lambdaQuery()
                .eq(Zdx::getZdid, id)
                .eq(Zdx::getDelFlag, CommonConstants.DELETE_FLAG));
        return zdxList;
    }
}

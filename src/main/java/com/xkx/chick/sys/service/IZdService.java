package com.xkx.chick.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xkx.chick.sys.pojo.entity.Zd;
import com.xkx.chick.sys.pojo.entity.Zdx;

import java.util.List;

/**
 * <p>
 * 字典 服务类
 * </p>
 *
 * @author 肖可欣
 * @since 2021-03-06
 */
public interface IZdService extends IService<Zd> {
    /**
     *  通过字典名获取字典项
     *  @param zdName 字典名
     *  @return 字典项列表
     */
    List<Zdx> getZdxByZdName(String zdName);
}

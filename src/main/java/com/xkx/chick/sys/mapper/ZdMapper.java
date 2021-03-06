package com.xkx.chick.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xkx.chick.sys.pojo.entity.Zd;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 字典 Mapper 接口
 * </p>
 *
 * @author 肖可欣
 * @since 2021-02-28
 */
@Repository
@Mapper
public interface ZdMapper extends BaseMapper<Zd> {

    String selectIdByName(String zdmc);
}

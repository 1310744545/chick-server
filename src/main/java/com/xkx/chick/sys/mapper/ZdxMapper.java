package com.xkx.chick.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xkx.chick.sys.pojo.entity.Zdx;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 字典项 Mapper 接口
 * </p>
 *
 * @author 肖可欣
 * @since 2021-02-28
 */
@Repository
@Mapper
public interface ZdxMapper extends BaseMapper<Zdx> {

    List<Zdx> selectByZdId(String zdId);
}

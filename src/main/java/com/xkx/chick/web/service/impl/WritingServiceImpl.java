package com.xkx.chick.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xkx.chick.web.mapper.WritingMapper;
import com.xkx.chick.web.pojo.entity.Writing;
import com.xkx.chick.web.service.IWritingService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 帖子 服务实现类
 * </p>
 *
 * @author 肖可欣
 * @since 2021-02-28
 */
@Service
public class WritingServiceImpl extends ServiceImpl<WritingMapper, Writing> implements IWritingService {

}

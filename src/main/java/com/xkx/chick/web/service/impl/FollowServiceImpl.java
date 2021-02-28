package com.xkx.chick.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xkx.chick.web.mapper.FollowMapper;
import com.xkx.chick.web.pojo.entity.Follow;
import com.xkx.chick.web.service.IFollowService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 关注关系 服务实现类
 * </p>
 *
 * @author 肖可欣
 * @since 2021-02-28
 */
@Service
public class FollowServiceImpl extends ServiceImpl<FollowMapper, Follow> implements IFollowService {

}

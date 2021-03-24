package com.xkx.chick.web.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xkx.chick.common.base.R;
import com.xkx.chick.common.constant.CommonConstants;
import com.xkx.chick.web.mapper.WritingMapper;
import com.xkx.chick.web.pojo.entity.Writing;
import com.xkx.chick.web.service.IWritingService;
import org.springframework.stereotype.Service;

import java.util.UUID;

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

    /**
     *  发布文章
     *  @param title 标题
     *  @param type 类型
     *  @param content 内容
     *  @return 字典项列表
     */
    @Override
    public R saveWrite(String title, String type, String content) {
        Writing writing = new Writing(UUID.randomUUID().toString(), title, content, type , CommonConstants.NO, CommonConstants.NO, CommonConstants.NO);
        if (baseMapper.insert(writing) < 1){
            return R.failed("发布失败,请稍后重试");
        }
        return R.ok("发布成功");
    }
}

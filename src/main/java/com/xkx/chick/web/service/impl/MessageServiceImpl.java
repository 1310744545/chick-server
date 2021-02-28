package com.xkx.chick.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xkx.chick.web.mapper.MessageMapper;
import com.xkx.chick.web.pojo.entity.Message;
import com.xkx.chick.web.service.IMessageService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 消息 服务实现类
 * </p>
 *
 * @author 肖可欣
 * @since 2021-02-28
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {

}

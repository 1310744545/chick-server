package com.xkx.chick.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xkx.chick.web.mapper.CommentReplyMapper;
import com.xkx.chick.web.pojo.entity.CommentReply;
import com.xkx.chick.web.service.ICommentReplyService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 评论回复 服务实现类
 * </p>
 *
 * @author 肖可欣
 * @since 2021-02-28
 */
@Service
public class CommentReplyServiceImpl extends ServiceImpl<CommentReplyMapper, CommentReply> implements ICommentReplyService {

}

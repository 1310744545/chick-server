package com.xkx.chick.common.config.handle;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.xkx.chick.common.util.SecurityUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Component
public class MetaHandler implements MetaObjectHandler {

    private static final Logger logger = LoggerFactory.getLogger(MetaHandler.class);

    /**
     * 新增数据执行
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        Integer userId = SecurityUtils.getUserId();
        this.setFieldValByName("createDate", new Date(), metaObject);
        this.setFieldValByName("createBy", userId, metaObject);
        this.setFieldValByName("updateDate", new Date(), metaObject);
        this.setFieldValByName("updateBy", userId, metaObject);
    }

    /**
     * 更新数据执行
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        Integer userId = SecurityUtils.getUserId();
        this.setFieldValByName("updateDate", new Date(), metaObject);
        this.setFieldValByName("updateBy", userId, metaObject);
    }

}
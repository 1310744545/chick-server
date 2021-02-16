package com.xkx.chick.common.constant;

/**
 * @ClassName CommonConstant
 * @Author 肖可欣
 * @Descrition 公共常量
 * @Create 2021-01-21 10:57
 */
public interface CommonConstants {
    /**
     *是否
     */
    String YES = "1";
    String NO = "0";

    /**
     * 成功标记，失败标记
     */
    Integer SUCCESS = 0;
    Integer FAIL = 1;

    /**
     * 用户返回信息
     */
    String ACCESS_IS_DENIED = "权限不足";
    String UNAUTHORIZED = "用户未登录,需登录";

    /**
     * 最大姓名数量
     */
    int MAX_NAME_LENGTH = 50;

    /**
     * 锁定标记
     */
    String LOCK_FLAG = "0";
    String UNLOCK_FLAG = "1";

    /**
     * 禁用标记
     */
    String ENABLED_FLAG = "0";
    String UN_ENABLED_FLAG = "1";

    /**
     * 删除标记
     */
    String DELETE_FLAG = "0";
    String UN_DELETE_FLAG = "1";
}

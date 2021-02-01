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
}
/*
 *
 *      Copyright (c) 2018-2025,  sky  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the sdecloud.com developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *
 *
 */

package com.xkx.chick.common.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xkx.chick.common.constant.CommonConstants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 响应信息主体
 *
 * @param <T>
 * @author sky
 */
@Data
@ApiModel(value = "响应信息主体")
public class R<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "返回标记：成功标记=0，失败标记=1")
    private int code;

    @ApiModelProperty(value = "用户提示信息")
    private String msg;

    @ApiModelProperty(value = "错误信息")
    private String errorMsg;

    @ApiModelProperty(value = "数据")
    private T data;

    public static <T> R<T> ok() {
        return restResult(null, CommonConstants.SUCCESS, "操作成功");
    }

    public static <T> R<T> ok(T data) {
        return restResult(data, CommonConstants.SUCCESS, "操作成功");
    }

    public static <T> R<T> ok(T data, String msg) {
        return restResult(data, CommonConstants.SUCCESS, msg);
    }

    public static <T> R<T> failed() {
        return restResult(null, CommonConstants.FAIL, "操作失败");
    }

    public static <T> R<T> failed(String msg) {
        return restResult(null, CommonConstants.FAIL, msg);
    }

    public static <T> R<T> failed(Integer code ,String message) {
        return restResult(null, code, message);
    }

    private static <T> R<T> restResult(T data, int code, String msg) {
        R<T> apiResult = new R<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }

    @JsonIgnore
    public boolean isSuccess() {
        return code == 0;
    }
}

package com.xkx.chick.web.controller;


import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xkx.chick.common.base.R;
import com.xkx.chick.common.constant.CommonConstants;
import com.xkx.chick.common.controller.BaseController;
import com.xkx.chick.common.util.PageUtils;
import com.xkx.chick.common.util.StringUtils;
import com.xkx.chick.web.pojo.entity.Tools;
import com.xkx.chick.web.service.IToolsService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 在线工具表 前端控制器
 * </p>
 *
 * @author 肖可欣
 * @since 2021-02-28
 */
@RestController
@RequestMapping("/chick/tools")
public class ToolsController extends BaseController{
    @Resource
    private IToolsService iToolsService;


    @ApiOperation(value = "工具列表(分页)", position = 1, httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "current", value = "当前页码", required = true),
            @ApiImplicitParam(paramType = "query", name = "size", value = "分页数量", required = true),
            @ApiImplicitParam(name = "keyword", value = "关键字", paramType = "query"),
            @ApiImplicitParam(name = "delFlag", value = "是否删除", paramType = "query"),
    })
    @GetMapping("/list")
    public R<Page<Tools>> list(Integer current, Integer size, String keyword, String delFlag) {
        if (StringUtils.isNotBlank(keyword) && keyword.length() > CommonConstants.MAX_NAME_LENGTH) {
            return R.failed("关键字过长");
        }
        if (!StringUtils.isNotBlank(delFlag)) {
            return R.failed("是否删除标记为空");
        }
        return R.ok(iToolsService.list(PageUtils.validPage(current, size), keyword, delFlag));
    }


    @ApiOperation(value = "生成UUID", position = 1, httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "count", value = "生成个数", required = true),
    })
    @GetMapping("/generateUUID")
    public R<List<String>> generateUUID(Integer count) {
        if (ObjectUtils.isEmpty(count)) {
            return R.failed("请填写生成个数");
        }
        if (count <= 0) {
            return R.failed("生成个数须大于0");
        }
        return R.ok(iToolsService.generateUUID(count), "生成成功");
    }

    @ApiOperation(value = "生成随机密码", position = 1, httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "count", value = "生成个数", required = true),
            @ApiImplicitParam(paramType = "query", name = "numberCount", value = "生成个数", required = true),
            @ApiImplicitParam(paramType = "query", name = "smallLetter", value = "小写字母", required = true),
            @ApiImplicitParam(paramType = "query", name = "bugLetter", value = "大写字母", required = true),
            @ApiImplicitParam(paramType = "query", name = "number", value = "数字", required = true),
            @ApiImplicitParam(paramType = "query", name = "specialCharacter", value = "特殊字符", required = true),
            @ApiImplicitParam(paramType = "query", name = "rubbishCharacter", value = "去除不易识别字符", required = true),
    })
    @GetMapping("/generateRandomCipher")
    public R<List<String>> generateRandomCipher(Integer count, Integer numberCount, Boolean smallLetter, Boolean bigLetter, Boolean number, Boolean specialCharacter, Boolean rubbishCharacter) {
        if (!smallLetter && !bigLetter && !number && !specialCharacter){
            return R.failed("请至少勾选一种字符");
        }
        if (ObjectUtils.isEmpty(count)) {
            return R.failed("请填写生成个数");
        }
        if (count <= 0) {
            return R.failed("生成个数须大于0");
        }
        if (ObjectUtils.isEmpty(numberCount)) {
            return R.failed("请填写密码位数");
        }
        if (numberCount <= 0) {
            return R.failed("密码位数须大于0");
        }
        return R.ok(iToolsService.generateRandomCipher(count, numberCount, smallLetter, bigLetter, number, specialCharacter, rubbishCharacter), "生成成功");
    }
}

package com.xkx.chick.web.controller;


import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xkx.chick.common.base.R;
import com.xkx.chick.common.constant.CommonConstants;
import com.xkx.chick.common.controller.BaseController;
import com.xkx.chick.common.util.PageUtils;
import com.xkx.chick.common.util.StringUtils;
import com.xkx.chick.web.pojo.vo.ToolsVO;
import com.xkx.chick.web.service.IToolsService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
public class ToolsController extends BaseController {
    @Resource
    private IToolsService iToolsService;

    @ApiOperation(value = "工具列表(分页)", position = 1, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "current", value = "当前页码", required = true),
            @ApiImplicitParam(paramType = "query", name = "size", value = "分页数量", required = true),
            @ApiImplicitParam(name = "keyword", value = "关键字", paramType = "query"),
            @ApiImplicitParam(name = "delFlag", value = "是否删除", paramType = "query"),
    })
    @PostMapping("/list")
    @PreAuthorize(CommonConstants.HAS_ROLE_ADMIN)
    public R<Page<ToolsVO>> list(Integer current, Integer size, String keyword, String delFlag) {
        if (StringUtils.isNotBlank(keyword) && keyword.length() > CommonConstants.MAX_NAME_LENGTH) {
            return R.failed("关键字过长");
        }
        if (!StringUtils.isNotBlank(delFlag)) {
            return R.failed("是否删除标记为空");
        }
        return R.ok(iToolsService.list(PageUtils.validPage(current, size), keyword, delFlag));
    }

    @ApiOperation(value = "删除或恢复工具", position = 1, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "toolId", value = "文件id", required = true),
            @ApiImplicitParam(paramType = "query", name = "delFlag", value = "当前删除状态", required = true),
    })
    @PostMapping("/deleteOrRenew")
    @PreAuthorize(CommonConstants.HAS_ROLE_ADMIN)
    public R deleteOrRenew(String toolId, String delFlag) {
        if (toolId == null){
            return R.failed("删除标记或文件id为空");
        }
        return iToolsService.deleteOrRenew(toolId, delFlag);
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

    @ApiOperation(value = "base64编码/解码", position = 1, httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "code", value = "编码或解码的字符", required = true),
            @ApiImplicitParam(paramType = "query", name = "flag", value = "0编码 1解码", required = true),
    })
    @GetMapping("/base64EncodeOrDecode")
    public R base64EncodeOrDecode(String code, String flag) {
        if (StringUtils.isEmpty(code)){
            return R.failed("请填写编码或解码的字符");
        }
        if (StringUtils.isEmpty(flag)){
            return R.failed("服务器错误");
        }
        return iToolsService.base64EncodeOrDecode(code, flag);
    }

    @ApiOperation(value = "生成二维码", position = 1, httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "textarea", value = "内容", paramType = "query"),
    })
    @GetMapping("/createQRCode")
    public void createQRCode(String textarea, HttpServletRequest request, HttpServletResponse response){
        if (StringUtils.isEmpty(textarea)){
            return;
        }
        iToolsService.createQRCode(textarea, request, response);
    }

    @ApiOperation(value = "下载二维码", position = 1, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "textarea", value = "内容", paramType = "query"),
    })
    @PostMapping("/downloadQRCode")
    public void downloadQRCode(String textarea, HttpServletRequest request, HttpServletResponse response){
        if (StringUtils.isEmpty(textarea)){
            return;
        }
        iToolsService.createQRCode(textarea, request, response);
    }

    @ApiOperation(value = "识别二维码", position = 1, httpMethod = "POST")
    @PostMapping("/distinguishQRCode")
    @PreAuthorize(CommonConstants.HAS_ROLE_ADMIN)
    public R distinguishQRCode(@RequestParam(name = "file") MultipartFile file){
        if (ObjectUtils.isEmpty(file)){
            return R.failed("请上传二维码");
        }
        return iToolsService.distinguishQRCode(file);
    }

}

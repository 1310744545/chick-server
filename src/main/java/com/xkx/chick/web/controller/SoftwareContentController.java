package com.xkx.chick.web.controller;


import com.xkx.chick.common.base.R;
import com.xkx.chick.common.constant.CommonConstants;
import com.xkx.chick.common.controller.BaseController;
import com.xkx.chick.web.service.ISoftwareContentService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * <p>
 * 软件内容 前端控制器
 * </p>
 *
 * @author 肖可欣
 * @since 2021-03-13
 */
@RestController
@RequestMapping("/chick/softwareContent")
public class SoftwareContentController extends BaseController {

    @Resource
    private ISoftwareContentService softwareContentService;

    @ApiOperation(value = "上传软件", position = 1, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "softwareId", value = "文件类型id", paramType = "query"),
            @ApiImplicitParam(name = "versions", value = "备注", paramType = "query"),
            @ApiImplicitParam(name = "system", value = "备注", paramType = "query"),
    })
    @PostMapping("/uploadFile")
    @PreAuthorize(CommonConstants.HAS_ROLE_ADMIN)
    public R uploadFile(@RequestParam(name = "file") MultipartFile file, String softwareId, String versions, String system){
        if (ObjectUtils.isEmpty(file)){
            return R.failed("请上传文件");
        }
        return softwareContentService.uploadFile(file, softwareId, versions, system);
    }
}

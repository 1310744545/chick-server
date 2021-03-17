package com.xkx.chick.web.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xkx.chick.common.base.R;
import com.xkx.chick.common.constant.CommonConstants;
import com.xkx.chick.common.controller.BaseController;
import com.xkx.chick.common.util.PageUtils;
import com.xkx.chick.common.util.StringUtils;
import com.xkx.chick.web.pojo.vo.SoftwareContentVO;
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

    @ApiOperation(value = "软件内容(分页)", position = 1, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "current", value = "当前页码", required = true),
            @ApiImplicitParam(paramType = "query", name = "size", value = "分页数量", required = true),
            @ApiImplicitParam(paramType = "query", name = "softwareId", value = "软件id", required = true),
    })
    @PostMapping("/list")
    @PreAuthorize(CommonConstants.HAS_ROLE_ADMIN)
    public R<Page<SoftwareContentVO>> list(Integer current, Integer size, String softwareId) {
        if (!StringUtils.isNotBlank(softwareId)) {
            return R.failed("软件id为空");
        }
        return R.ok(softwareContentService.list(PageUtils.validPage(current, size), softwareId));
    }

    @ApiOperation(value = "删除软件内容", position = 1, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "softwareContentId", value = "软件内容id", required = true),
            @ApiImplicitParam(paramType = "query", name = "fileId", value = "软件文件id", required = true),
            @ApiImplicitParam(paramType = "query", name = "url", value = "软件地址", required = true),
    })
    @PostMapping("/deleteAll")
    @PreAuthorize(CommonConstants.HAS_ROLE_ADMIN)
    public R deleteAll(String softwareContentId, String fileId, String url) {
        if (!StringUtils.isNotBlank(softwareContentId)) {
            return R.failed("软件内容id为空");
        }
        if (!StringUtils.isNotBlank(fileId)) {
            return R.failed("软件文件id为空");
        }
        if (!StringUtils.isNotBlank(url)) {
            return R.failed("软件地址为空");
        }
        return softwareContentService.deleteAll(softwareContentId, fileId, url);
    }
}

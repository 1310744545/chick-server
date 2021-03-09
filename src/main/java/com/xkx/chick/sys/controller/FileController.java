package com.xkx.chick.sys.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xkx.chick.common.base.R;
import com.xkx.chick.common.constant.CommonConstants;
import com.xkx.chick.common.controller.BaseController;
import com.xkx.chick.common.util.PageUtils;
import com.xkx.chick.common.util.StringUtils;
import com.xkx.chick.sys.pojo.vo.FileVO;
import com.xkx.chick.sys.service.IFileService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 肖可欣
 * @since 2021-03-06
 */
@RestController
@RequestMapping("/chick/File")
public class FileController extends BaseController {

    @Autowired
    IFileService fileService;

    @ApiOperation(value = "管理上传文件", position = 1, httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "文件类型id", paramType = "query"),
            @ApiImplicitParam(name = "remarks", value = "备注", paramType = "query"),
    })
    @PostMapping("/managerUploadFile")
    @PreAuthorize(CommonConstants.HAS_ROLE_ADMIN)
    public R managerUploadFile(@RequestParam(name = "file") MultipartFile file, String type, String remarks){
        if (ObjectUtils.isEmpty(file)){
            return R.failed("请上传文件");
        }
        return fileService.managerUploadFile(file, type, remarks);
    }

    @ApiOperation(value = "文件列表(分页)", position = 1, httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "current", value = "当前页码", required = true),
            @ApiImplicitParam(paramType = "query", name = "size", value = "分页数量", required = true),
            @ApiImplicitParam(name = "keyword", value = "关键字", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "文件类型id", paramType = "query"),
            @ApiImplicitParam(name = "delFlag", value = "是否删除", paramType = "query"),
    })
    @GetMapping("/list")
    @PreAuthorize(CommonConstants.HAS_ROLE_ADMIN)
    public R<Page<FileVO>> list(Integer current, Integer size, String keyword, String type, String delFlag) {
        if (StringUtils.isNotBlank(keyword) && keyword.length() > CommonConstants.MAX_NAME_LENGTH) {
            return R.failed("关键字过长");
        }
        if (!StringUtils.isNotBlank(delFlag)) {
            return R.failed("是否删除标记为空");
        }
        return R.ok(fileService.list(PageUtils.validPage(current, size), keyword, type, delFlag));
    }

    @ApiOperation(value = "删除或恢复文件", position = 1, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "fileId", value = "文件id", required = true),
            @ApiImplicitParam(paramType = "query", name = "delFlag", value = "当前删除状态", required = true),
    })
    @PostMapping("/deleteOrRenew")
    @PreAuthorize(CommonConstants.HAS_ROLE_ADMIN)
    public R deleteOrRenew(String fileId, String delFlag) {
        if (fileId == null){
            return R.failed("删除标记或文件id为空");
        }
        return fileService.deleteOrRenew(fileId, delFlag);
    }

    @ApiOperation(value = "批量删除文件", position = 1, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "fileIds", value = "文件id,以都好分割", required = true),
    })
    @PostMapping("/batchRemove")
    @PreAuthorize(CommonConstants.HAS_ROLE_ADMIN)
    public R batchRemove(String fileIds) {
        if (fileIds == null){
            return R.failed("删除标记或文件id为空");
        }
        return fileService.batchRemove(fileIds);
    }
}

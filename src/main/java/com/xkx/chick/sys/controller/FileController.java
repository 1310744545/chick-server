package com.xkx.chick.sys.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xkx.chick.common.base.R;
import com.xkx.chick.common.constant.CommonConstants;
import com.xkx.chick.common.controller.BaseController;
import com.xkx.chick.common.util.PageUtils;
import com.xkx.chick.common.util.StringUtils;
import com.xkx.chick.sys.pojo.entity.SysFile;
import com.xkx.chick.sys.service.IFileService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @PostMapping("/uploadFile")
    public R uploadFile(MultipartFile file){
        if (ObjectUtils.isEmpty(file)){
            return R.failed("请上传文件");
        }
        return fileService.uploadFile(file);
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public R<Page<SysFile>> list(Integer current, Integer size, String keyword,String type, String delFlag) {
        if (StringUtils.isNotBlank(keyword) && keyword.length() > CommonConstants.MAX_NAME_LENGTH) {
            return R.failed("关键字过长");
        }
        if (!StringUtils.isNotBlank(delFlag)) {
            return R.failed("是否删除标记为空");
        }
        return R.ok(fileService.list(PageUtils.validPage(current, size), keyword, type, delFlag));
    }
}

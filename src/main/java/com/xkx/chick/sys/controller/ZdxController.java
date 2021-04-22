package com.xkx.chick.sys.controller;


import com.xkx.chick.common.base.R;
import com.xkx.chick.common.controller.BaseController;
import com.xkx.chick.sys.pojo.entity.Zdx;
import com.xkx.chick.sys.service.IZdService;
import com.xkx.chick.sys.service.IZdxService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 字典项 前端控制器
 * </p>
 *
 * @author 肖可欣
 * @since 2021-02-28
 */
@RestController
@RequestMapping("/chick/sysZdx")
public class ZdxController extends BaseController {

    @Resource
    private IZdxService zdxService;
    @Resource
    private IZdService zdService;

    /**
     * 获取影视类型
     * @return
     */
    @GetMapping("/getFilmType")
    public R getFilmType(){
        List<Zdx> zdxList = zdService.getZdxByZdName("影视类型");
        return R.ok(zdxList);
    }
}

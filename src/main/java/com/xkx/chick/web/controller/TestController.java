package com.xkx.chick.web.controller;

import com.xkx.chick.common.util.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author 肖可欣
 * @Create 2021-01-20 21:23
 */
@Controller
@ResponseBody
public class TestController {

    @PostMapping("/add")
    public String add(){
        return "增加信息";
    }
    @PostMapping("/remove")
    public String remove(){
        return "删除信息";
    }
    @PostMapping("/update")
    public String update(){
        return "修改信息";
    }
    @GetMapping("/get")
    public String get(){
        return "获取信息";
    }
}

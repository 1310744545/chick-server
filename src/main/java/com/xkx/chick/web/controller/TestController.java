package com.xkx.chick.web.controller;

import com.xkx.chick.common.base.R;
import com.xkx.chick.common.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    RedisUtil redisUtil;

    @PostMapping("/add")
    public R add(){
        return R.ok("这里是add的data","增加信息");
    }
    @PostMapping("/remove")
    public R remove(){
        return R.ok("这里是remove的data","删除信息");
    }
    @PostMapping("/test")
    public R test(){
        return R.ok("这里是test的data","test信息");
    }
    @PostMapping("/update")
    public R update() {
        return R.ok("这里是update的data","修改信息");
    }
    @GetMapping("/get")
    public R get(){
        redisUtil.set("mykey", "myvalue");
        System.out.println(redisUtil.get("mykey"));
        return R.ok("这里是get的data","获取信息");
    }
}

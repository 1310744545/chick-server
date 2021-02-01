package com.xkx.chick.common.controller;

import com.xkx.chick.common.base.R;
import com.xkx.chick.common.security.UserInfoDetail;
import com.xkx.chick.common.util.SecurityUtils;
import com.xkx.chick.sys.pojo.entity.User;
import com.xkx.chick.sys.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

/**
 * @ClassName BaseController
 * @Author xiaokexin
 * @Date 2021/1/31 20:33
 * @Description 基础Controller
 * @Version 1.0
 */
@Controller
public class BaseController {
    @Autowired
    UserServiceImpl userService;

    public UserInfoDetail getUser() {
        return SecurityUtils.getUserInfo();
    }

    public String getUserId() {
        UserInfoDetail userDO = getUser();
        if (userDO == null || userDO.getUserId() == null) {
            return null;
        }
        return userDO.getUserId()+"";
    }

    public String getUsername() {
        return getUser().getUsername();
    }

    public R validate(BindingResult result) {
        if(result.hasErrors()){
            for (ObjectError error : result.getAllErrors()) {
                return  R.failed(error.getDefaultMessage());
            }
        }
        return  R.ok();
    }
}

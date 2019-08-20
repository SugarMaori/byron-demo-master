package com.git.byron.validation.controller;

import com.git.byron.validation.annotation.TypeValid;
import com.git.byron.validation.entity.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: byron
 * @ProjectName: byron-demo-master
 * @Package: com.git.byron.validation.controller
 * @ClassName: UserController
 * @Description:
 * @Date: 2019/8/20 10:16
 * @Version: 1.0
 */
@RestController
@Validated
public class UserController {

    @GetMapping(value = "/user/type")
    public String  get(@Validated User user){
        return "用户类型是：" + user.getType();
    }

    @GetMapping(value = "/user/type2")
    public String  get2( @TypeValid(strType = {"1","2"},message = "不支持此类型") String type){
        return "用户类型是：" + type;
    }
}

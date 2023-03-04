package com.heu.miaosha.controller;

import com.heu.miaosha.controller.viewobject.UserVO;
import com.heu.miaosha.response.CommonReturnType;
import com.heu.miaosha.service.UserService;
import com.heu.miaosha.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: lidong
 * @description
 * @date: 2023/3/4 10:42
 */
@Controller("user")
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;


    @RequestMapping("/get")
    @ResponseBody
    public CommonReturnType getUser(@RequestParam(name = "id")Integer id){
        UserModel userModel = userService.getUserById(id);
        UserVO userVO = converFromModel(userModel);
        userVO = null;
        return CommonReturnType.create(userVO);
    }


    private UserVO converFromModel(UserModel userModel){
        if (userModel == null){
            return  null;
         }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userModel,userVO);
        return userVO;
    }
}

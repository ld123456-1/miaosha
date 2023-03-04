package com.heu.miaosha.controller;

import com.heu.miaosha.controller.viewobject.UserVO;
import com.heu.miaosha.error.BusinessException;
import com.heu.miaosha.error.EmBusinessError;
import com.heu.miaosha.response.CommonReturnType;
import com.heu.miaosha.service.UserService;
import com.heu.miaosha.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: lidong
 * @description
 * @date: 2023/3/4 10:42
 */
@Controller("user")
@RequestMapping("/user")
public class UserController extends BaseController{
    @Autowired
    private UserService userService;


    @RequestMapping("/get")
    @ResponseBody
    public CommonReturnType getUser(@RequestParam(name = "id")Integer id) throws BusinessException {
        UserModel userModel = userService.getUserById(id);
        if (userModel == null){
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }
        UserVO userVO = converFromModel(userModel);

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

package com.heu.miaosha.controller;

import com.alibaba.druid.util.StringUtils;
import com.heu.miaosha.controller.viewobject.UserVO;
import com.heu.miaosha.error.BusinessException;
import com.heu.miaosha.error.EmBusinessError;
import com.heu.miaosha.response.CommonReturnType;
import com.heu.miaosha.service.UserService;
import com.heu.miaosha.service.model.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;



/**
 * @author: lidong
 * @description
 * @date: 2023/3/4 10:42
 */
@Controller("user")
@RequestMapping("/user")
@Slf4j
//跨域请求
@CrossOrigin
public class UserController extends BaseController{
    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest httpServletRequest;


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


    /**
     * 电话登录验证码功能
     * @param telphone
     * @return
     */
    @PostMapping(value = "/getotp",consumes = CONTENT_TYPE_FORMED)
    @ResponseBody
    public CommonReturnType getOtp(@RequestParam(name = "telphone")String telphone){

        // 生成五位随机数当作验证码
        Random random = new Random();
        int randomInt = random.nextInt(99999);
        if (randomInt < 10000){
            randomInt+=10000;
        }

        String otpCode = String.valueOf(randomInt);


        //otp验证码和手机号以键值对形式进行关联
        httpServletRequest.getSession().setAttribute(telphone , otpCode);

        //otp密码在系统端口进行显示
        log.info("telphone = " +telphone + "————————"+"otp = " +otpCode);


        return CommonReturnType.create(null);
    }



    public CommonReturnType register(@RequestParam(name = "telphone")String telphone,
                                     @RequestParam(name =  "otpCode")String otpCode,
                                     @RequestParam(name = "telphone")String name,
                                     @RequestParam(name = "telphone")Byte gender,
                                     @RequestParam(name = "telphone")Integer age,
                                     @RequestParam(name = "password")String password) throws BusinessException {
        //获取存储在session中的短信验证码
        String inSessionOtpCode = (String) this.httpServletRequest.getSession().getAttribute(telphone);
        if (StringUtils.equals(otpCode,inSessionOtpCode)){
            throw  new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"短信验证码不正确");
        }

        //用户注册流程
        UserModel userModel = new UserModel();
        userModel.setName(name);
        userModel.setAge(age);
        userModel.setGender(gender);
        userModel.setTelphone(telphone);
        userModel.setEncrptPassword(MD5Encoder.encode(password.getBytes()));

        userService.register(userModel);
        return CommonReturnType.create(null);
    }
}

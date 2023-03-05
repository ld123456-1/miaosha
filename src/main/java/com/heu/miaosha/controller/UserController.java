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
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
@CrossOrigin(origins = "http://localhost:63342",allowCredentials = "true",allowedHeaders = "*")
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
    @RequestMapping(method = RequestMethod.POST,value = "/getotp",consumes = CONTENT_TYPE_FORMED)
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



    @RequestMapping(value = "/register",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType register(@RequestParam(name = "telphone")String telphone,
                                     @RequestParam(name =  "otpCode")String otpCode,
                                     @RequestParam(name = "name")String name,
                                     @RequestParam(name = "gender")Byte gender,
                                     @RequestParam(name = "age")Integer age,
                                     @RequestParam(name = "password")String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        //获取存储在session中的短信验证码
        String inSessionOtpCode = (String) this.httpServletRequest.getSession().getAttribute(telphone);
        if (!StringUtils.equals(otpCode,inSessionOtpCode)){
            throw  new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"短信验证码不正确");
        }

        //用户注册流程
        UserModel userModel = new UserModel();
        userModel.setName(name);
        userModel.setAge(age);
        userModel.setGender(new Byte(String.valueOf(gender)));
        userModel.setTelphone(telphone);
        userModel.setEncrptPassword(this.EncodeByMd5(password));

        userService.register(userModel);
        return CommonReturnType.create(null);
    }

    public String EncodeByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64Encoder = new BASE64Encoder();
        String newStr = base64Encoder.encode(md5.digest(str.getBytes("utf-8")));
        return newStr;
    }
}

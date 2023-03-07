package com.heu.miaosha.controller;

import com.heu.miaosha.error.BusinessException;
import com.heu.miaosha.error.EmBusinessError;
import com.heu.miaosha.response.CommonReturnType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: lidong
 * @description
 * @date: 2023/3/4 16:40
 */
public class BaseController {
    public static final String CONTENT_TYPE_FORMED = "application/x-www-form-urlencoded";

    /**
     *通用的exceptionhandler,用于解决未被Controller层吸收的异常
     */
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.OK)
//    @ResponseBody
//    public Object handlerException(HttpServletRequest request, Exception ex){
//
//
//        Map<String , Object> responseData = new HashMap<>();
//
//        if (ex instanceof BusinessException){
//            //        //异常类型强转为自定义异常
//            BusinessException businessException = (BusinessException)ex;
//
//
//            responseData.put("errCode",businessException.getErrcode());
//            responseData.put("errMsg" , businessException.getErrMsg());
//
//        }else {
//            responseData.put("errCode", EmBusinessError.UNKNOW_ERROR.getErrcode());
//            responseData.put("errMsg" , EmBusinessError.UNKNOW_ERROR.getErrMsg());
//
//        }
//
//        return CommonReturnType.create(responseData,"fail");
//
//
//
//    }
}

package com.heu.miaosha.controller;

import com.heu.miaosha.error.BusinessException;
import com.heu.miaosha.error.EmBusinessError;
import com.heu.miaosha.response.CommonReturnType;
import com.heu.miaosha.service.OrderService;
import com.heu.miaosha.service.model.OrderModel;
import com.heu.miaosha.service.model.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: lidong
 * @description
 * @date: 2023/3/7 11:33
 */
@Controller
@RequestMapping("/order")
@Slf4j
//跨域请求
@CrossOrigin(origins = "http://localhost:63342",allowCredentials = "true",allowedHeaders = "*")
public class OrderController extends BaseController{
    @Autowired
    private OrderService orderService;


    @Autowired
    private HttpServletRequest httpServletRequest;

    @RequestMapping(method = RequestMethod.POST,value = "/createorder",consumes = CONTENT_TYPE_FORMED)
    @ResponseBody
    public CommonReturnType creatOrder(@RequestParam(name = "itemId")Integer itemId,
                                       @RequestParam(name = "amount")Integer amount) throws BusinessException {

        Boolean isLogin = (Boolean) this.httpServletRequest.getSession().getAttribute("IS_LOGIN");
        if(isLogin == null || !isLogin.booleanValue()){
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN,"用户还未登陆，不能下单");
        }
        UserModel loginUser = (UserModel) httpServletRequest.getSession().getAttribute("LOGIN_USER");


        OrderModel orderModel = orderService.creatOrder(loginUser.getId(), itemId, amount);
        return CommonReturnType.create(null);
    }
}

package com.heu.miaosha.service;

import com.heu.miaosha.error.BusinessException;
import com.heu.miaosha.service.model.OrderModel;

/**
 * @author: lidong
 * @description
 * @date: 2023/3/7 9:43
 */
public interface OrderService {
    OrderModel creatOrder(Integer userId,Integer itemId , Integer amount) throws BusinessException;
}

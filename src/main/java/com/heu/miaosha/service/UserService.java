package com.heu.miaosha.service;

import com.heu.miaosha.error.BusinessException;
import com.heu.miaosha.service.model.UserModel;

/**
 * @author: lidong
 * @description
 * @date: 2023/3/4 10:52
 */
public interface UserService {
    //通过用户id获得用户对象
    UserModel getUserById(Integer id);
    void register(UserModel userModel) throws BusinessException;
}

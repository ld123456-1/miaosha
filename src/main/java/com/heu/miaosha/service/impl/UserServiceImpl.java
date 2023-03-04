package com.heu.miaosha.service.impl;

import com.heu.miaosha.dao.UserDOMapper;
import com.heu.miaosha.dao.UserPasswordDOMapper;
import com.heu.miaosha.dataobject.UserDO;
import com.heu.miaosha.dataobject.UserPasswordDO;
import com.heu.miaosha.service.UserService;
import com.heu.miaosha.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: lidong
 * @description
 * @date: 2023/3/4 10:57
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDOMapper userDOMapper;

    @Autowired
    private UserPasswordDOMapper userPasswordDOMapper;


    @Override
    public UserModel getUserById(Integer id) {
        UserDO userDO = userDOMapper.selectByPrimaryKey(id);
        if (userDO == null){
            return null;
        }
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserID(userDO.getId());

        return convertFromDataObject(userDO,userPasswordDO);

    }




    private UserModel convertFromDataObject(UserDO userDO , UserPasswordDO userPasswordDO ){
        if (userDO == null){
            return null;
        }


        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDO , userModel);
        if (userPasswordDO!=null){
            userModel.setEncrptPassword(userPasswordDO.getEncrptPassword());
        }



        return userModel;
    }
}

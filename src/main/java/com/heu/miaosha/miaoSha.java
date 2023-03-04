package com.heu.miaosha;

import com.heu.miaosha.dao.UserDOMapper;
import com.heu.miaosha.dataobject.UserDO;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: lidong
 * @description
 * @date: 2023/3/3 18:01
 */

@SpringBootApplication(scanBasePackages = {"com.heu.miaosha"})
@RestController
@MapperScan("com.heu.miaosha.dao")
public class miaoSha {

    @Autowired
    private UserDOMapper userDOMapper;




    @RequestMapping("/")
    public String home(){
        UserDO userDO = userDOMapper.selectByPrimaryKey(1);
        if (userDO == null){
            return "对象不存在";
        }else {
            return userDO.getName();
        }
    }

    public static void main(String[] args) {
        System.out.println("helloworld");
        SpringApplication.run(miaoSha.class , args);
    }

}
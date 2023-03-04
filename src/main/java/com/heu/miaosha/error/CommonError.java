package com.heu.miaosha.error;

/**
 * @author: lidong
 * @description
 * @date: 2023/3/4 15:30
 */
public interface CommonError {
    public int getErrcode();
    public String getErrMsg();
    public CommonError setErrMsg(String errMsg);

}

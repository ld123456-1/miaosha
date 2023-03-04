package com.heu.miaosha.error;

/**
 * @author: lidong
 * @description
 * @date: 2023/3/4 15:33
 */
public enum EmBusinessError implements CommonError{
    //0000开头为通用型错误
    PARAMETER_VALIDATION_ERROR(00001,"参数不合法"),



    //1000开头为用户相关错误定义
    USER_NOT_EXIST(10001,"用户不存在")
    ;

    private EmBusinessError(int errCode,String errMsg){
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    private int errCode;
    private String errMsg;

    @Override
    public int getErrcode() {
        return this.errCode;
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }
}

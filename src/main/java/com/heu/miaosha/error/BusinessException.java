package com.heu.miaosha.error;

/**
 * @author: lidong
 * @description
 * @date: 2023/3/4 15:41
 */
//包装器业务异常类处理方法
public class BusinessException extends Exception implements CommonError{

    //实际是调用了emum类
    private CommonError commonError;

    /**
     * 直接接受CommonError的传递参数来构造异常
     * @param commonError
     */
    public BusinessException(CommonError commonError){
        super();
        this.commonError = commonError;
    }

    /**
     * 接受自定义errMsg的方式来构造业务异常
     * @param commonError
     * @param errMsg
     */
    public BusinessException(CommonError commonError , String errMsg){
        super();
        this.commonError = commonError;
        this.commonError.setErrMsg(errMsg);
    }




    @Override
    public int getErrcode() {
        return this.commonError.getErrcode();
    }

    @Override
    public String getErrMsg() {
        return this.commonError.getErrMsg();
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        return this.commonError.setErrMsg(errMsg);
    }
}

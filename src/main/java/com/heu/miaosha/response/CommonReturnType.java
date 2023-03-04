package com.heu.miaosha.response;

/**
 * @author: lidong
 * @description
 * @date: 2023/3/4 12:12
 */
public class CommonReturnType {
    //状态码定义  success or fail
    private String status;

    //具体的返回数据
    private Object data;

    public static  CommonReturnType create(Object result){
        return CommonReturnType.create(result,"success");
    }
    public static  CommonReturnType create(Object result , String status){
        CommonReturnType type = new CommonReturnType();
        type.setData(result);
        type.setStatus(status);
        return type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

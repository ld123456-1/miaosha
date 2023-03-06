package com.heu.miaosha.controller.viewobject;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author: lidong
 * @description
 * @date: 2023/3/6 18:46
 */
@Data
public class ItemVO {
    private Integer id;
    //商品的名称

    private String title;
    //商品的价格

    private BigDecimal price;
    //商品的库存

    private Integer stock;
    //商品的描述

    private String description;
    //商品的价格
    private Integer sales;
    //设置图片地址

    private String imgUrl;
}

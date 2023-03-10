package com.heu.miaosha.service.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author: lidong
 * @description
 * @date: 2023/3/6 16:54
 */
public class ItemModel {
    private Integer id;
    //商品的名称
    @NotBlank(message = "商品名称不能为空")
    private String title;
    //商品的价格
    @NotNull(message = "商品价格不能为空")
    @Min(value = 0,message = "商品价格必须大于0")
    private BigDecimal price;
    //商品的库存
    @NotNull(message = "库存不能为空")
    private Integer stock;
    //商品的描述
    @NotBlank(message = "商品描述信息不能为空")
    private String description;
    //商品的价格
    private Integer sales;
    //设置图片地址
    @NotBlank(message = "商品图片信息不能为空")
    private String imgUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}

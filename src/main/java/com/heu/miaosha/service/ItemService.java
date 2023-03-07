package com.heu.miaosha.service;

import com.heu.miaosha.error.BusinessException;
import com.heu.miaosha.service.model.ItemModel;

import java.util.List;

/**
 * @author: lidong
 * @description
 * @date: 2023/3/6 17:27
 */
public interface ItemService {
    //创建商品对象
    ItemModel createItem(ItemModel itemModel) throws BusinessException;

    //商品列表浏览

    List<ItemModel> listItem();

    //商品详情

    ItemModel getItemById(Integer id);



    boolean decreaseStock(Integer itemID , Integer amount);

    void increseSales(Integer itemId , Integer amount) throws BusinessException;

}

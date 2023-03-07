package com.heu.miaosha.controller;

import com.heu.miaosha.controller.viewobject.ItemVO;
import com.heu.miaosha.dao.ItemDOMapper;
import com.heu.miaosha.error.BusinessException;
import com.heu.miaosha.response.CommonReturnType;
import com.heu.miaosha.service.ItemService;
import com.heu.miaosha.service.model.ItemModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: lidong
 * @description
 * @date: 2023/3/6 18:45
 */
@Controller
@RequestMapping("/item")
@Slf4j
//跨域请求
@CrossOrigin(origins = "http://localhost:63342",allowCredentials = "true",allowedHeaders = "*")
public class ItemController extends BaseController{


    @Autowired
    private ItemService itemService;


    /**
     * 商品添加页面
     * @param title
     * @param description
     * @param price
     * @param stock
     * @param imgUrl
     * @return
     * @throws BusinessException
     */
    @RequestMapping(method = RequestMethod.POST,value = "/create",consumes = CONTENT_TYPE_FORMED)
    @ResponseBody
    public CommonReturnType createItem(@RequestParam(name = "title")String title,
                                       @RequestParam(name = "description")String description,
                                       @RequestParam(name = "price") BigDecimal price,
                                       @RequestParam(name = "stock")Integer stock,
                                       @RequestParam(name = "imgUrl")String imgUrl) throws BusinessException {
        ItemModel itemModel = new ItemModel();
        itemModel.setTitle(title);
        itemModel.setDescription(description);
        itemModel.setPrice(price);
        itemModel.setStock(stock);
        itemModel.setImgUrl(imgUrl);
        ItemModel item = itemService.createItem(itemModel);
        ItemVO itemVO = convertVoFromModel(item);

        return CommonReturnType.create(itemVO);


    }

    @RequestMapping(method = RequestMethod.GET,value = "/get")
    @ResponseBody
    public CommonReturnType getItem(@RequestParam(name = "id")Integer id){
        ItemModel itemById = itemService.getItemById(id);


        log.info(itemById.getTitle());

        return CommonReturnType.create(itemById);
    }


    /**
     * 商品详情的展示
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType listItem() {
        List<ItemModel> itemModelList = itemService.listItem();
        //使用stream api将list内的itemModel转化为ItemVO;
        List<ItemVO> itemVOList = itemModelList.stream().map(itemModel -> {
            ItemVO itemVO = this.convertVoFromModel(itemModel);
            return itemVO;
        }).collect(Collectors.toList());
        return CommonReturnType.create(itemVOList);
    }


    private ItemVO  convertVoFromModel(ItemModel itemModel){
        if (itemModel == null){
            return null;
        }
        ItemVO itemVO = new ItemVO();
        BeanUtils.copyProperties(itemModel , itemVO);
        return itemVO;
    }



}

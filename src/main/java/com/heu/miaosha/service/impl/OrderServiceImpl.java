package com.heu.miaosha.service.impl;

import com.heu.miaosha.dao.OrderDOMapper;
import com.heu.miaosha.dao.SequenceDOMapper;
import com.heu.miaosha.dataobject.OrderDO;
import com.heu.miaosha.dataobject.SequenceDO;
import com.heu.miaosha.error.BusinessException;
import com.heu.miaosha.error.EmBusinessError;
import com.heu.miaosha.response.CommonReturnType;
import com.heu.miaosha.service.ItemService;
import com.heu.miaosha.service.OrderService;
import com.heu.miaosha.service.UserService;
import com.heu.miaosha.service.model.ItemModel;
import com.heu.miaosha.service.model.OrderModel;
import com.heu.miaosha.service.model.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author: lidong
 * @description
 * @date: 2023/3/7 9:43
 */
@Service
@Slf4j
public class OrderServiceImpl  implements OrderService {

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderDOMapper orderDOMapper;

    @Autowired
    private SequenceDOMapper sequenceDOMapper;

    @Override
    @Transactional
    public OrderModel creatOrder(Integer userId, Integer itemId, Integer amount) throws BusinessException {
        //校验下单状态 确定商品存在 用户合法 数量正确
        ItemModel itemModel = itemService.getItemById(itemId);
        if (itemModel == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR , "商品不存在");
        }

        UserModel userModel = userService.getUserById(userId);
        if (userModel == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"用户信息不正确");

        }

        if (amount<=0 || amount>99){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"用户购买数量不正确");

        }

        //落单减库存

        boolean result = itemService.decreaseStock(itemId,amount);
        if (!result){
            throw new BusinessException(EmBusinessError.STOCK_NOT_ENOUGH);
        }


        //订单入库
        OrderModel orderModel = new OrderModel();
        orderModel.setUserId(userId);
        orderModel.setAmount(amount);
        orderModel.setItemId(itemId);
        orderModel.setItemPrice(itemModel.getPrice());
        orderModel.setOrderPrice(itemModel.getPrice().multiply(new BigDecimal(amount)));


        //生成交易订单号
        orderModel.setId(generateOrderNo());
        OrderDO orderDO = convertFromOrderModel(orderModel);
        orderDOMapper.insertSelective(orderDO);


        itemService.increseSales(itemId,amount);
        //返回前端


        return orderModel;
    }

    OrderDO convertFromOrderModel(OrderModel orderModel){
        if (orderModel == null){
            return null;

        }
        OrderDO  orderDO= new OrderDO();
        BeanUtils.copyProperties(orderModel,orderDO);
        orderDO.setItemPrice(orderModel.getItemPrice().doubleValue());
        orderDO.setOrderPrice(orderModel.getOrderPrice().doubleValue());
        return orderDO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    String generateOrderNo(){
        StringBuilder stringBuilder = new StringBuilder();
        //年月日
        LocalDateTime localDateTime = LocalDateTime.now();
        String nowDate = localDateTime.format(DateTimeFormatter.ISO_DATE).replace("-","");

        stringBuilder.append(nowDate);


        //自增
        int sequence =0;
        SequenceDO sequenceDO = sequenceDOMapper.getSequenceByName("order_info");
        sequence = sequenceDO.getCurrentValue();
        sequenceDO.setCurrentValue(sequenceDO.getCurrentValue()+sequenceDO.getStep());
        sequenceDOMapper.updateByPrimaryKeySelective(sequenceDO);
        String sequenceStr = String.valueOf(sequence);
        for (int i = 0 ; i<6-sequenceStr.length() ; i++){
            stringBuilder.append(0);
        }
        stringBuilder.append(sequenceStr);


        //分库分表位
        stringBuilder.append("00");
        return stringBuilder.toString();
    }
}

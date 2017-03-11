package com.mju.dao;

import com.mju.model.entity.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Hua on 2017/3/2.
 */
@Mapper
public interface OrderDao {

    Order selectOrderById(String orderId);

    List<Order> selectAllOrders();

    List<Order> queryOrder(Map<String, Object> map);

    Integer countOfOrder(Map<String, Object> map);

    Integer insertOrder(Order order);

    Integer deleteOrderById(String orderId);

    Integer updateOrderStatus(Order order);

    Integer updateOrder(Order order);

}

package com.mju.service;

import com.mju.model.dto.PageBean;
import com.mju.model.entity.Movie;
import com.mju.model.entity.Order;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Hua on 2017/2/21.
 */
public interface OrderService {
    Order getOrderById(String orderId);

    List<Order> queryOrderByScheduleId(Integer scheduleId);

    PageBean<Order> queryOrder(Map<String, Object> fields);

    Order saveOrder(Order order);

    boolean modifyOrderOnStatusById(Order order);

    boolean deleteOrderById(String orderId);

    boolean modifyOrder(Order order);

}

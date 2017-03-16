package com.mju.service.impl;

import com.mju.dao.OrderDao;
import com.mju.model.dto.PageBean;
import com.mju.model.entity.Order;
import com.mju.service.OrderService;
import com.mju.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by Hua on 2017/2/21.
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;

    @Override
    public Order getOrderById(String orderId) {
        return Optional
                .ofNullable(orderDao.selectOrderById(orderId))
                .orElse(null);
    }

    @Override
    public PageBean<Order> queryOrder(Map<String, Object> fields) {
        fields = Optional.ofNullable(fields).orElse(new HashMap<String,Object>());
        PageBean<Order> pageBean = new PageBean<>();
        int page = PageUtil.getPage(fields);
        int perPage = PageUtil.getPerPage(fields);
        int offset = PageUtil.calculateOffset(page, perPage);

        pageBean.setCurrentPage(page);
        pageBean.setPageCount(perPage);
        fields.put("offset", offset);
        fields.put("perPage", perPage);

        List<Order> list = orderDao.queryOrder(fields);
        pageBean.setPageData(list);
        int count = orderDao.countOfOrder(fields);
        pageBean.setTotalCount(count);
        return pageBean;
    }

    @Override
    public List<Order> queryOrderByScheduleId(Integer scheduleId) {
        return orderDao.queryOrderByScheduleId(scheduleId);
    }

    @Override
    @Transactional
    public Order saveOrder(Order order) {
        String orderId = UUID.randomUUID().toString().replaceAll("-", "");
        order.setOrderId(orderId);
        order.setCreateTime(new Timestamp(System.currentTimeMillis()));
        order.setOrderStatus(1);
        if (orderDao.insertOrder(order) > 0) {
            return order;
        }else {
            return null;
        }
    }

    @Override
    @Transactional
    public boolean deleteOrderById(String orderId) {
        return orderDao.deleteOrderById(orderId) > 0;
    }


    @Override
    @Transactional
    public boolean modifyOrderOnStatusById(Order order) {
        return orderDao.updateOrderStatus(order) > 0;
    }

    @Override
    @Transactional
    public boolean modifyOrder(Order order) {
        return orderDao.updateOrder(order) > 0;
    }
}

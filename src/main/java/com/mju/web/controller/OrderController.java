package com.mju.web.controller;

import com.mju.model.dto.ExceptionMsg;
import com.mju.model.dto.PageBean;
import com.mju.model.dto.RepMessage;
import com.mju.model.entity.Order;
import com.mju.service.OrderService;
import com.mju.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hua on 2017/2/21.
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/query")
    public RepMessage queryOrder(@RequestBody(required = false) Map<String, Object> fields){
        RepMessage rep = new RepMessage();
        Map<String, Object> content = new HashMap<>();
        try {
            PageBean<Order> list = orderService.queryOrder(fields);

            if (list != null && list.getPageData().size() > 0) {
                content.put("orderInfo", list);
                rep.setContent(content);
                rep.setStatus(ExceptionMsg.SUCCESS);
            }else {
                rep.setStatus(ExceptionMsg.FAILED);
            }


        } catch (Exception e) {
            rep.setStatus(ExceptionMsg.EXCEPTION);
        }
        return rep;
    }

    @GetMapping("/close/{orderId}")
    public RepMessage closeOrder(@PathVariable String orderId) {
        RepMessage rep = new RepMessage();
        Map<String, Object> content = new HashMap<>();
        try {
            Order o = orderService.getOrderById(orderId);
            if (Const.ORDER_STATUS_Y == o.getOrderStatus()) {
                rep.setStatus(ExceptionMsg.FAILED);
                return rep;
            }

            Order order = new Order();
            order.setOrderId(orderId);
            order.setOrderStatus(Const.ORDER_STATUS_C);
            if (orderService.modifyOrderOnStatusById(order)) {
                rep.setStatus(ExceptionMsg.SUCCESS);
            }else {
                rep.setStatus(ExceptionMsg.FAILED);
            }

        } catch (Exception e) {
            rep.setStatus(ExceptionMsg.EXCEPTION);
        }
        return rep;
    }

    @GetMapping("/{orderId}")
    public RepMessage getOrderById(@PathVariable String orderId) {
        RepMessage rep = new RepMessage();
        Map<String, Object> content = new HashMap<>();
        try {
            Order order = orderService.getOrderById(orderId);
            if (order != null) {
                content.put("orderInfo", order);
                rep.setContent(content);
                rep.setStatus(ExceptionMsg.SUCCESS);
            }else {
                rep.setStatus(ExceptionMsg.FAILED);
            }

        } catch (Exception e) {
            rep.setStatus(ExceptionMsg.EXCEPTION);
        }
        return rep;
    }

    @PostMapping
    public RepMessage postOrder(@Valid @RequestBody Order order,BindingResult result) {
        RepMessage rep = new RepMessage();
        try {
            if (result.hasErrors()) {
                StringBuffer sb = new StringBuffer();
                for (ObjectError err : result.getAllErrors()) {
                    sb.append(err.getDefaultMessage() +";");
                }
                rep.setRepCode(Const.FAILED);
                rep.setRepMsg(sb.toString());
            }else {

                if (orderService.saveOrder(order) != null) {
                    rep.setStatus(ExceptionMsg.SUCCESS);
                }else {
                    rep.setStatus(ExceptionMsg.FAILED);
                }
            }
        } catch (Exception e) {
            rep.setStatus(ExceptionMsg.EXCEPTION);
        }
        return rep;
    }


    @PutMapping("/{orderId}")
    public RepMessage updateOrder(@PathVariable String orderId,@RequestBody Order order) {

        RepMessage rep = new RepMessage();
        try {
            Order o = orderService.getOrderById(orderId);
            if (o != null) {
                if (orderService.modifyOrderOnStatusById(order)) {

                    rep.setStatus(ExceptionMsg.SUCCESS);
                }else {

                    rep.setStatus(ExceptionMsg.FAILED);
                }
            }
        } catch (Exception e) {
            rep.setStatus(ExceptionMsg.EXCEPTION);
        }
        return rep;
    }

    @DeleteMapping("/{orderId}")
    public RepMessage deleteOrder(@PathVariable String orderId) {
        RepMessage rep = new RepMessage();
        try {
            if (orderService.deleteOrderById(orderId)) {
                rep.setStatus(ExceptionMsg.SUCCESS);
            }else {
                rep.setStatus(ExceptionMsg.FAILED);
            }
        } catch (Exception e) {
            rep.setStatus(ExceptionMsg.EXCEPTION);
        }
        return rep;
    }


}

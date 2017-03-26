package com.mju.web.controller;

import com.mju.model.dto.ExceptionMsg;
import com.mju.model.dto.PageBean;
import com.mju.model.dto.RepMessage;
import com.mju.model.entity.Order;
import com.mju.service.OrderService;
import com.mju.util.Const;
import com.pingplusplus.Pingpp;
import com.pingplusplus.exception.*;
import com.pingplusplus.model.Charge;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Hua on 2017/2/21.
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    static {

    }

    @Autowired
    private OrderService orderService;

    @Value("${pingpp.api.key}")
    private String apiKey;

    @Value("${pingpp.app.id}")
    private String appId;

    @Value("${pingpp.private.key}")
    private String privateKey;

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

    @GetMapping("/payment/{orderId}")
    public RepMessage payment(@PathVariable String orderId) {
        RepMessage rep = new RepMessage();
        Pingpp.apiKey = this.apiKey;
        Pingpp.appId = this.appId;
        Pingpp.privateKey = this.privateKey;
        try {
            Order order = orderService.getOrderById(orderId);
            String chId = order.getChId();
            if (StringUtils.isEmpty(chId)) {
                rep.setRepMsg("请先选择支付方式，并完成支付！");
                rep.setRepCode(Const.FAILED);
                return rep;
            }
            Charge charge = null;
            Map<String, Object> params = new HashMap<String, Object>();
            charge = Charge.retrieve(chId, params);
            if (!charge.getPaid()) {
                rep.setStatus(ExceptionMsg.FAILED);
                rep.setRepMsg("未付款");
                return rep;
            }

            if (!order.getOrderId().equals(charge.getOrderNo())) {
                rep.setStatus(ExceptionMsg.FAILED);
                rep.setRepMsg("订单号错误");
                return rep;
            }
            order.setOrderStatus(Const.ORDER_STATUS_Y);
            if (orderService.modifyOrderOnStatusById(order)) {
                rep.setStatus(ExceptionMsg.SUCCESS);
            }else {
                rep.setStatus(ExceptionMsg.FAILED);
                rep.setRepMsg("数据库操作失败");
            }
        } catch (Exception e) {
            rep.setStatus(ExceptionMsg.EXCEPTION);
        }
        return rep;

    }


    @PostMapping("/create/charge")
    public RepMessage createCharge(@RequestParam(value = "orderId") String orderId,@RequestParam(value = "channel",required = false) String channel,HttpServletRequest request) {


        Pingpp.apiKey = this.apiKey;
        Pingpp.appId = this.appId;
        Pingpp.privateKey = this.privateKey;

        RepMessage rep = new RepMessage();

        Order order = orderService.getOrderById(orderId);

        if (Const.ORDER_STATUS_Y == order.getOrderStatus()) {
            rep.setRepMsg("已付款");
            rep.setRepCode(Const.FAILED);
            return rep;
        }

        Charge charge = null;
        Map<String, Object> chargeParams = new HashMap<String, Object>();

        chargeParams.put("order_no", order.getOrderId());
        chargeParams.put("amount", order.getAmount()*100);
        Map<String, String> app = new HashMap<String, String>();
        app.put("id", appId);
        chargeParams.put("app", app);
        chargeParams.put("client_ip", "127.0.0.1");
        chargeParams.put("currency", "cny");
        chargeParams.put("subject", "电影票");
        chargeParams.put("body", order.getOrderId() + order.getUsername());
        switch (channel) {
            case Const.ALI_PC_CHANNEL:
                chargeParams.put("channel", Const.ALI_PC_CHANNEL);
                Map<String, Object> extra = new HashMap<String, Object>();
                extra.put("success_url", "http://127.0.0.1:8080/" + request.getServletContext().getContextPath() + "/payment/" + orderId);
                chargeParams.put("extra", extra);
                break;
            case Const.ALI_QR:
                chargeParams.put("channel", Const.ALI_QR);
                break;
            default:
                rep.setRepMsg("请选择渠道");
                rep.setRepCode(Const.FAILED);
                break;
        }

        try {
            charge = Charge.create(chargeParams);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        } catch (InvalidRequestException e) {
            e.printStackTrace();
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIException e) {
            e.printStackTrace();
        } catch (ChannelException e) {
            e.printStackTrace();
        } catch (RateLimitException e) {
            e.printStackTrace();
        }
        if (charge != null) {
            orderService.modifyOrderChargeId(orderId, charge.getId());

            switch (channel) {
                case Const.ALI_PC_CHANNEL:
                    rep.setContent(Collections.singletonMap("charge",charge.toString()));
                    break;
                case Const.ALI_QR:
                    rep.setContent(Collections.singletonMap("charge",charge));
                    break;
                default:
                    break;
            }
            rep.setStatus(ExceptionMsg.SUCCESS);
        }else {
            rep.setStatus(ExceptionMsg.FAILED);
        }

        return rep;
    }

}

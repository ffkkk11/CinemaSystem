package com.mju.web.controller;

import com.mju.model.dto.ExceptionMsg;
import com.mju.model.dto.RepMessage;
import com.mju.model.entity.Order;
import com.mju.model.entity.Schedule;
import com.mju.model.entity.User;
import com.mju.service.OrderService;
import com.mju.service.ScheduleService;
import com.mju.service.UserService;
import com.mju.util.Const;
import com.pingplusplus.Pingpp;
import com.pingplusplus.model.Charge;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Hua on 2017/3/2.
 */
@Controller
public class IndexController {

    @Value("${pingpp.api.key}")
    private String apiKey;

    @Value("${pingpp.app.id}")
    private String appId;

    @Value("${pingpp.private.key}")
    private String privateKey;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String home(Model model) {
        return this.newMovie(model);
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/hello")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String hello() {
        return "hello";
    }


    @RequestMapping("/info")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public String info() {
        return "info";
    }

    @RequestMapping("/cinema_list")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String cinema() {
        return "cinema/cinema_list";
    }

    @RequestMapping("/tmpl_list")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String tmpl() {
        return "tmpl/tmpl_list";
    }

    @RequestMapping("/room_list")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String room() {
        return "room/room_list";
    }

    @RequestMapping("/movie_list")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String movie() {
        return "movie/movie_list";
    }

    @RequestMapping("/user_list")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String user() {
        return "user/user_list";
    }

    @RequestMapping("/schedule_list")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String schedule() {
        return "schedule/schedule_list";
    }

    @RequestMapping("/order_list")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String order() {
        return "order/order_list";
    }

    @RequestMapping("/new_movie")
    public String newMovie(Model model) {
        List<Schedule> scheduleList = scheduleService.queryNewList();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        Date d = new Date();
        String nowTime = sdf.format(d);
        model.addAttribute("nowTime", nowTime);
        model.addAttribute("scheduleInfo", scheduleList);
        return "new_movie";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(User user,Model model) {
        if (user == null) {
            model.addAttribute("errorMsg", "请输入账号信息");
            return "register";
        }

        String username = user.getUsername();
        String password = user.getPassword();

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            model.addAttribute("errorMsg", "账号及密码不能为空");
            return "register";
        }

        User u = userService.getUserByUsername(username);
        if (u != null) {
            model.addAttribute("errorMsg", "账号已存在");
            return "register";
        }


        user.setRole(Const.ROLE_USER);
        if (userService.saveUser(user)) {
            return "login";
        }else {
            model.addAttribute("errorMsg", "添加失败");
            return "register";
        }


    }

    @RequestMapping("/buy/seat/{scheduleId}")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public String buySeat(@PathVariable Integer scheduleId,Model model) {
        try {
            Schedule schedule = scheduleService.getScheduleById(scheduleId);
            model.addAttribute("scheduleDetail", schedule);
            StringBuffer sb = new StringBuffer();
            List<Order> seatList = orderService.queryOrderByScheduleId(scheduleId);
            for (Order o : seatList) {
                String seats = o.getSeats();
                if (StringUtils.isNotEmpty(seats)) {
                    for (String s : seats.split(",")) {
                        sb.append(s).append(",");
                    }
                }

            }
            String str = "";
            if (StringUtils.isNotEmpty(sb)) {
                str = sb.substring(0,sb.length()-1);
            }

            model.addAttribute("alreadyBuySeats", str);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "buy/seat";
    }

    @RequestMapping("/buy/create_order")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public String createOrder(Order order, Model model) {

        String seats = order.getSeats();
        Double amount =0d;
        Schedule schedule = scheduleService.getScheduleById(order.getScheduleId());
        Double price = schedule.getPrice();

        if (StringUtils.isNotEmpty(seats)) {
            for (String s : seats.split(",")) {
                amount += price;
            }
        }
        order.setAmount(amount);

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        order.setUserId(user.getUserId());
        Order o = orderService.saveOrder(order);
        o.setSchedule(schedule);
        model.addAttribute("orderDetail", order);

        return "buy/create_order";
    }

    @RequestMapping("/my_order")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public String myOrder(Model model) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user != null) {
            model.addAttribute("currentUser", user);
        }
        return "my_order";
    }

    @GetMapping("/payment/{orderId}")
    public String payment(@PathVariable String orderId,Model model) {
        Pingpp.apiKey = this.apiKey;
        Pingpp.appId = this.appId;
        Pingpp.privateKey = this.privateKey;
        try {
            Order order = orderService.getOrderById(orderId);
            String chId = order.getChId();

            Charge charge = null;
            Map<String, Object> params = new HashMap<String, Object>();
            charge = Charge.retrieve(chId, params);
            if (!charge.getPaid()) {
                model.addAttribute("errMsg", "未付款");
                return "err";
            }

            if (!order.getOrderId().equals(charge.getOrderNo())) {
                model.addAttribute("errMsg", "订单号错误");
                return "err";
            }
            order.setOrderStatus(Const.ORDER_STATUS_Y);
            if (orderService.modifyOrderOnStatusById(order)) {
                return "redirect:/my_order";
            }else {
                model.addAttribute("errMsg", "数据库操作失败");
                return "err";
            }
        } catch (Exception e) {
            model.addAttribute("errMsg", "异常");
            return "err";
        }
    }
}

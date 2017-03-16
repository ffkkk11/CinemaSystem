package com.mju.web.controller;

import com.mju.model.entity.Order;
import com.mju.model.entity.Schedule;
import com.mju.model.entity.User;
import com.mju.service.OrderService;
import com.mju.service.ScheduleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Hua on 2017/3/2.
 */
@Controller
public class IndexController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private OrderService orderService;

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
        return "/cinema/cinema_list";
    }

    @RequestMapping("/tmpl_list")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String tmpl() {
        return "/tmpl/tmpl_list";
    }

    @RequestMapping("/room_list")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String room() {
        return "/room/room_list";
    }

    @RequestMapping("/movie_list")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String movie() {
        return "/movie/movie_list";
    }

    @RequestMapping("/user_list")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String user() {
        return "/user/user_list";
    }

    @RequestMapping("/schedule_list")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String schedule() {
        return "/schedule/schedule_list";
    }

    @RequestMapping("/order_list")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String order() {
        return "/order/order_list";
    }

    @RequestMapping("/new_movie")
    public String newMovie(Model model) {
        List<Schedule> scheduleList = scheduleService.queryNewList();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        Date d = new Date();
        String nowTime = sdf.format(d);
        model.addAttribute("nowTime", nowTime);
        model.addAttribute("scheduleInfo", scheduleList);
        return "/new_movie";
    }

    @GetMapping("/register")
    public String register() {
        return "/register";
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
        return "/buy/seat";
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

        return "/buy/create_order";
    }

    @RequestMapping("/my_order")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public String myOrder(Model model) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user != null) {
            model.addAttribute("currentUser", user);
        }
        return "/my_order";
    }

}

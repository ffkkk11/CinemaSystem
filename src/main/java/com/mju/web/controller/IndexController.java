package com.mju.web.controller;

import com.mju.model.entity.Schedule;
import com.mju.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @RequestMapping("/info")
    public String info() {
        return "info";
    }

    @RequestMapping("/cinema_list")
    public String cinema() {
        return "/cinema/cinema_list";
    }

    @RequestMapping("/tmpl_list")
    public String tmpl() {
        return "/tmpl/tmpl_list";
    }

    @RequestMapping("/room_list")
    public String room() {
        return "/room/room_list";
    }

    @RequestMapping("/movie_list")
    public String movie() {
        return "/movie/movie_list";
    }

    @RequestMapping("/user_list")
    public String user() {
        return "/user/user_list";
    }

    @RequestMapping("/schedule_list")
    public String schedule() {
        return "/schedule/schedule_list";
    }

    @RequestMapping("/order_list")
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
}

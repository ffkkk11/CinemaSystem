package com.mju.web.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Hua on 2017/3/2.
 */
@Controller
public class IndexController {

    @RequestMapping("/")
    public String home() {
        return "index";
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
}

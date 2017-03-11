package com.mju.web.controller;

import com.mju.model.dto.ExceptionMsg;
import com.mju.model.dto.PageBean;
import com.mju.model.dto.RepMessage;
import com.mju.model.entity.User;
import com.mju.service.UserService;
import com.mju.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hua on 2017/3/2.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/query")
    public RepMessage queryUser(@RequestBody(required = false) Map<String, Object> fields) {

        RepMessage rep = new RepMessage();
        Map<String, Object> content = new HashMap<>();
        try {
            PageBean<User> list = userService.queryUser(fields);

            if (list != null && list.getPageData().size() > 0) {
                for (User u : list.getPageData()) {
                    u.setPassword("******");
                }

                content.put("userInfo", list);
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

    @GetMapping("/check/{username}")
    public RepMessage checkUsername(@PathVariable String username) {
        RepMessage rep = new RepMessage();
        try {
            User u = userService.getUserByUsername(username);
            if (u != null) {
                rep.setRepCode(Const.FAILED);
                rep.setRepMsg("用户名已存在");
            }else{
                rep.setRepCode(Const.SUCCESS);
                rep.setRepMsg("用户名可以正常使用");
            }

        } catch (Exception e) {
            rep.setStatus(ExceptionMsg.EXCEPTION);
        }
        return rep;

    }

    @GetMapping("/{userId}")
    public RepMessage getUser(@PathVariable String userId) {
        RepMessage rep = new RepMessage();
        Map<String, Object> content = new HashMap<>();
        try {
            User user = userService.getUserById(userId);
            if (user != null) {
                content.put("userInfo", user);
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

    @PutMapping("/{userId}")
    public RepMessage putUser(@Valid @RequestBody User user, BindingResult result,@PathVariable String userId) {
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
                user.setUserId(userId);
                if (userService.modifyUser(user)) {
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

    @PostMapping
    public RepMessage addUser(@Valid @RequestBody User user, BindingResult result) {
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
                User u = userService.getUserByUsername(user.getUsername());

                if (u != null) {
                    rep.setRepCode(Const.FAILED);
                    rep.setRepMsg("用户名已存在");
                    return rep;
                }

                if (userService.saveUser(user)) {
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

    @DeleteMapping("/{userId}")
    public RepMessage delUser(@PathVariable String userId) {
        RepMessage rep = new RepMessage();
        try {
            if (userService.delUserbyId(userId)) {
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

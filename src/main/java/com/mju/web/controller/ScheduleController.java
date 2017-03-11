package com.mju.web.controller;

import com.mju.model.dto.ExceptionMsg;
import com.mju.model.dto.PageBean;
import com.mju.model.dto.RepMessage;
import com.mju.model.entity.Schedule;
import com.mju.service.ScheduleService;
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
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    @PostMapping("/query")
    public RepMessage query(@RequestBody(required = false) Map<String, Object> fields){
        RepMessage rep = new RepMessage();
        Map<String, Object> content = new HashMap<>();
        try {
            PageBean<Schedule> list = scheduleService.querySchedule(fields);

            if (list != null && list.getPageData().size() > 0) {
                content.put("scheduleInfo", list);
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

    @GetMapping("/{scheduleId}")
    public RepMessage getScheduleById(@PathVariable Integer scheduleId) {
        RepMessage rep = new RepMessage();
        Map<String, Object> content = new HashMap<>();
        try {
            Schedule schedule = scheduleService.getScheduleById(scheduleId);
            if (schedule != null) {
                content.put("scheduleInfo", schedule);
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


    @PutMapping("/{scheduleId}")
    public RepMessage putSchedule(@Valid @RequestBody Schedule schedule, BindingResult result,@PathVariable Integer scheduleId) {
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
                schedule.setScheduleId(scheduleId);
                if (scheduleService.modifySchedule(schedule)) {
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
    public RepMessage postSchedule(@Valid @RequestBody Schedule schedule, BindingResult result) {

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
                if (scheduleService.saveSchedule(schedule)) {
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

    @DeleteMapping("/{scheduleId}")
    public RepMessage deleteSchedule(@PathVariable Integer scheduleId) {
        RepMessage rep = new RepMessage();
        try {
            if (scheduleService.deleteScheduleById(scheduleId)) {
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

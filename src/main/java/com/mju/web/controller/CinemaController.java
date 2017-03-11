package com.mju.web.controller;

import com.mju.model.dto.ExceptionMsg;
import com.mju.model.dto.PageBean;
import com.mju.model.dto.RepMessage;
import com.mju.model.entity.Cinema;
import com.mju.service.CinemaService;
import com.mju.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hua on 2017/2/19.
 */
@RestController
@RequestMapping("/cinema")
public class CinemaController {
    @Autowired
    private CinemaService cinemaService;

    @PostMapping("/query")
    public RepMessage queryCinema(@RequestBody(required = false) Map<String, Object> fields) {
        RepMessage rep = new RepMessage();
        Map<String, Object> content = new HashMap<>();
        try {
            PageBean<Cinema> list = cinemaService.queryCinema(fields);
            if (list != null && list.getPageData().size() > 0) {
                content.put("cinemaInfo", list);
                rep.setContent(content);
                rep.setStatus(ExceptionMsg.SUCCESS);
            }else{
                rep.setStatus(ExceptionMsg.FAILED);
            }

        } catch (Exception e) {
            rep.setStatus(ExceptionMsg.EXCEPTION);
        }
        return rep;
    }

    @GetMapping("/{cinemaId}")
    public RepMessage getCinemaById(@PathVariable Integer cinemaId) {
        RepMessage rep = new RepMessage();
        Map<String, Object> content = new HashMap<>();
        try {
            Cinema cinema = cinemaService.getCinemaById(cinemaId);
            if (cinema != null) {
                content.put("cinemaInfo", cinema);
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

    @PutMapping("/{cinemaId}")
    public RepMessage putCinema(@Valid @RequestBody Cinema cinema, BindingResult result, @PathVariable Integer cinemaId) {
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
                cinema.setCinemaId(cinemaId);
                if (cinemaService.modifyCinema(cinema)) {
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
    public RepMessage postCinema(@Valid @RequestBody Cinema cinema, BindingResult result) {

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
                if (cinemaService.saveCinema(cinema)) {
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

    @DeleteMapping("/{cinemaId}")
    public RepMessage deleteCinema(@PathVariable Integer cinemaId) {
        RepMessage rep = new RepMessage();
        try {
            if (cinemaService.deleteCinemaById(cinemaId)) {
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

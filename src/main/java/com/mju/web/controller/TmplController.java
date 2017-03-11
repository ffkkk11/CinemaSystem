package com.mju.web.controller;

import com.mju.model.dto.ExceptionMsg;
import com.mju.model.dto.PageBean;
import com.mju.model.dto.RepMessage;
import com.mju.model.entity.Tmpl;
import com.mju.service.TmplService;
import com.mju.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hua on 2017/2/20.
 */
@RestController
@RequestMapping("/tmpl")
public class TmplController {
    @Autowired
    private TmplService tmplService;

    @PostMapping("/query")
    public RepMessage queryTmpl(@RequestBody(required = false) Map<String, Object> fields){
        RepMessage rep = new RepMessage();
        Map<String, Object> content = new HashMap<>();
        try {
            PageBean<Tmpl> list = tmplService.queryTmpl(fields);

            if (list != null && list.getPageData().size() > 0) {
                content.put("tmplInfo", list);
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

    @GetMapping("/{tmplId}")
    public RepMessage getTmplById(@PathVariable Integer tmplId) {
        RepMessage rep = new RepMessage();
        Map<String, Object> content = new HashMap<>();
        try {
            Tmpl tmpl = tmplService.getTmplById(tmplId);
            if (tmpl != null) {
                content.put("tmplInfo", tmpl);
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

    @PutMapping("/{tmplId}")
    public RepMessage putTmpl(@Valid @RequestBody Tmpl tmpl,BindingResult result,@PathVariable Integer tmplId) {
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
                tmpl.setTmplId(tmplId);
                if (tmplService.modifyTmpl(tmpl)) {
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
    public RepMessage postTmpl(@Valid @RequestBody Tmpl tmpl,BindingResult result) {
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
                if (tmplService.saveTmpl(tmpl)) {
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

    @DeleteMapping("/{tmplId}")
    public RepMessage deleteTmpl(@PathVariable Integer tmplId) {
        RepMessage rep = new RepMessage();
        try {
            if (tmplService.deleteTmplById(tmplId)) {
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

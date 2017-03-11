package com.mju.service;

import com.mju.model.dto.PageBean;
import com.mju.model.entity.Tmpl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Hua on 2017/2/20.
 */
public interface TmplService {
    Tmpl getTmplById(Integer tmplId);

    PageBean<Tmpl> queryTmpl(Map<String, Object> fields);

    boolean saveTmpl(Tmpl tmpl);

    boolean deleteTmplById(Integer tmplId);

    boolean modifyTmpl(Tmpl tmpl);
}

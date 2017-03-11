package com.mju.service.impl;

import com.mju.dao.TmplDao;
import com.mju.model.dto.PageBean;
import com.mju.model.entity.Tmpl;
import com.mju.service.TmplService;
import com.mju.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Hua on 2017/2/20.
 */
@Service
public class TmplServiceImpl implements TmplService {
    @Autowired
    private TmplDao tmplDao;

    @Override
    public Tmpl getTmplById(Integer tmplId) {
        return Optional.ofNullable(tmplDao.selectTmplById(tmplId)).orElse(null);
    }

    @Override
    public PageBean<Tmpl> queryTmpl(Map<String, Object> fields) {
        fields = Optional.ofNullable(fields).orElse(new HashMap<String,Object>());
        PageBean<Tmpl> pageBean = new PageBean<>();
        int page = PageUtil.getPage(fields);
        int perPage = PageUtil.getPerPage(fields);
        int offset = PageUtil.calculateOffset(page, perPage);

        pageBean.setCurrentPage(page);
        pageBean.setPageCount(perPage);
        fields.put("offset", offset);
        fields.put("perPage", perPage);

        List<Tmpl> list = tmplDao.queryTmpl(fields);
        pageBean.setPageData(list);
        int count = tmplDao.countOfTmpl(fields);
        pageBean.setTotalCount(count);
        return pageBean;
    }

    @Override
    @Transactional
    public boolean saveTmpl(Tmpl tmpl) {
        return tmplDao.insertTmpl(tmpl) > 0;
    }

    @Override
    @Transactional
    public boolean deleteTmplById(Integer tmplId) {
        return tmplDao.deleteTmplById(tmplId) > 0;
    }

    @Override
    @Transactional
    public boolean modifyTmpl(Tmpl tmpl) {
        return tmplDao.updateTmpl(tmpl) > 0;
    }
}

package com.mju.dao;

import com.mju.model.entity.Tmpl;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Hua on 2017/3/2.
 */
@Mapper
public interface TmplDao {
    Tmpl selectTmplById(Integer tmplId);

    List<Tmpl> selectAllTmpls();

    List<Tmpl> queryTmpl(Map<String, Object> map);

    Integer countOfTmpl(Map<String, Object> map);

    Integer insertTmpl(Tmpl tmpl);

    Integer deleteTmplById(Integer tmplId);

    Integer updateTmpl(Tmpl tmpl);
}

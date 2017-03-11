package com.mju.service.impl;

import com.mju.dao.MovieDao;
import com.mju.model.dto.PageBean;
import com.mju.model.entity.Movie;
import com.mju.service.MovieService;
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
public class MovieServiceImpl implements MovieService {
    @Autowired
    private MovieDao movieDao;

    @Override
    public Movie getMovieById(Integer movieId) {
        return Optional
                .ofNullable(movieDao.selectMovieById(movieId))
                .orElse(null);
    }

    @Override
    public PageBean<Movie> queryMovie(Map<String, Object> fields) {
        fields = Optional.ofNullable(fields).orElse(new HashMap<String,Object>());
        PageBean<Movie> pageBean = new PageBean<>();
        int page = PageUtil.getPage(fields);
        int perPage = PageUtil.getPerPage(fields);
        int offset = PageUtil.calculateOffset(page, perPage);

        pageBean.setCurrentPage(page);
        pageBean.setPageCount(perPage);
        fields.put("offset", offset);
        fields.put("perPage", perPage);

        List<Movie> list = movieDao.queryMovie(fields);
        pageBean.setPageData(list);
        int count = movieDao.countOfMovie(fields);
        pageBean.setTotalCount(count);
        return pageBean;
    }

    @Override
    @Transactional
    public boolean saveMovie(Movie movie) {
        return movieDao.insertMovie(movie) > 0;
    }

    @Override
    @Transactional
    public boolean deleteMovieById(Integer movieId) {
        return movieDao.deleteMovieById(movieId) >0 ;
    }

    @Override
    @Transactional
    public boolean modifyMovie(Movie movie) {
        return movieDao.updateMovie(movie) > 0;
    }
}

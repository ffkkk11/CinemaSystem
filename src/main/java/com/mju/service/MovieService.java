package com.mju.service;

import com.mju.model.dto.PageBean;
import com.mju.model.entity.Movie;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Hua on 2017/2/20.
 */
public interface MovieService {
    Movie getMovieById(Integer movieId);

    PageBean<Movie> queryMovie(Map<String, Object> fields);

    boolean saveMovie(Movie movie);

    boolean deleteMovieById(Integer movieId);

    boolean modifyMovie(Movie movie);

}

package com.mju.dao;

import com.mju.model.entity.Movie;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Hua on 2017/3/2.
 */
@Mapper
public interface MovieDao {
    Movie selectMovieById(Integer movieId);

    List<Movie> selectAllMovies();

    List<Movie> queryMovie(Map<String, Object> map);

    Integer countOfMovie(Map<String, Object> map);

    Integer insertMovie(Movie movie);

    Integer deleteMovieById(Integer movieId);

    Integer updateMovie(Movie movie);
}

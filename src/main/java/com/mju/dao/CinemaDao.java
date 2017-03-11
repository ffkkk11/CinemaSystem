package com.mju.dao;

import com.mju.model.entity.Cinema;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * Created by Hua on 2017/3/2.
 */
@Mapper
public interface CinemaDao {
    Cinema selectCinemaById(Integer cinemaId);

    List<Cinema> selectAllCinemas();

    List<Cinema> queryCinema(Map<String, Object> map);

    Integer countOfCinema(Map<String, Object> map);

    Integer insertCinema(Cinema cinema);

    Integer deleteCinemaById(Integer cinemaId);

    Integer updateCinema(Cinema cinema);

}

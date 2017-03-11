package com.mju.service;

import com.mju.model.dto.PageBean;
import com.mju.model.entity.Cinema;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Hua on 2017/2/19.
 */
public interface CinemaService {
    Cinema getCinemaById(Integer cinemaId);

    PageBean<Cinema> queryCinema(Map<String, Object> fields);

    boolean saveCinema(Cinema cinema);

    boolean deleteCinemaById(Integer cinemaId);

    boolean modifyCinema(Cinema cinema);
}

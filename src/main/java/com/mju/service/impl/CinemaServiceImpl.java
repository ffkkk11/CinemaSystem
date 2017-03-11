package com.mju.service.impl;

import com.mju.dao.CinemaDao;
import com.mju.model.dto.PageBean;
import com.mju.model.entity.Cinema;
import com.mju.service.CinemaService;
import com.mju.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Hua on 2017/2/19.
 */
@Service
public class CinemaServiceImpl implements CinemaService {
    @Autowired
    private CinemaDao cinemaDao;

    @Override
    public Cinema getCinemaById(Integer cinemaId) {
        return Optional
                .ofNullable(cinemaDao.selectCinemaById(cinemaId))
                .orElse(null);
    }

    @Override
    public PageBean<Cinema> queryCinema(Map<String, Object> fields) {
        fields = Optional.ofNullable(fields).orElse(new HashMap<String,Object>());
        PageBean<Cinema> pageBean = new PageBean<>();
        int page = PageUtil.getPage(fields);
        int perPage = PageUtil.getPerPage(fields);
        int offset = PageUtil.calculateOffset(page, perPage);

        pageBean.setCurrentPage(page);
        pageBean.setPageCount(perPage);
        fields.put("offset", offset);
        fields.put("perPage", perPage);

        List<Cinema> list = cinemaDao.queryCinema(fields);
        pageBean.setPageData(list);
        int count = cinemaDao.countOfCinema(fields);
        pageBean.setTotalCount(count);
        return pageBean;
    }


    @Override
    @Transactional
    public boolean saveCinema(Cinema cinema) {
        return cinemaDao.insertCinema(cinema) >0;
    }

    @Override
    @Transactional
    public boolean deleteCinemaById(Integer cinemaId) {
        return cinemaDao.deleteCinemaById(cinemaId) > 0;
    }

    @Override
    @Transactional
    public boolean modifyCinema(Cinema cinema) {
        return cinemaDao.updateCinema(cinema) > 0;
    }
}

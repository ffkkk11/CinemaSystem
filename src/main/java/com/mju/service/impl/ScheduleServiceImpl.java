package com.mju.service.impl;

import com.mju.dao.ScheduleDao;
import com.mju.model.dto.PageBean;
import com.mju.model.entity.Schedule;
import com.mju.service.ScheduleService;
import com.mju.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by Hua on 2017/2/21.
 */
@Service
public class ScheduleServiceImpl implements ScheduleService {
    @Autowired
    private ScheduleDao scheduleDao;

    @Override
    public Schedule getScheduleById(Integer scheduleId) {
        return Optional
                .ofNullable(scheduleDao.selectScheduleById(scheduleId))
                .orElse(null);
    }

    @Override
    public PageBean<Schedule> querySchedule(Map<String, Object> fields) {
        fields = Optional.ofNullable(fields).orElse(new HashMap<String,Object>());
        PageBean<Schedule> pageBean = new PageBean<>();
        int page = PageUtil.getPage(fields);
        int perPage = PageUtil.getPerPage(fields);
        int offset = PageUtil.calculateOffset(page, perPage);

        pageBean.setCurrentPage(page);
        pageBean.setPageCount(perPage);
        fields.put("offset", offset);
        fields.put("perPage", perPage);

        List<Schedule> list = scheduleDao.querySchedule(fields);
        Iterator<Schedule> iterator = list.iterator();
        while (iterator.hasNext()) {
            Schedule schedule = iterator.next();
            Long currTime = System.currentTimeMillis();
            Long beginTime = schedule.getBeginTime().getTime();
            if (beginTime < currTime && schedule.getStatus()) {
                schedule.setStatus(false);
                scheduleDao.updateSchedule(schedule);
            }
        }



        pageBean.setPageData(list);
        int count = scheduleDao.countOfSchedule(fields);
        pageBean.setTotalCount(count);
        return pageBean;
    }

    @Override
    public List<Schedule> queryNewList() {
        return scheduleDao.queryNewSchedule();
    }

    @Override
    @Transactional
    public boolean saveSchedule(Schedule schedule) {
        return scheduleDao.insertSchedule(schedule) > 0;
    }

    @Override
    @Transactional
    public boolean deleteScheduleById(Integer scheduleId) {
        return scheduleDao.deleteSchedule(scheduleId) > 0;
    }

    @Override
    @Transactional
    public boolean modifySchedule(Schedule schedule) {
        return scheduleDao.updateSchedule(schedule) > 0;
    }
}

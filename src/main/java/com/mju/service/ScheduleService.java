package com.mju.service;

import com.mju.model.dto.PageBean;
import com.mju.model.entity.Schedule;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Hua on 2017/2/21.
 */
public interface ScheduleService {
    Schedule getScheduleById(Integer scheduleId);

    PageBean<Schedule> querySchedule(Map<String, Object> fields);

    List<Schedule> queryNewList();

    boolean saveSchedule(Schedule schedule);

    boolean deleteScheduleById(Integer scheduleId);

    boolean modifySchedule(Schedule schedule);

}

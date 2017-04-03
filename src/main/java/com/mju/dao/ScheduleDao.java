package com.mju.dao;

import com.mju.model.entity.Schedule;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Hua on 2017/3/2.
 */
@Mapper
public interface ScheduleDao {

    Schedule selectScheduleById(Integer scheduleId);

    List<Schedule> selectAllSchedules();

    List<Schedule> querySchedule(Map<String, Object> map);

    List<Schedule> queryNewSchedule();

    List<Schedule> queryInvalidSchedule();

    Integer countOfSchedule(Map<String, Object> map);

    Integer insertSchedule(Schedule schedule);

    Integer deleteSchedule(Integer scheduleId);

/*    Integer updateScheduleStatus(Integer scheduleId, Integer status);*/

    Integer updateSchedule(Schedule schedule);
}

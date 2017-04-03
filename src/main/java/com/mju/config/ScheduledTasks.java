package com.mju.config;
//   ┏┓　　　┏┓
// ┏┛┻━━━┛┻┓
// ┃　　　　　　　┃ 　
// ┃　　　━　　　┃
// ┃　┳┛　┗┳　┃
// ┃　　　　　　　┃
// ┃　　　┻　　　┃
// ┃　　　　　　　┃
// ┗━┓　　　┏━┛
//     ┃　　　┃ 神兽保佑　　　　　　　　
//     ┃　　　┃ 代码无BUG！
//     ┃　　　┗━━━┓
//     ┃　　　　　　　┣┓
//     ┃　　　　　　　┏┛
//     ┗┓┓┏━┳┓┏┛
//       ┃┫┫　┃┫┫
//       ┗┻┛　┗┻┛

import com.mju.dao.OrderDao;
import com.mju.dao.ScheduleDao;
import com.mju.model.entity.Order;
import com.mju.model.entity.Schedule;
import com.mju.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Hua on 2017/4/3.
 */
@Component
public class ScheduledTasks {
    @Autowired
    private ScheduleDao scheduleDao;

    @Autowired
    private OrderDao orderDao;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate =  30000)
    public void checkSchedule() {

        List<Schedule> scheduleList = scheduleDao.queryInvalidSchedule();
        for (Schedule schedule : scheduleList) {
            schedule.setStatus(false);
            scheduleDao.updateSchedule(schedule);
        }
        System.out.println(sdf.format(new Date()));
    }

    @Scheduled(fixedRate = 30000)
    public void checkOrder() {
        List<Order> orderList = orderDao.queryInvalidOrder(Const.ORDER_INVALID_TIME);
        for (Order order : orderList) {
            order.setOrderStatus(Const.ORDER_STATUS_C);
            orderDao.updateOrderStatus(order);
        }
    }

}

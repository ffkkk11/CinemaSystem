package com.mju.model.entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Hua on 2017/2/19.
 */
public class Order extends Schedule implements Serializable {
    private String orderId;     //编号
    private String userId;      //用户编号
    private Integer orderStatus;    //订单状态 1:待付款,2:已完成,3:已关闭,4:错误
    private Timestamp createTime;   //订单创建时间
    private String seats;           //座位
    private String username;        //用户名
    private Double amount;          //总额
    private String chId;            //chargeID

    public String getOrderId() {
        return orderId;
    }

    public Order setOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public Order setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public Order setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public Order setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getSeats() {
        return seats;
    }

    public Order setSeats(String seats) {
        this.seats = seats;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public Order setUsername(String username) {
        this.username = username;
        return this;
    }

    public Double getAmount() {
        return amount;
    }

    public Order setAmount(Double amount) {
        this.amount = amount;
        return this;
    }

    public Order setSchedule(Schedule schedule) {
        this.scheduleId = schedule.getScheduleId();
        this.roomId = schedule.getRoomId();
        this.roomName = schedule.getRoomName();
        this.movieId = schedule.getMovieId();
        this.movieName = schedule.getMovieName();
        this.movieInfo = schedule.getMovieInfo();
        this.beginTime = schedule.getBeginTime();
        this.bTime = schedule.getbTime();
        this.price = schedule.getPrice();
        this.status = schedule.getStatus();
        this.cinemaId = schedule.getCinemaId();
        this.cinemaName = schedule.getCinemaName();
        this.moviePic = schedule.getMoviePic();
        this.seatMap = schedule.getSeatMap();


        return this;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", userId='" + userId + '\'' +
                ", orderStatus=" + orderStatus +
                ", createTime=" + createTime +
                ", seats='" + seats + '\'' +
                ", username='" + username + '\'' +
                ", amount=" + amount +
                '}';
    }

    public String getChId() {
        return chId;
    }

    public Order setChId(String chId) {
        this.chId = chId;
        return this;
    }
}

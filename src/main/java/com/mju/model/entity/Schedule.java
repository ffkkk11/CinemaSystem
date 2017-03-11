package com.mju.model.entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Hua on 2017/2/19.
 */
public class Schedule implements Serializable {
    protected Integer scheduleId;      //编号
    protected Integer roomId;          //影厅
    protected String roomName;        //影厅名称
    protected String movieId;         //影片
    protected String movieName;       //影片名称
    protected Timestamp beginTime;    //开始时间
    protected Timestamp endTime;      //结束时间
    protected Double price;           //单价
    protected Boolean status;         //上架、下架

    public Integer getScheduleId() {
        return scheduleId;
    }

    public Schedule setScheduleId(Integer scheduleId) {
        this.scheduleId = scheduleId;
        return this;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public Schedule setRoomId(Integer roomId) {
        this.roomId = roomId;
        return this;
    }

    public String getRoomName() {
        return roomName;
    }

    public Schedule setRoomName(String roomName) {
        this.roomName = roomName;
        return this;
    }

    public String getMovieId() {
        return movieId;
    }

    public Schedule setMovieId(String movieId) {
        this.movieId = movieId;
        return this;
    }

    public String getMovieName() {
        return movieName;
    }

    public Schedule setMovieName(String movieName) {
        this.movieName = movieName;
        return this;
    }

    public Timestamp getBeginTime() {
        return beginTime;
    }

    public Schedule setBeginTime(Timestamp beginTime) {
        this.beginTime = beginTime;
        return this;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public Schedule setEndTime(Timestamp endTime) {
        this.endTime = endTime;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public Schedule setPrice(Double price) {
        this.price = price;
        return this;
    }

    public Boolean getStatus() {
        return status;
    }

    public Schedule setStatus(Boolean status) {
        this.status = status;
        return this;
    }
}

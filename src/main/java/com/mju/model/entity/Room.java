package com.mju.model.entity;

import java.io.Serializable;

/**
 * Created by Hua on 2017/2/19.
 */
public class Room extends Tmpl implements Serializable {
    private Integer roomId;     //影厅编号
    private String roomName;   //影厅名称
    private Integer cinemaId;   //影院编号
    private String cinemaName;  //影院名称

    public Integer getRoomId() {
        return roomId;
    }

    public Room setRoomId(Integer roomId) {
        this.roomId = roomId;
        return this;
    }

    public String getRoomName() {
        return roomName;
    }

    public Room setRoomName(String roomName) {
        this.roomName = roomName;
        return this;
    }

    public Integer getCinemaId() {
        return cinemaId;
    }

    public Room setCinemaId(Integer cinemaId) {
        this.cinemaId = cinemaId;
        return this;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public Room setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
        return this;
    }
}

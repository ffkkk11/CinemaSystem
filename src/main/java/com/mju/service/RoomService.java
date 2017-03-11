package com.mju.service;

import com.mju.model.dto.PageBean;
import com.mju.model.entity.Room;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Hua on 2017/2/20.
 */
public interface RoomService {
    Room getRoomById(Integer roomId);

    PageBean<Room> queryRoom(Map<String, Object> fields);

    boolean saveRoom(Room room);

    boolean deleteRoomById(Integer roomId);

    boolean modifyRoom(Room room);
}

package com.mju.dao;

import com.mju.model.entity.Room;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Hua on 2017/3/2.
 */
@Mapper
public interface RoomDao {
    Room selectRoomById(Integer roomId);

    List<Room> selectAllRooms();

    List<Room> queryRoom(Map<String, Object> map);

    Integer countOfRoom(Map<String, Object> map);

    Integer insertRoom(Room room);

    Integer deleteRoomById(Integer roomId);

    Integer updateRoom(Room room);
}

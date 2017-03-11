package com.mju.service.impl;

import com.mju.dao.RoomDao;
import com.mju.model.dto.PageBean;
import com.mju.model.entity.Room;
import com.mju.service.RoomService;
import com.mju.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Hua on 2017/2/20.
 */
@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomDao roomDao;

    @Override
    public Room getRoomById(Integer roomId) {
        return Optional
                .ofNullable(roomDao.selectRoomById(roomId))
                .orElse(null);
    }

    @Override
    public PageBean<Room> queryRoom(Map<String, Object> fields) {
        fields = Optional.ofNullable(fields).orElse(new HashMap<String,Object>());
        PageBean<Room> pageBean = new PageBean<>();
        int page = PageUtil.getPage(fields);
        int perPage = PageUtil.getPerPage(fields);
        int offset = PageUtil.calculateOffset(page, perPage);

        pageBean.setCurrentPage(page);
        pageBean.setPageCount(perPage);
        fields.put("offset", offset);
        fields.put("perPage", perPage);

        List<Room> list = roomDao.queryRoom(fields);
        pageBean.setPageData(list);
        int count = roomDao.countOfRoom(fields);
        pageBean.setTotalCount(count);
        return pageBean;
    }

    @Override
    @Transactional
    public boolean saveRoom(Room room) {
        return roomDao.insertRoom(room) > 0;
    }

    @Override
    @Transactional
    public boolean deleteRoomById(Integer roomId) {
        return roomDao.deleteRoomById(roomId) > 0;
    }

    @Override
    @Transactional
    public boolean modifyRoom(Room room) {
        return roomDao.updateRoom(room) > 0;
    }
}

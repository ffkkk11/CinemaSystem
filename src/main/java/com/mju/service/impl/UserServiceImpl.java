package com.mju.service.impl;

import com.mju.dao.UserDao;
import com.mju.model.dto.PageBean;
import com.mju.model.entity.User;
import com.mju.service.UserService;
import com.mju.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by Hua on 2017/3/2.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserById(String userId) {
        return Optional
                .ofNullable(userDao.getUserById(userId))
                .orElse(null);
    }

    @Override
    public User getUserByUsername(String username) {
        return Optional
                .ofNullable(userDao.getUserByUsername(username))
                .orElse(null);

    }

    @Override
    @Transactional
    public boolean saveUser(User user) {
        user.setUserId(UUID.randomUUID().toString().replaceAll("-", ""));
        return userDao.addUser(user) > 0;
    }

    @Override
    @Transactional
    public boolean delUserbyId(String userId) {
        return userDao.delUser(userId) > 0;
    }

    @Override
    public PageBean<User> queryUser(Map<String, Object> fields) {
        fields = Optional.ofNullable(fields).orElse(new HashMap<String,Object>());
        PageBean<User> pageBean = new PageBean<>();
        int page = PageUtil.getPage(fields);
        int perPage = PageUtil.getPerPage(fields);
        int offset = PageUtil.calculateOffset(page, perPage);

        pageBean.setCurrentPage(page);
        pageBean.setPageCount(perPage);
        fields.put("offset", offset);
        fields.put("perPage", perPage);

        List<User> list = userDao.queryUser(fields);
        pageBean.setPageData(list);
        int count = userDao.countOfUser(fields);
        pageBean.setTotalCount(count);

        return pageBean;
    }

    @Override
    @Transactional
    public boolean modifyUser(User user) {
        return userDao.updateUser(user) > 0;
    }
}

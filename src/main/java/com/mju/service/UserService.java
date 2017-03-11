package com.mju.service;

import com.mju.model.dto.PageBean;
import com.mju.model.entity.User;

import java.util.Map;
import java.util.Optional;

/**
 * Created by Hua on 2017/3/2.
 */
public interface UserService {
    User getUserById(String userId);

    User getUserByUsername(String username);

    boolean saveUser(User user);

    boolean delUserbyId(String userId);

    PageBean<User> queryUser(Map<String, Object> fields);

    boolean modifyUser(User user);
}

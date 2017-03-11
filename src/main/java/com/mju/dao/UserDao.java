package com.mju.dao;

import com.mju.model.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Created by Hua on 2017/3/2.
 */
@Mapper
public interface UserDao {
    int addUser(User user);

    User getUserById(String userId);

    User getUserByUsername(String username);

    List<User> queryUser(Map<String, Object> map);

    int countOfUser(Map<String, Object> map);

    int delUser(String userId);

    int updateUser(User user);

}

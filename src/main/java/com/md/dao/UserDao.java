package com.md.dao;

import com.md.entity.User;

import java.util.List;
import java.util.Map;

public interface UserDao {

    List<User> query(Map<String, Object> map);

    int insert(User user);

    int update(Map<String, Object> map);

    int count(Map<String, Object> map);

    User detail(Map<String, Object> map);

    int delete(Map<String, Object> map);

}

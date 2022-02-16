package com.md.service;

import com.md.entity.User;

import java.util.List;

public interface UserService {

    /**
     * 添加一条记录
     * @param pi
     * @return
     */
    public int create(User pi);


    /**
     * 根据id进行删除
     * @param ids
     * @return
     */
    public int delete(String ids);

    /**
     * 更新传入对象
     * @param user
     * @return
     */
    public int update(User user);


    /**
     * 查询
     * @param user
     * @return
     */
    public List<User> query(User user);


    /**
     * 查询一个
     * @param id
     * @return
     */
    public User detail(Integer id);

    public int count(User user);


    /**
     * 用户登录，判断用户名和密码
     * @param userName
     * @param password
     * @return
     */
    User login(String userName, String password);
}

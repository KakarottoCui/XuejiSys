package com.md.service;

import com.md.entity.Teacher;

import java.util.List;

public interface TeacherService {
    /**
     * 添加一条记录
     * @param pi
     * @return
     */
    public int create(Teacher pi);


    /**
     * 根据id进行删除
     * @param ids
     * @return
     */
    public int delete(String ids);

    /**
     * 更新传入对象
     * @param teacher
     * @return
     */
    public int update(Teacher teacher);


    /**
     * 查询
     * @param teacher
     * @return
     */
    public List<Teacher> query(Teacher teacher);


    /**
     * 查询一个
     * @param id
     * @return
     */
    public Teacher detail(Integer id);

    public int count(Teacher teacher);


    /**
     * 登录，判断用户名和密码
     */
    Teacher login(String teacherName, String password);
}

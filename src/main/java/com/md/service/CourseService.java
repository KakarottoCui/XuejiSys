package com.md.service;

import com.md.entity.Course;

import java.util.List;

public interface CourseService {
    /**
     * 添加一条记录
     * @param pi
     * @return
     */
    public int create(Course pi);


    /**
     * 根据id进行删除
     * @param ids
     * @return
     */
    public int delete(String ids);

    /**
     * 更新传入对象
     * @param course
     * @return
     */
    public int update(Course course);


    /**
     * 查询
     * @param course
     * @return
     */
    public List<Course> query(Course course);


    /**
     * 查询一个
     * @param id
     * @return
     */
    public Course detail(Integer id);

    public int count(Course course);
}

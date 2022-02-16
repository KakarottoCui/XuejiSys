package com.md.service;

import com.md.entity.Job;

import java.util.List;

public interface JobService {
    /**
     * 添加一条记录
     * @param pi
     * @return
     */
    public int create(Job pi);


    /**
     * 根据id进行删除
     * @param id
     * @return
     */
    public int delete(Integer id);

    /**
     * 更新传入对象
     * @param job
     * @return
     */
    public int update(Job job);


    /**
     * 查询
     * @param job
     * @return
     */
    public List<Job> query(Job job);


    /**
     * 查询一个
     * @param id
     * @return
     */
    public Job detail(Integer id);

    public int count(Job job);
}

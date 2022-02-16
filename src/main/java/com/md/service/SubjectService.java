package com.md.service;

import com.md.entity.Subject;

import java.util.List;

public interface SubjectService {
    /**
     * 添加一条记录
     * @param pi
     * @return
     */
    public int create(Subject pi);


    /**
     * 根据id进行删除
     * @param ids
     * @return
     */
    public int delete(String ids);

    /**
     * 更新传入对象
     * @param subject
     * @return
     */
    public int update(Subject subject);


    /**
     * 查询
     * @param subject
     * @return
     */
    public List<Subject> query(Subject subject);


    /**
     * 查询一个
     * @param id
     * @return
     */
    public Subject detail(Integer id);

    public int count(Subject subject);
}

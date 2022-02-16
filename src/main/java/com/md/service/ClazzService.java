package com.md.service;

import com.md.entity.Clazz;

import java.util.List;

public interface ClazzService {

    /**
     * 添加一条记录
     * @param pi
     * @return
     */
    public int create(Clazz pi);


    /**
     * 根据id进行删除
     * @param ids
     * @return
     */
    public int delete(String ids);

    /**
     * 更新传入对象
     * @param clazz
     * @return
     */
    public int update(Clazz clazz);


    /**
     * 查询
     * @param clazz
     * @return
     */
    public List<Clazz> query(Clazz clazz);


    /**
     * 查询一个
     * @param id
     * @return
     */
    public Clazz detail(Integer id);

    public int count(Clazz clazz);
}

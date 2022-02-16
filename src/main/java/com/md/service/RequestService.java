package com.md.service;

import com.md.entity.Request;

import java.util.List;

public interface RequestService {

    /**
     * 添加一条记录
     * @param pi
     * @return
     */
    public int create(Request pi);


    /**
     * 根据id进行删除
     * @param id
     * @return
     */
    public int delete(Integer id);

    /**
     * 更新传入对象
     * @param request
     * @return
     */
    public int update(Request request);


    /**
     * 查询
     * @param request
     * @return
     */
    public List<Request> query(Request request);


    /**
     * 查询一个
     * @param id
     * @return
     */
    public Request detail(Integer id);

    public int count(Request request);
}

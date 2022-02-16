package com.md.service;

import com.md.entity.Score;

import java.util.List;

public interface ScoreService {
    /**
     * 添加一条记录
     * @return
     */
    public int create(String sectionIds, String courseIds, Integer studentId);


    /**
     * 根据id进行删除
     * @param id
     * @return
     */
    public int delete(Integer id);

    /**
     * 更新传入对象
     * @param score
     * @return
     */
    public int update(Score score);


    /**
     * 查询
     * @param score
     * @return
     */
    public List<Score> query(Score score);


    /**
     * 查询一个
     * @param id
     * @return
     */
    public Score detail(Integer id);

    public int count(Score score);


    public int update(Integer courseId, Integer sectionId, String stuIds, String scores);
}

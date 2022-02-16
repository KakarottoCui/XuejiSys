package com.md.service;

import com.md.entity.Section;
import com.md.entity.Student;

import java.util.List;
import java.util.Map;

public interface SectionService {
    /**
     * 添加一条记录
     * @param pi
     * @return
     */
    public int create(Section pi);


    /**
     * 根据id进行删除
     * @param ids
     * @return
     */
    public int delete(String ids);

    /**
     * 更新传入对象
     * @param section
     * @return
     */
    public int update(Section section);


    /**
     * 查询
     * @param section
     * @return
     */
    public List<Section> query(Section section);


    /**
     * 查询一个
     * @param id
     * @return
     */
    public Section detail(Integer id);

    public int count(Section section);


    /**
     * 按照学生查询班级开课
     * @param studentId
     * @return
     */
    public List<Section> queryByStudent(Integer studentId);
}

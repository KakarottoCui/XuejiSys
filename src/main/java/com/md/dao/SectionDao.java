package com.md.dao;

import com.md.entity.Section;

import java.util.List;
import java.util.Map;

public interface SectionDao {
    public int create(Section pi);

    public int delete(Map<String, Object> paramMap);

    public int update(Map<String, Object> paramMap);

    public List<Section> query(Map<String, Object> paramMap);

    public Section detail(Map<String, Object> paramMap);

    public int count(Map<String, Object> paramMap);

//  按照学生查询班级开课
    public List<Section> queryByStudent(Map<String, Object> paramMap);

    public List<Section> queryByTeacher(Map<String, Object> paramMap);
}

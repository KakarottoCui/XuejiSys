package com.md.dao;

import com.md.entity.Subject;

import java.util.List;
import java.util.Map;

public interface SubjectDao {
    public int create(Subject pi);

    public int delete(Map<String, Object> paramMap);

    public int update(Map<String, Object> paramMap);

    public List<Subject> query(Map<String, Object> paramMap);

    public Subject detail(Map<String, Object> paramMap);

    public int count(Map<String, Object> paramMap);
}

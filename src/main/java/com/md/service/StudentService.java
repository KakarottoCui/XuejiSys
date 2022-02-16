package com.md.service;
import com.md.entity.Student;

import java.util.HashMap;
import java.util.List;

public interface StudentService {
    /**
     * 添加一条记录
     * @param pi
     * @return
     */
    public int create(Student pi);


    /**
     * 根据id进行删除
     * @param ids
     * @return
     */
    public int delete(String ids);

    /**
     * 更新传入对象
     * @param student
     * @return
     */
    public int update(Student student);


    /**
     * 查询
     * @param student
     * @return
     */
    public List<Student> query(Student student);


    /**
     * 查询一个
     * @param id
     * @return
     */
    public Student detail(Integer id);

    public int count(Student student);

    /**
     * 登录，判断用户名和密码
     */
    Student login(String stuNo, String password);




    public List<HashMap> querySelectStudent(Integer courseId, Integer sectionId);


    public List<Student> queryStudentByTeacher(Integer teacherId, Integer clazzId, Integer subjectId);



}

package com.md.service.Impl;

import com.github.pagehelper.PageHelper;
import com.md.dao.StudentDao;
import com.md.entity.Student;
import com.md.service.StudentService;
import com.md.utils.BeanMapUtils;
import com.md.utils.MD5Utils;
import com.md.utils.MapParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentDao studentDao;

    @Override
    public int create(Student pi) {
        // 默认的学生状态
        pi.setStatus(Student.StatusType.type_1);
        pi.setStuPwd(MD5Utils.getMD5(pi.getStuPwd()));

        return studentDao.create(pi);
    }

    @Override
    public int delete(String ids) {
        // 前台传过来的是要删除的id字符串，使用的逗号分隔
        String[] arr = ids.split(",");
        int flag = 0;
        for (String s : arr){
            flag = studentDao.delete(MapParameter.getInstance().add("id",Integer.parseInt(s)).getMap());
        }

        return flag;
    }

    @Override
    public int update(Student student) {
        return studentDao.update(
                MapParameter.getInstance().
                        add(BeanMapUtils.beanToMapForUpdate(student))
                        .addId(student.getId())
                        .getMap());
    }

    @Override
    public List<Student> query(Student student) {
// 传分页的话就使用分页，没有传的话就正常的查询
        if (student != null && student.getPage() != null){
            // 使用分页
            PageHelper.startPage(student.getPage(),student.getLimit());
        }
        return studentDao.query(BeanMapUtils.beanToMap(student));
    }

    @Override
    public Student detail(Integer id) {
        return studentDao.detail(MapParameter.getInstance().add("id",id).getMap());
    }

    @Override
    public int count(Student student) {
        return studentDao.count(BeanMapUtils.beanToMap(student));
    }

    @Override
    public Student login(String stuNo, String password) {
        // 根据用户名和密码进行查询，
        // 使用封装好的工具类，也可以直接使用map
        Map<String, Object> map = MapParameter.getInstance().add("stuNo", stuNo).add("stuPwd", password).getMap();

        return studentDao.detail(map);
    }




    @Override
    public List<HashMap> querySelectStudent(Integer courseId, Integer sectionId) {
        Map<String, Object> map = MapParameter.getInstance()
                .add("courseId", courseId)
                .add("sectionId", sectionId)
                .getMap();

        return studentDao.querySelectStudent(map);
    }

    @Override
    public List<Student> queryStudentByTeacher(Integer teacherId, Integer clazzId, Integer subjectId) {
        Map<String, Object> map = MapParameter.getInstance()
                .add("teacherId", teacherId)
                .add("clazzId", clazzId)
                .add("subjectId", subjectId)
                .getMap();

        return studentDao.queryStudentByTeacher(map);
    }
}

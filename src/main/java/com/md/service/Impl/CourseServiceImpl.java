package com.md.service.Impl;

import com.github.pagehelper.PageHelper;
import com.md.dao.CourseDao;
import com.md.entity.Course;
import com.md.service.CourseService;
import com.md.utils.BeanMapUtils;
import com.md.utils.MapParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    
    @Autowired
    private CourseDao courseDao;

    @Override
    public int create(Course pi) {
        return courseDao.create(pi);
    }

    @Override
    public int delete(String ids) {

        // 前台传过来的是要删除的id字符串，使用的逗号分隔
        String[] arr = ids.split(",");
        int flag = 0;
        for (String s : arr){
            flag = courseDao.delete(MapParameter.getInstance().add("id",Integer.parseInt(s)).getMap());
        }

        return flag;
    }

    @Override
    public int update(Course course) {
        // 首先将对象转为map再进行操作
        // 1. 首先将要修改的对象传入的map，并且以update开头
//        BeanMapUtils.beanToMapForUpdate(subject);
        // 2. 放入到map集合中
//        MapParameter.getInstance().add();
        // 3. 最后放入需要修改的id

        return courseDao.update(
                MapParameter.getInstance().
                        add(BeanMapUtils.beanToMapForUpdate(course))
                        .addId(course.getId())
                        .getMap());
        // set后面的就是传入的对象，以update开头的：BeanMapUtils.beanToMapForUpdate(user)，where后面的就是需要修改的id：addId(user.getId())
//        update tb_user SET id = ?, name = ? WHERE id = ?
        // 首先将对象转为map再进行操作
    }

    @Override
    public List<Course> query(Course course) {
// 传分页的话就使用分页，没有传的话就正常的查询
        if (course != null && course.getPage() != null){
            // 使用分页
            PageHelper.startPage(course.getPage(),course.getLimit());
        }
        return courseDao.query(BeanMapUtils.beanToMap(course));
    }

    @Override
    public Course detail(Integer id) {
        return courseDao.detail(MapParameter.getInstance().add("id",id).getMap());
    }

    @Override
    public int count(Course course) {
        return courseDao.count(BeanMapUtils.beanToMap(course));
    }
}

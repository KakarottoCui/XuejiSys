package com.md.service.Impl;

import com.github.pagehelper.PageHelper;
import com.md.dao.TeacherDao;
import com.md.entity.Teacher;
import com.md.service.TeacherService;
import com.md.utils.BeanMapUtils;
import com.md.utils.MD5Utils;
import com.md.utils.MapParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherDao teacherDao;

    @Override
    public int create(Teacher pi) {

        pi.setTeacherPwd(MD5Utils.getMD5(pi.getTeacherPwd()));

        return teacherDao.create(pi);
    }

    @Override
    public int delete(String ids) {

        // 前台传过来的是要删除的id字符串，使用的逗号分隔
        String[] arr = ids.split(",");
        int flag = 0;
        for (String s : arr){
            flag = teacherDao.delete(MapParameter.getInstance().add("id",Integer.parseInt(s)).getMap());
        }

        return flag;
    }

    @Override
    public int update(Teacher teacher) {
        // 首先将对象转为map再进行操作
        // 1. 首先将要修改的对象传入的map，并且以update开头
//        BeanMapUtils.beanToMapForUpdate(subject);
        // 2. 放入到map集合中
//        MapParameter.getInstance().add();
        // 3. 最后放入需要修改的id

        return teacherDao.update(
                MapParameter.getInstance().
                        add(BeanMapUtils.beanToMapForUpdate(teacher))
                        .addId(teacher.getId())
                        .getMap());
        // set后面的就是传入的对象，以update开头的：BeanMapUtils.beanToMapForUpdate(user)，where后面的就是需要修改的id：addId(user.getId())
//        update tb_user SET id = ?, name = ? WHERE id = ?
        // 首先将对象转为map再进行操作
    }

    @Override
    public List<Teacher> query(Teacher teacher) {
        // 传分页的话就使用分页，没有传的话就正常的查询
        if (teacher != null && teacher.getPage() != null){
            // 使用分页
            PageHelper.startPage(teacher.getPage(),teacher.getLimit());
        }
        return teacherDao.query(BeanMapUtils.beanToMap(teacher));
    }

    @Override
    public Teacher detail(Integer id) {
        return teacherDao.detail(MapParameter.getInstance().add("id",id).getMap());
    }

    @Override
    public int count(Teacher teacher) {
        return teacherDao.count(BeanMapUtils.beanToMap(teacher));
    }

    @Override
    public Teacher login(String teacherName, String password) {
        // 根据用户名和密码进行查询，
        // 使用封装好的工具类，也可以直接使用map
        Map<String, Object> map = MapParameter.getInstance().add("teacherName", teacherName).add("teacherPwd", password).getMap();

        return teacherDao.detail(map);
    }
}

package com.md.service.Impl;

import com.github.pagehelper.PageHelper;
import com.md.dao.UserDao;

import com.md.entity.User;
import com.md.service.UserService;
import com.md.utils.BeanMapUtils;
import com.md.utils.MapParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;


    @Override
    public int create(User pi) {
        return userDao.insert(pi);
    }

    @Override
    public int delete(String ids) {
        // 没有使用工具类
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("id",id);
//        return userDao.delete(map);

        // 前台传过来的是要删除的id字符串，使用的逗号分隔
        String[] arr = ids.split(",");
        int flag = 0;
        for (String s : arr){
            flag = userDao.delete(MapParameter.getInstance().add("id",Integer.parseInt(s)).getMap());
        }

        return flag;
    }

    @Override
    public int update(User user) {
        // 首先将对象转为map再进行操作
        // 1. 首先将要修改的对象传入的map，并且以update开头
//        BeanMapUtils.beanToMapForUpdate(user);
        // 2. 放入到map集合中
//        MapParameter.getInstance().add();
        // 3. 最后放入需要修改的id

        return userDao.update(MapParameter.getInstance().add(BeanMapUtils.beanToMapForUpdate(user)).addId(user.getId()).getMap());
        // set后面的就是传入的对象，以update开头的：BeanMapUtils.beanToMapForUpdate(user)，where后面的就是需要修改的id：addId(user.getId())
//        update tb_user SET id = ?, name = ? WHERE id = ?
    }

    @Override
    public List<User> query(User user) {

        // 传分页的话就使用分页，没有传的话就正常的查询
        if (user != null && user.getPage() != null){
            // 使用分页
            PageHelper.startPage(user.getPage(),user.getLimit());
        }
        return userDao.query(BeanMapUtils.beanToMap(user));
    }


    @Override
    public User detail(Integer id) {
        return userDao.detail(MapParameter.getInstance().add("id",id).getMap());
    }

    @Override
    public int count(User user) {
        return userDao.count(BeanMapUtils.beanToMap(user));
    }


    @Override
    public User login(String userName, String password) {
        // 根据用户名和密码进行查询，
        // 使用封装好的工具类，也可以直接使用map
        Map<String, Object> map = MapParameter.getInstance().add("userName", userName).add("userPwd", password).getMap();

        return userDao.detail(map);
    }
}

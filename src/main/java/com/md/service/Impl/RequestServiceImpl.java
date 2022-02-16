package com.md.service.Impl;

import com.github.pagehelper.PageHelper;
import com.md.dao.RequestDao;
import com.md.entity.Request;
import com.md.service.RequestService;
import com.md.utils.BeanMapUtils;
import com.md.utils.MapParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {
    @Autowired
    private RequestDao requestDao;

    @Override
    public int create(Request pi) {
        return requestDao.create(pi);
    }

    @Override
    public int delete(Integer id) {

        return requestDao.delete(MapParameter.getInstance().add("id",id).getMap());
    }

    @Override
    public int update(Request request) {
        // 首先将对象转为map再进行操作
        return requestDao.update(BeanMapUtils.beanToMapForUpdate(request));
    }

    @Override
    public List<Request> query(Request request) {
// 传分页的话就使用分页，没有传的话就正常的查询
        if (request != null && request.getPage() != null){
            // 使用分页
            PageHelper.startPage(request.getPage(),request.getLimit());
        }
        return requestDao.query(BeanMapUtils.beanToMap(request));
    }

    @Override
    public Request detail(Integer id) {
        return requestDao.detail(MapParameter.getInstance().add("id",id).getMap());
    }

    @Override
    public int count(Request request) {
        return requestDao.count(BeanMapUtils.beanToMap(request));
    }
}

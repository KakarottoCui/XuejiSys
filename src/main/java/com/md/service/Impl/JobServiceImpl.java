package com.md.service.Impl;

import com.github.pagehelper.PageHelper;
import com.md.dao.JobDao;
import com.md.entity.Job;
import com.md.service.JobService;
import com.md.utils.BeanMapUtils;
import com.md.utils.MapParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private JobDao jobDao;

    @Override
    public int create(Job pi) {
        return jobDao.create(pi);
    }

    @Override
    public int delete(Integer id) {

        return jobDao.delete(MapParameter.getInstance().add("id",id).getMap());
    }

    @Override
    public int update(Job job) {
        // 首先将对象转为map再进行操作
        return jobDao.update(BeanMapUtils.beanToMapForUpdate(job));
    }

    @Override
    public List<Job> query(Job job) {
// 传分页的话就使用分页，没有传的话就正常的查询
        if (job != null && job.getPage() != null){
            // 使用分页
            PageHelper.startPage(job.getPage(),job.getLimit());
        }
        return jobDao.query(BeanMapUtils.beanToMap(job));
    }

    @Override
    public Job detail(Integer id) {
        return jobDao.detail(MapParameter.getInstance().add("id",id).getMap());
    }

    @Override
    public int count(Job job) {
        return jobDao.count(BeanMapUtils.beanToMap(job));
    }
}

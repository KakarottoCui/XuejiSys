package com.md.service.Impl;

import com.github.pagehelper.PageHelper;
import com.md.dao.SectionDao;
import com.md.dao.StudentDao;
import com.md.entity.Section;
import com.md.entity.Student;
import com.md.service.SectionService;
import com.md.utils.BeanMapUtils;
import com.md.utils.MapParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SectionServiceImpl implements SectionService {

    @Autowired
    private SectionDao sectionDao;

    @Override
    public int create(Section pi) {
        return sectionDao.create(pi);
    }

    @Override
    public int delete(String ids) {

        String[] arr = ids.split(",");
        int flag = 0;
        for (String s : arr){
            flag = sectionDao.delete(MapParameter.getInstance().add("id",Integer.parseInt(s)).getMap());
        }

        return flag;
    }

    @Override
    public int update(Section section) {
        return sectionDao.update(
                MapParameter.getInstance().
                        add(BeanMapUtils.beanToMapForUpdate(section))
                        .addId(section.getId())
                        .getMap());
    }

    @Override
    public List<Section> query(Section section) {
// 传分页的话就使用分页，没有传的话就正常的查询
        if (section != null && section.getPage() != null){
            // 使用分页
            PageHelper.startPage(section.getPage(),section.getLimit());
        }
        return sectionDao.query(BeanMapUtils.beanToMap(section));
    }

    @Override
    public Section detail(Integer id) {
        return sectionDao.detail(MapParameter.getInstance().add("id",id).getMap());
    }

    @Override
    public int count(Section section) {
        return sectionDao.count(BeanMapUtils.beanToMap(section));
    }

    @Override
    public List<Section> queryByStudent(Integer studentId) {
       List<Section> sections = sectionDao.queryByStudent(MapParameter.getInstance().add("studentId",studentId).getMap());
       return sections;
    }
}

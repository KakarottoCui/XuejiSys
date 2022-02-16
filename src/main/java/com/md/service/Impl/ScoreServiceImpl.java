package com.md.service.Impl;

import com.github.pagehelper.PageHelper;
import com.md.dao.ScoreDao;
import com.md.entity.Score;
import com.md.service.ScoreService;
import com.md.utils.BeanMapUtils;
import com.md.utils.MapParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ScoreServiceImpl implements ScoreService {

    @Autowired
    private ScoreDao scoreDao;

    /**
     * 选课信息，多选
     * @param sectionIds
     * @param courseIds
     * @param studentId
     * @return
     */
    @Override
    public int create(String sectionIds, String courseIds, Integer studentId) {

        // 清除已经选过的课程数据
        Map<String, Object> map = MapParameter.getInstance().add("stuId", studentId).getMap();
        scoreDao.delete(map);
        int flag = 0;
        // 对数据进行分割
        String[] sectionIdArr = sectionIds.split(",");
        String[] courseIdArr = courseIds.split(",");
        // 循环插入
        for (int i = 0; i < sectionIdArr.length ; i++) {
            Score score = new Score();
            score.setSectionId(Integer.parseInt(sectionIdArr[i]));
            score.setCourseId(Integer.parseInt(courseIdArr[i]));
            score.setStuId(studentId);

            // 获取到每条信息进行添加
            flag = scoreDao.create(score);
        }

        return flag;
    }

    @Override
    public int delete(Integer id) {

        return scoreDao.delete(MapParameter.getInstance().add("id",id).getMap());
    }

    @Override
    public int update(Score score) {
        // 首先将对象转为map再进行操作
        return scoreDao.update(BeanMapUtils.beanToMapForUpdate(score));
    }

    @Override
    public List<Score> query(Score score) {
// 传分页的话就使用分页，没有传的话就正常的查询
        if (score != null && score.getPage() != null){
            // 使用分页
            PageHelper.startPage(score.getPage(),score.getLimit());
        }
        return scoreDao.query(BeanMapUtils.beanToMap(score));
    }

    @Override
    public Score detail(Integer id) {
        return scoreDao.detail(MapParameter.getInstance().add("id",id).getMap());
    }

    @Override
    public int count(Score score) {
        return scoreDao.count(BeanMapUtils.beanToMap(score));
    }


    /**
     * 老师给学生进行评分
     * @param courseId
     * @param sectionId
     * @param stuIds
     * @param scores
     * @return
     */
    @Override
    public int update(Integer courseId, Integer sectionId, String stuIds, String scores) {
        // 1. 对接受到的数据进行转换
        String[] stuIdArray = stuIds.split(",");
        String[] scoresArray = scores.split(",");

        int flag = 0;
        for (int i = 0; i < stuIdArray.length; i++) {
            Map<String, Object> map = MapParameter.getInstance()
                    .add("courseId", courseId)
                    .add("sectionId", sectionId)
                    .add("stuId", Integer.parseInt(stuIdArray[i]))
                    // 需要进行更新的成绩信息
                    .add("updateScore", Double.parseDouble(scoresArray[i]))
                    .getMap();

            flag = scoreDao.update(map);
        }

        return flag;
    }


}

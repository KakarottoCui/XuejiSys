package com.md.controller;

import com.md.entity.Course;
import com.md.entity.Score;
import com.md.entity.Section;
import com.md.entity.Student;
import com.md.service.CourseService;
import com.md.service.ScoreService;
import com.md.service.SectionService;
import com.md.service.StudentService;
import com.md.utils.MapControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/score")
public class ScoreController {


    @Autowired
    private ScoreService scoreService;

    @Autowired
    private SectionService sectionService;

    @Autowired
    private CourseService courseService;


    /**
     * 添加选课信息
     * @param sectionIds
     * @param courseIds
     * @param session
     * @return
     */
    @PostMapping("/create")
    @ResponseBody
    public Map<String, Object> create(String sectionIds, String courseIds , HttpSession session){


        Student student = (Student) session.getAttribute("user");
        int result = scoreService.create(sectionIds,courseIds,student.getId());

        if (result <= 0){
            return MapControl.getInstance().error().getMap();
        }
        return MapControl.getInstance().success().getMap();
    }


    /**
     * 页面跳转到添加成绩的页面
     */
    @GetMapping("/student_score")
    public String student_score(){
        return "score/student_score";
    }


    @PostMapping("/query_student_score")
    @ResponseBody
    public Map<String,Object> query_student_score(HttpSession session){
        Student student = (Student) session.getAttribute("user");
        Score score = new Score();
        score.setStuId(student.getId());
        // 获取到成绩表
        List<Score> scoreList = scoreService.query(score);
        // 进行嵌套查询，当然也可以直接使用sql语句来写

        List<Section> sectionList = sectionService.query(null);
        List<Course> courseList = courseService.query(null);

        scoreList.forEach(score1 -> {

            courseList.forEach(course -> {
                if (score1.getCourseId() == course.getId().intValue()){
                    score1.setCourse(course);
                }
            });

            sectionList.forEach(section -> {
                if (score1.getSectionId() == section.getId().intValue()){
                    score1.setSection(section);
                }
            });

            score1.setStudent(student);

        });



        return MapControl.getInstance().success().put("data",scoreList).getMap();
    }
}

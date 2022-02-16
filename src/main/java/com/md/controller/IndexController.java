package com.md.controller;

import com.md.entity.*;
import com.md.service.*;
import com.md.utils.MD5Utils;
import com.md.utils.MapControl;
import com.sun.javafx.collections.MappingChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {

    @Autowired
    private UserService userService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private ClazzService clazzService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private SectionService sectionService;

    @Autowired
    private  ScoreService scoreService;

    @GetMapping("/index")
    public String index(){
        return "index";
    }


    /**
     * 基本资料
     * @return
     */
    @GetMapping("/info")
    public String info(){
        return "info";
    }


    /**
     * 密码
     * @return
     */
    @GetMapping("/pwd")
    public String pwd(){
        return "pwd";
    }


    /**
     * 修改密码
     * @return
     */
    @PostMapping("/pwd")
    @ResponseBody
    public Map<String,Object> pwd(String sourcePwd,String newPwd, String type, Integer id){
        if ("1".equals(type)){
            User user = userService.detail(id);
            // 先比较原密码是否正确
            if (user.getUserPwd().equals(MD5Utils.getMD5(sourcePwd))){
                // 进行密码得修改
                User user_ = new User();
                user_.setId(id);
                user_.setUserPwd(MD5Utils.getMD5(newPwd));
                int update = userService.update(user_);
                if (update > 0){
                    return MapControl.getInstance().success().getMap();
                }else{
                    return MapControl.getInstance().error().getMap();
                }
            }else{
                return MapControl.getInstance().error("原密码有误").getMap();
            }
        }

        if ("2".equals(type)){
            Teacher teacher = teacherService.detail(id);
            // 先比较原密码是否正确
            if (teacher.getTeacherPwd().equals(MD5Utils.getMD5(sourcePwd))){
                // 进行密码得修改
                Teacher teacher_ = new Teacher();
                teacher_.setId(id);
                teacher_.setTeacherPwd(MD5Utils.getMD5(newPwd));
                int update = teacherService.update(teacher_);
                if (update > 0){
                    return MapControl.getInstance().success().getMap();
                }else{
                    return MapControl.getInstance().error().getMap();
                }
            }else{
                return MapControl.getInstance().error("原密码有误").getMap();
            }
        }

        if ("3".equals(type)){
            Student student = studentService.detail(id);
            // 先比较原密码是否正确
            if (student.getStuPwd().equals(MD5Utils.getMD5(sourcePwd))){
                // 进行密码得修改
                Student student_ = new Student();
                student_.setId(id);
                student_.setStuPwd(MD5Utils.getMD5(newPwd));
                int update = studentService.update(student_);
                if (update > 0){
                    return MapControl.getInstance().success().getMap();
                }else{
                    return MapControl.getInstance().error().getMap();
                }
            }else{
                return MapControl.getInstance().error("原密码有误").getMap();
            }
        }

        return MapControl.getInstance().error().getMap();
    }


    /**
     * 主页信息得展示，配置echarts
     * @return
     */
    @GetMapping("/main")
    public String main(ModelMap modelMap){

        // 1. 系统数据
        List<Clazz> clazzs = clazzService.query(null);
        List<Subject> subjects = subjectService.query(null);
        List<Teacher> teachers = teacherService.query(null);
        List<Course> courses = courseService.query(null);
        List<Section> sections = sectionService.query(null);
        List<Student> students = studentService.query(null);

        modelMap.put("clazzCnt",clazzs.size());
        modelMap.put("subjectCnt",subjects.size());
        modelMap.put("teacherCnt",teachers.size());
        modelMap.put("courseCnt",courses.size());
        modelMap.put("sectionCnt",sections.size());
        modelMap.put("studentCnt",students.size());


        // 2. 每个班级的数量
        ArrayList<Map<String, Object>> mapList = new ArrayList<>();
        for (Clazz clazz: clazzs){
            // 每个班级对应的学生数量
            HashMap<String, Object> map = new HashMap<>();
            map.put("name",clazz.getClazzName());
            int cnt = 0;
            for (Student student: students){
                if (student.getClazzId() == clazz.getId().intValue()){
                    cnt++;
                }
            }
            map.put("cnt",cnt);
            mapList.add(map);
        }

        modelMap.put("clazzList",mapList);
        return "main";
    }
}

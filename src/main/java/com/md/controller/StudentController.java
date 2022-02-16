package com.md.controller;

import com.md.entity.Clazz;
import com.md.entity.Student;
import com.md.entity.Subject;
import com.md.entity.Teacher;
import com.md.service.ClazzService;
import com.md.service.StudentService;
import com.md.service.SubjectService;
import com.md.utils.MapControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Component
@RequestMapping("/student")
public class StudentController {
    private final String LIST = "student/list";
    private final String ADD = "student/add";
    private final String UPDATE = "student/update";


    @Autowired
    private StudentService studentService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private ClazzService clazzService;

    /**
     * 插入一条新数据
     * @param student
     * @return
     */
    @PostMapping("/create")
    @ResponseBody
    public Map<String, Object> create(@RequestBody Student student){
        int result = studentService.create(student);
        if (result <= 0){
            return MapControl.getInstance().error().getMap();
        }
        return MapControl.getInstance().success().getMap();
    }


    /**
     * 根据id进行删除
     * @param ids
     * @return
     */
    @PostMapping("/delete")
    @ResponseBody
    public Map<String, Object> delete(String ids){
        int result = studentService.delete(ids);
        if (result <= 0){
            return MapControl.getInstance().error().getMap();
        }
        return MapControl.getInstance().success().getMap();
    }

    /**
     * 修改信息
     * @param student
     * @return
     */
    @PostMapping("/update")
    @ResponseBody
    public Map<String, Object> update(@RequestBody Student student){
        int result = studentService.update(student);
        if (result <= 0){
            return MapControl.getInstance().error().getMap();
        }
        return MapControl.getInstance().success().getMap();
    }


    /**
     * 获取一条详细的信息
     * @param id
     * @return
     */
    @GetMapping("/detail/{id}")
    public String  detail(@PathVariable("id") Integer id, ModelMap modelMap){
        Student student = studentService.detail(id);
        // 拿到所有的专业名称
        List<Subject> subjectList = subjectService.query(null);
        modelMap.addAttribute("subjectList",subjectList);
        modelMap.addAttribute("student",student);

        return UPDATE;
    }

    /**
     * 重点：查询，和专业班级一块查询
     * @param student
     * @return
     */
    @PostMapping("/query")
    @ResponseBody
    public Map<String, Object> query(Student student){
        List<Student> studentList = studentService.query(student);
        // 查询所有的专业
        List<Subject> subjectList = subjectService.query(null);
        // 查询所有的班级
        List<Clazz> clazzList = clazzService.query(null);


        // 循环赋值,两个表在一起进行查询
        studentList.forEach(entity ->{
            subjectList.forEach(subject -> {
                    if (entity.getSubjectId() == subject.getId()){
                        entity.setSubject(subject);
                    }
            });
            clazzList.forEach(clazz -> {
                if (entity.getClazzId() == clazz.getId()){
                    entity.setClazz(clazz);
                }
            });
        });

        // 总条数
        Integer count = studentService.count(student);
        return  MapControl.getInstance().success().put("data",studentList).put("count",count).getMap();
    }


    /**
     * 页面跳转
     * @return
     */
    @GetMapping("/list")
    public String list(){
        return LIST;
    }

    /**
     * 添加的时候先查询专业，然后再查询班级，用于前端选择使用
     *
     * @return
     */
    @GetMapping("/add")
    public String add(ModelMap modelMap){

        List<Subject> subjectList = subjectService.query(null);
        modelMap.addAttribute("subjectList",subjectList);

        return ADD;
    }


    /**
     * 老师查询学生的基本信息
     * 带有专业和班级
     * @return
     */
    @GetMapping("/teacher_student")
    public String teacher_student(ModelMap modelMap){
        List<Clazz> clazzList = clazzService.query(null);
        List<Subject> subjectList = subjectService.query(null);

        modelMap.addAttribute("clazzes",clazzList);
        modelMap.addAttribute("subjects",subjectList);

        return "student/teacher_student";
    }


    /**
     * 老师查询学生数据的展示
     * @param session
     * @param clazzId
     * @param subjectId
     * @return
     */
    @PostMapping("/teacher_student")
    @ResponseBody
    public Map<String, Object> teacher_student(HttpSession session, Integer clazzId, Integer subjectId){

        Teacher teacher = (Teacher)session.getAttribute("user");
        List<Student> students = studentService.queryStudentByTeacher(teacher.getId(),clazzId,subjectId);

        List<Subject> subjectList = subjectService.query(null);
        List<Clazz> clazzList = clazzService.query(null);

        // 设置关联关系
        students.forEach(student -> {
            subjectList.forEach(subject -> {
                if (subject.getId() == student.getSubjectId().intValue()){
                    student.setSubject(subject);
                }
            });

            clazzList.forEach(clazz -> {
                if (clazz.getId() == student.getClazzId().intValue()){
                    student.setClazz(clazz);
                }
            });
        });

        return MapControl.getInstance().success().add("data",students).getMap();
    }
}

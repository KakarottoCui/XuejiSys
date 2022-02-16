package com.md.controller;

import com.md.entity.*;
import com.md.service.*;
import com.md.utils.MapControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/section")
public class SectionController {
    private final String LIST = "section/list";
    private final String ADD = "section/add";
    private final String UPDATE = "section/update";


    @Autowired
    private SectionService sectionService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private ClazzService clazzService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private ScoreService scoreService;

    /**
     * 插入一条新数据
     * @param section
     * @return
     */
    @PostMapping("/create")
    @ResponseBody
    public Map<String, Object> create(@RequestBody Section section){
        int result = sectionService.create(section);
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
        int result = sectionService.delete(ids);
        if (result <= 0){
            return MapControl.getInstance().error().getMap();
        }
        return MapControl.getInstance().success().getMap();
    }

    /**
     * 修改信息
     * @param section
     * @return
     */
    @PostMapping("/update")
    @ResponseBody
    public Map<String, Object> update(@RequestBody Section section){
        int result = sectionService.update(section);
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
        Section section = sectionService.detail(id);
        // 拿到所有的专业名称
        List<Teacher> teacherList = teacherService.query(null);
        List<Course> courseList = courseService.query(null);

        modelMap.addAttribute("teacherList",teacherList);
        modelMap.addAttribute("courseList",courseList);

        modelMap.addAttribute("section",section);

        return UPDATE;
    }

    /**
     * 重点：查询，和专业班级一块查询
     * @param section
     * @return
     */
    @PostMapping("/query")
    @ResponseBody
    public Map<String, Object> query(Section section){
        List<Section> sectionList = sectionService.query(section);
        // 查询所有的老师
        List<Teacher> teacherList = teacherService.query(null);
        // 查询所有的课程
        List<Course> courseList = courseService.query(null);


//         循环赋值,两个表在一起进行查询
        sectionList.forEach(entity ->{
            teacherList.forEach(teacher -> {
                if (entity.getTeacherId() == teacher.getId()){
                    entity.setTeacher(teacher);
                }
            });
            courseList.forEach(course -> {
                if (entity.getCourseId() == course.getId()){
                    entity.setCourse(course);
                }
            });
        });
//
//        // 总条数
        Integer count = sectionService.count(section);
        return  MapControl.getInstance().success().put("data",sectionList).put("count",count).getMap();
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
    public String add(Integer clazzId, ModelMap modelMap){

        List<Teacher> teacherList = teacherService.query(null);
        List<Course> courseList = courseService.query(null);

        modelMap.addAttribute("teacherList",teacherList);
        modelMap.addAttribute("courseList",courseList);
        modelMap.addAttribute("clazzId",clazzId);

        return ADD;
    }


    /**
     * 节点树，利用ztree
     *  父节点为专业，子节点为专业下面对应的具体班级
     *  利用列表和map组合的形式
     *  格式是根据ztree的api来写的
     *  [{"children":[{"name":"软件工程2020102","id":16,"parentId":17},
     *  {"name":"网络通信2020103","id":17,"parentId":17}],"name":"计算机应用","id":17,"parentId":0},
     *  {"children":[{"name":"英语2020201","id":18,"parentId":18},{"name":"日语2020202","id":19,"parentId":18},
     *  {"name":"文学","id":23,"parentId":18}],"name":"英语","id":18,"parentId":0},
     *  {"children":[{"name":"电子商务2020301","id":20,"parentId":19},{"name":"物流2020302","id":21,"parentId":19}],"name":"电子商务","id":19,"parentId":0},
     *  {"children":[],"name":"计算机","id":22,"parentId":0}]
     * @return
     */
    @PostMapping("/tree")
    @ResponseBody
    public List<Map<String,Object>> tree(){
        List<Subject> subjectList = subjectService.query(null);
        List<Clazz> clazzList = clazzService.query(null);

        List<Map<String,Object>> list = new ArrayList<>();

        subjectList.forEach(subject -> {
            // 构造父节点
            HashMap<String, Object> map = new HashMap<>();
            map.put("id",subject.getId());
            map.put("name",subject.getSubjectName());
            map.put("parentId",0);

            // 构造子节点
            List<Map<String,Object>> childrenList = new ArrayList<>();
            clazzList.forEach(clazz -> {
                if (clazz.getSubjectId() == subject.getId()){
                    HashMap<String, Object> map1 = new HashMap<>();
                    map1.put("id",clazz.getId());
                    map1.put("name",clazz.getClazzName());
                    // 用来指定它的父节点
                    map1.put("parentId",subject.getId());
                    childrenList.add(map1);
                }
            });

            map.put("children",childrenList);

            list.add(map);
        });


        return list;
    }


    /**
     * 用于页面的跳转
     * @return
     */
    @GetMapping("/student_section")
    public String student_section(){
        return "section/student_section";
    }


    /**
     * 学生页面用于数据的显示
     * @param session
     * @return
     */
    @PostMapping("/query_student_section")
    @ResponseBody
    public Map<String, Object> student_section(HttpSession session){
        // 1. 获取当前登陆的信息
        Student student = (Student)session.getAttribute("user");
        // 2. 通过当前登录用户的id来获取课程信息，多表查询
        List<Section> sections = sectionService.queryByStudent(student.getId());

        for (Section section : sections) {
            System.out.println(section);
        }

        // 3.查询班级，老师，课程的详细信息
        List<Clazz> clazzList = clazzService.query(null);
        List<Teacher> teacherList = teacherService.query(null);
        List<Course> courseList = courseService.query(null);

        // 4. 嵌套循环,设置对应的值
        sections.forEach(section -> {
            clazzList.forEach(clazz -> {
                if (section.getClazzId() == clazz.getId().intValue()){
                    section.setClazz(clazz);
                }
            });

            teacherList.forEach(teacher -> {
                if (section.getTeacherId() == teacher.getId().intValue()){
                    section.setTeacher(teacher);
                }
            });

            courseList.forEach(course -> {
                if (section.getCourseId() == course.getId().intValue()){
                    section.setCourse(course);
                }
            });

        });

        return  MapControl.getInstance().success().put("data",sections).getMap();
    }


    /**
     * 页面的跳转
     * @return
     */
    @GetMapping("/teacher_section")
    public String teacher_section(){
        return "section/teacher_section";
    }


    /**
     * 评分展示
     * @param session
     * @return
     */
    @PostMapping("/query_teacher_section")
    @ResponseBody
    public Map<String, Object> query_teacher_section(HttpSession session){
        // 1. 获取当前登录的老师的信息
        Teacher teacher = (Teacher) session.getAttribute("user");
        Section param = new Section();
        param.setTeacherId(teacher.getId());

        // 2. 通过老师来进行查询
        List<Section> sections = sectionService.query(param);

        // 3. 查询班级，课程的详细信息
        List<Clazz> clazzList = clazzService.query(null);
        List<Course> courseList = courseService.query(null);

        // 4. 嵌套循环,设置对应的值,不用进行数据库的关联操作
        sections.forEach(section -> {
            clazzList.forEach(clazz -> {
                if (section.getClazzId() == clazz.getId().intValue()){
                    section.setClazz(clazz);
                }
            });

            courseList.forEach(course -> {
                if (section.getTeacherId() == course.getId().intValue()){
                    section.setCourse(course);
                }
            });

        });

        return  MapControl.getInstance().success().put("data",sections).getMap();
    }


    /**
     * 老师给学生评分页面
     * @param courseId
     * @param sectionId
     * @param modelMap
     * @return
     */
    @GetMapping("/teacher_student_score")
    public String teacher_student_score(Integer courseId, Integer sectionId, ModelMap modelMap){
        List<HashMap> maps = studentService.querySelectStudent(courseId, sectionId);
        modelMap.put("list",maps);
        modelMap.put("courseId",courseId);
        modelMap.put("sectionId",sectionId);

        return "section/teacher_student_score";
    }


    /**
     * 对学生进行评分
     * @param courseId
     * @param sectionId
     * @param stuIds
     * @param scores
     * @return
     */
    @PostMapping("/teacher_student_score")
    @ResponseBody
    public Map<String, Object> teacher_student_score(Integer courseId, Integer sectionId, String stuIds, String scores){
        int result = scoreService.update(courseId,sectionId,stuIds,scores);
        if (result <= 0){
            return MapControl.getInstance().error().getMap();
        }
        return MapControl.getInstance().success().getMap();
    }
}

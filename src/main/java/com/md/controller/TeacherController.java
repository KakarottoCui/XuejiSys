package com.md.controller;

import com.md.entity.Teacher;
import com.md.service.TeacherService;
import com.md.utils.MapControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("teacher/")
public class TeacherController {
    
    private final String LIST = "teacher/list";
    private final String ADD = "teacher/add";
    private final String UPDATE = "teacher/update";


    @Autowired
    private TeacherService teacherService;


    /**
     * 插入一条新数据
     * @param teacher
     * @return
     */
    @PostMapping("/create")
    @ResponseBody
    public Map<String, Object> create(@RequestBody Teacher teacher){
        int result = teacherService.create(teacher);
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
        int result = teacherService.delete(ids);
        if (result <= 0){
            return MapControl.getInstance().error().getMap();
        }
        return MapControl.getInstance().success().getMap();
    }

    /**
     * 修改信息
     * @param teacher
     * @return
     */
    @PostMapping("/update")
    @ResponseBody
    public Map<String, Object> update(@RequestBody Teacher teacher){
        int result = teacherService.update(teacher);
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
        Teacher teacher = teacherService.detail(id);

        modelMap.addAttribute("teacher",teacher);

        return UPDATE;
    }

    /**
     * 查询
     * @param teacher
     * @return
     */
    @PostMapping("/query")
    @ResponseBody
    public Map<String, Object> query(Teacher teacher){
        List<Teacher> teacherList = teacherService.query(teacher);
        // 总条数
        Integer count = teacherService.count(teacher);
        return  MapControl.getInstance().success().put("data",teacherList).put("count",count).getMap();
    }


    /**
     * 页面跳转
     * @return
     */
    @GetMapping("/list")
    public String list(){
        return LIST;
    }

    @GetMapping("/add")
    public String add(){
        return ADD;
    }


}

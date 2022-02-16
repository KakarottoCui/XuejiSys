package com.md.controller;

import com.md.entity.Subject;
import com.md.service.SubjectService;
import com.md.utils.MapControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/subject")
public class SubjectController {


    private final String LIST = "subject/list";
    private final String ADD = "subject/add";
    private final String UPDATE = "subject/update";


    @Autowired
    private SubjectService subjectService;


    /**
     * 插入一条新数据
     * @param subject
     * @return
     */
    @PostMapping("/create")
    @ResponseBody
    public Map<String, Object> create(Subject subject){
        int result = subjectService.create(subject);
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
        int result = subjectService.delete(ids);
        if (result <= 0){
            return MapControl.getInstance().error().getMap();
        }
        return MapControl.getInstance().success().getMap();
    }

    /**
     * 修改信息
     * @param subject
     * @return
     */
    @PostMapping("/update")
    @ResponseBody
    public Map<String, Object> update(Subject subject){
        int result = subjectService.update(subject);
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
        Subject subject = subjectService.detail(id);

        modelMap.addAttribute("subject",subject);

        return UPDATE;
    }

    /**
     * 查询
     * @param subject
     * @return
     */
    @PostMapping("/query")
    @ResponseBody
    public Map<String, Object> query(Subject subject){
        List<Subject> subjectList = subjectService.query(subject);
        // 总条数
        Integer count = subjectService.count(subject);
        return  MapControl.getInstance().success().put("data",subjectList).put("count",count).getMap();
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

package com.md.controller;

import com.md.entity.User;
import com.md.entity.User;
import com.md.service.UserService;
import com.md.service.UserService;
import com.md.utils.MD5Utils;
import com.md.utils.MapControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    private final String LIST = "user/list";
    private final String ADD = "user/add";
    private final String UPDATE = "user/update";


    @Autowired
    private UserService userService;


    /**
     * 插入一条新数据
     * @param user
     * @return
     */
    @PostMapping("/create")
    @ResponseBody
    public Map<String, Object> create(@RequestBody User user){
        int result = userService.create(user);
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
        int result = userService.delete(ids);
        if (result <= 0){
            return MapControl.getInstance().error().getMap();
        }
        return MapControl.getInstance().success().getMap();
    }

    /**
     * 修改信息
     * @param user
     * @return
     */
    @PostMapping("/update")
    @ResponseBody
    public Map<String, Object> update(@RequestBody User user){
        // 添加用户的时候密码需要进行加密
        user.setUserPwd(MD5Utils.getMD5(user.getUserPwd()));

        int result = userService.update(user);
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
        User user = userService.detail(id);

        modelMap.addAttribute("user",user);

        return UPDATE;
    }

    /**
     * 查询
     * @param user
     * @return
     */
    @PostMapping("/query")
    @ResponseBody
    public Map<String, Object> query(User user){
        List<User> userList = userService.query(user);
        // 总条数
        Integer count = userService.count(user);
        return  MapControl.getInstance().success().put("data",userList).put("count",count).getMap();
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

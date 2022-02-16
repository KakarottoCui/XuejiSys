package com.md.controller;

import com.md.entity.Student;
import com.md.entity.Teacher;
import com.md.entity.User;
import com.md.service.StudentService;
import com.md.service.TeacherService;
import com.md.service.UserService;
import com.md.utils.MD5Utils;
import com.md.utils.MapControl;
import com.md.utils.MapParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;


    /**
     * 默认跳转到登录页面
     * @return
     */
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    /**
     * 用户点击登录进行判断
     * @param session
     * @param userName
     * @param password
     * @param type
     * @param captcha
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public Map<String, Object> login(HttpSession session,String userName, String password, String type, String captcha){



        // 1. 校验传过来的参数不能为空
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password) || StringUtils.isEmpty(type) || StringUtils.isEmpty(captcha)){
            return MapControl.getInstance().error("信息不能为空").getMap();
        }
        // 2. 校验验证码
        String _captcha = (String) session.getAttribute("captcha");
        if (!captcha.equals(_captcha)){
            return MapControl.getInstance().error("验证码不正确").getMap();
        }

        // 3. 判断登录人员的类型
        // 管理员
        if("1".equals(type)){
            User user = userService.login(userName, MD5Utils.getMD5(password));
            if(user != null){
                session.setAttribute("user",user);
                session.setAttribute("type","1");
                return MapControl.getInstance().success().add("data",user).getMap();
            }else{
                return MapControl.getInstance().error("用户名或密码错误").getMap();
            }
        }
        // 老师
        if ("2".equals(type)){
            Teacher teacher = teacherService.login(userName, MD5Utils.getMD5(password));
            if (teacher != null){
                session.setAttribute("user",teacher);
                session.setAttribute("type","2");
                return MapControl.getInstance().success().add("data",teacher).getMap();
            }else{
                return MapControl.getInstance().error("用户名或密码有误").getMap();
            }
        }
        // 学生
        if ("3".equals(type)){
            Student student = studentService.login(userName, MD5Utils.getMD5(password));
            if (student != null){
                session.setAttribute("user",student);
                session.setAttribute("type","3");
                return MapControl.getInstance().success().add("data",student).getMap();
            }else{
                return MapControl.getInstance().error("用户名或密码有误").getMap();
            }
        }


        return MapControl.getInstance().error().getMap();
    }
}

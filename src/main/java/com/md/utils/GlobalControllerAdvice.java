package com.md.utils;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;


@ControllerAdvice
public class GlobalControllerAdvice {

    private final String ERROR = "error";

//    没有权限的话返回404页面
    @ExceptionHandler(value = PermissionException.class)
    public ModelAndView noPermission(PermissionException e){
        ModelAndView modelAndView = new ModelAndView(ERROR);
        modelAndView.addObject(ERROR,e.getMessage());
        return modelAndView;
    }


//    运行时异常返回map
    @ExceptionHandler(value = RuntimeException.class)
    @ResponseBody
    public Map<String, Object> noPermission(RuntimeException e){
        // 错误信息控制台显示，方便调试
        e.printStackTrace();
        return MapControl.getInstance().error(e.getMessage()).getMap();
    }
}

package com.itlike.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class controllerAdvice {
    //设置异常
    @ExceptionHandler(value = Exception.class)
    public ModelAndView exceptionHandler(RuntimeException e){
        System.out.println("触发了异常"+e.getMessage());
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("Exception",e.getMessage());
        modelAndView.setViewName("/WEB-INF/views/error.jsp");
        System.out.println(modelAndView.getViewName());
        return modelAndView;
    }
}

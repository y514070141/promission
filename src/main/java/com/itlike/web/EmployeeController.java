package com.itlike.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itlike.domain.AjaxRes;
import com.itlike.domain.Employee;
import com.itlike.domain.QueryVo;
import com.itlike.domain.pageListRes;
import com.itlike.service.employeeService;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class EmployeeController {
    /*注入业务层*/
    @Autowired
    private employeeService employeeService;

    //@RequiresPermissions("employee:index")要想使用词注解  必须配置第三方
    @RequestMapping("/employee")
    @RequiresPermissions("employee:index")
    public String employee(){
        return "employee";
    }

    @RequestMapping("/employeeList")
    @ResponseBody
    public pageListRes employeeList(QueryVo queryVo){
//        System.out.println(queryVo);
        /*调用业务层查询员工*/
        pageListRes pageListRes = employeeService.getEmployee(queryVo);
        return pageListRes;
    }

    /*接收员工添加表单*/
    @RequestMapping("/saveEmployee")
    @ResponseBody
    @RequiresPermissions("employee:add")
    public AjaxRes saveEmployee(Employee employee){
        System.out.println("----------------------"+employee);
        AjaxRes ajaxRes = new AjaxRes();
        try {
//            int i=1/0;
            /*调用业务层,保存用户*/
            employeeService.saveEmployee(employee);
            ajaxRes.setMsg("保存成功");
            employee.setState(true);
            ajaxRes.setSuccess(true);
        }catch (Exception e){
            ajaxRes.setSuccess(false);
            ajaxRes.setMsg("保存失败");
        }
        return ajaxRes;
    }

    /*接收更新员工请求*/
    @RequestMapping("/updateEmployee")
    @ResponseBody
    @RequiresPermissions("employee:edit")
    public AjaxRes updateEmployee(Employee employee){
        AjaxRes ajaxRes = new AjaxRes();
        try {
            /*调用业务层,更新员工*/
            employeeService.updateEmployee(employee);
            ajaxRes.setMsg("更新成功");
            ajaxRes.setSuccess(true);
        }catch (Exception e){
            ajaxRes.setSuccess(false);
            ajaxRes.setMsg("更新失败");
        }
        return ajaxRes;
    }

    @RequestMapping("/updateState")
    @ResponseBody
    @RequiresPermissions("employee:delete")
    public AjaxRes updateState(Long id){
        AjaxRes ajaxRes = new AjaxRes();
        try {
            /*调用业务层,更新员工*/
            employeeService.updateState(id);
            ajaxRes.setMsg("更新成功");
            ajaxRes.setSuccess(true);
        }catch (Exception e){
            ajaxRes.setSuccess(false);
            ajaxRes.setMsg("更新失败");
        }
        return ajaxRes;
    }

    //回显角色
    @RequestMapping("/getRoleById")
    @ResponseBody
    public List<Long> getRoleById(Long id){
        return employeeService.getRidByEid(id);
    }

//    设置异常
    @ExceptionHandler(value = AuthorizationException.class)
    public void exceptionHandler(HandlerMethod method, HttpServletResponse response) throws IOException {//发生异常的方法
        //跳转界面 ajax无法跳转  不是ajax可以跳转
        //判断是是不是json请求   是浏览器自己请求

        ResponseBody methodAnnotation=method.getMethodAnnotation(ResponseBody.class);
        //如果有@ResponBody注解
        if(methodAnnotation != null){
            //Ajax
            AjaxRes ajaxRes = new AjaxRes();
            ajaxRes.setMsg("你没有权限操作");
            ajaxRes.setSuccess(false);
            String s=new ObjectMapper().writeValueAsString(ajaxRes);
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(s);
        }else {
            try {//重定向
                response.sendRedirect("error.jsp");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    
}

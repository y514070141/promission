package com.itlike.web;

import com.itlike.domain.*;
import com.itlike.service.roleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class roleController {
    @Autowired
    private roleService roleService;
    @RequestMapping("/role")
    public String employee(){
        return "role";
    }


    @RequestMapping("/getRoles")
    @ResponseBody
    public pageListRes getRoles(QueryVo vo){
//        System.out.println(vo);
        pageListRes roles = roleService.getRoles(vo);
        return roles;
    }

    @RequestMapping("/saveRole")
    @ResponseBody
    public AjaxRes saveRole(Role role){
//        System.out.println("-------------------------saveRole="+role);
        AjaxRes ajaxRes=new AjaxRes();
        try{
            roleService.insertRoleAndPromission(role);
            ajaxRes.setSuccess(true);
            ajaxRes.setMsg("保存成功");
        }catch (Exception e){
            ajaxRes.setSuccess(false);
            ajaxRes.setMsg("保存失败");
        }
        return ajaxRes;
    }

    //通过rid查询她的权限   编辑权限回显
    @RequestMapping("/getPermissionByRid")
    @ResponseBody
    public List<Permission> getPermissionByRid(Role role){
        System.out.println("getPermissionByRid="+role);
        List<Permission> permissions = roleService.selectPermissionById(role.getRid());
        return permissions;
    }

    //修改角色
    @RequestMapping("/updateRole")
    @ResponseBody
    public AjaxRes updateRole(Role role){
        System.out.println("修改role="+role);
        AjaxRes ajaxRes=new AjaxRes();
        try{
            roleService.updatePermissionByRid(role);
            ajaxRes.setMsg("编辑---更新成功");
            ajaxRes.setSuccess(true);
            //System.out.println(ajaxRes.getMsg());
        }catch (Exception e){
            ajaxRes.setMsg("编辑---更新失败");
            ajaxRes.setSuccess(false);
        }
        return ajaxRes;
    }

    @RequestMapping("/deleteRoleByRid")
    @ResponseBody
    public AjaxRes deleteRoleByRid(Long rid){
        AjaxRes ajaxRes=new AjaxRes();
        try{
            roleService.deleteRole(rid);
            ajaxRes.setSuccess(true);
            ajaxRes.setMsg("删除成功");
        }catch (Exception e){
            ajaxRes.setSuccess(false);
            ajaxRes.setMsg("删除失败");
        }
       return ajaxRes;
    }


    //加载角色下拉框
    @RequestMapping("/roleList")
    @ResponseBody
    public List<Role> roleList(){
        return roleService.selectAllRole();
    }

}

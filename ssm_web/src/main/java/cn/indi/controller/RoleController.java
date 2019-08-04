package cn.indi.controller;

import cn.indi.domain.Permission;
import cn.indi.domain.Role;
import cn.indi.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @RequestMapping("/findAll.do")
    public ModelAndView findAll() throws Exception {
        ModelAndView mv = new ModelAndView();
        List <Role> roleList = roleService.findAll();
        mv.addObject("roleList",roleList);
        mv.setViewName("role-list");
        return mv;
    }
    @RequestMapping("/save.do")
    public String save(Role role) throws Exception {
        roleService.save(role);
        return "redirect:findAll.do";
    }
    //根据roleId查询角色，并查询出可以添加的全限
    @RequestMapping("/findRoleByIdAndAllPermission.do")
    public ModelAndView findRoleByIdAndAllPermission(@RequestParam(name = "id") int roleId) throws Exception {
        ModelAndView mv = new ModelAndView();
        //根据根据roleId查询role
        Role role = roleService.findById(roleId);
        // 根据roleId查询可以添加的全限
        List<Permission> otherPermissions = roleService.findOtherPermissions(roleId);
        mv.addObject("role",role);
        mv.addObject("permissionList",otherPermissions);
        mv.setViewName("role-permission-add");
        return mv;
    }
    //给角色添加用户权限操作
    @RequestMapping("/addPermissionToRole.do")
    public String addPermissionToRole(@RequestParam(name = "roleId") int roleId,@RequestParam(name = "ids") int[] ids) throws Exception {
        roleService.addPermissionToRole(roleId,ids);
        return "redirect:findAll.do";
    }
}

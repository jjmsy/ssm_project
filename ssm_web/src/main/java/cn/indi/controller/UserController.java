package cn.indi.controller;

import cn.indi.domain.Role;
import cn.indi.domain.UserInfo;
import cn.indi.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    //查询指定id的用户，并带有角色信息
    @RequestMapping("/findById.do")
    public ModelAndView findById(int id) throws Exception {
        ModelAndView mv = new ModelAndView();
        UserInfo userInfo = userService.findById(id);
        mv.addObject("user",userInfo);
        mv.setViewName("user-show1");
        return mv;
    }

    //用户添加
    @RequestMapping("/save.do")
    @PreAuthorize("authentication.principal.username=='tom'")//只让tom有权限执行添加方法
    public String save(UserInfo userInfo) throws Exception {
        userService.save(userInfo);
        //添加完成之后查询用户信息
        return "redirect:findAll.do";
    }

    @RequestMapping("/findAll.do")
    @PreAuthorize("hasRole('ROLE_ADMIN')")//只让ADMIN角色 查看用户信息
    public ModelAndView findAll() throws Exception {
        ModelAndView mv = new ModelAndView();
        List<UserInfo> list = userService.findAll();
        mv.addObject("userList",list);
        mv.setViewName("user-list");
        return mv;
    }

    //查询用户以及 用户可以添加的角色
    @RequestMapping("/findUserByIdAndAllRole.do")
    public ModelAndView findUserByIdAndAllRole(@RequestParam(name = "id" ,required = true) int userId) throws Exception {

        ModelAndView mv = new ModelAndView();
        //1.根据用户id查询用户
        UserInfo userInfo = userService.findById(userId);
        //2.根据用户的id查询可以添加的用户角色
       List<Role> otherRoles =  userService.findOtherRoles(userId);
       mv.addObject("user",userInfo);
       mv.addObject("roleList",otherRoles);
       mv.setViewName("user-role-add");
       return mv;
    }
    //给用户添加角色
    @RequestMapping("/addRoleToUser.do")
    public String addRoleToUser(@RequestParam(name = "userId") int userId,@RequestParam(name = "ids") int[] roleIds){
        userService.addRoleToUser( userId,roleIds);
        return "redirect:findAll.do";
    }
}

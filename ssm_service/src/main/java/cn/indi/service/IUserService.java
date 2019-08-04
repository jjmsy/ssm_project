package cn.indi.service;

import cn.indi.domain.Role;
import cn.indi.domain.UserInfo;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface IUserService extends UserDetailsService {
    List<UserInfo> findAll() throws Exception;

    //添加用户
    void save(UserInfo userInfo)throws Exception;

    //根据id查询用户
    UserInfo findById(int id) throws Exception;

    //查询可以添加的角色
    List<Role> findOtherRoles(int userId) throws Exception;

    //给用户添加角色
    void addRoleToUser(int userId, int[] roleIds);
}

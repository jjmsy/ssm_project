package cn.indi.service;

import cn.indi.domain.Permission;
import cn.indi.domain.Role;

import java.util.List;

public interface IRoleService {
    //查询所有角色信息
    List<Role> findAll() throws Exception;
    //添加角色信息
    void save(Role role)  throws Exception;

    Role findById(int roleId) throws Exception;

    List<Permission> findOtherPermissions(int roleId) throws Exception;

    //添加角色的权限操作
    void addPermissionToRole(int roleId, int[] ids) throws Exception;
}

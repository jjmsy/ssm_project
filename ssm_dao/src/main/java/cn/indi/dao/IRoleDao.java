package cn.indi.dao;

import cn.indi.domain.Permission;
import cn.indi.domain.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface IRoleDao {
    /**
     * 根据id查询出所有对应的角色
     * @param userId
     * @return
     * @throws Exception
     */
    @Select("select * from role where id in(select roleId from users_role where userId = #{userId})")
    //角色封装
    @Results({
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "roleName",column = "roleName"),
            @Result(property = "roleDesc",column = "roleDesc"),
            @Result(property = "permissions",column = "id",javaType = java.util.List.class,many=@Many(select = "cn.indi.dao.IPermissionDao.findPermissionByRoleId"))

    })
    List<Role> findRoleByUserId(Integer userId)throws Exception;

    @Select("select * from role")
    List<Role> findAll() throws Exception;

    @Insert("insert into role (roleName,roleDesc) values(#{roleName},#{roleDesc})")
    void save(Role role) throws Exception;

    @Select("select * from role where id = #{roleId}")
    Role findById(int roleId);

    @Select("select * from permission where id not in( select permissionId from role_permission where roleId = #{roleId})")
    List<Permission> findOtherPermissions(int roleId);

    @Insert("insert into role_permission (permissionId,roleId) values (#{roleId},#{id}) ")
    void addPermissionToRole(@Param("roleId") int roleId,@Param("id") int id);
}

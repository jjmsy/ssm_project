package cn.indi.dao;

import cn.indi.domain.Role;
import cn.indi.domain.UserInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserDao {
    @Select("select * from users where username = #{username}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "email", column = "email"),
            @Result(property = "password", column = "password"),
            @Result(property = "phoneNum", column = "phoneNum"),
            @Result(property = "status", column = "status"),
            @Result(property = "roles", column = "id",javaType = java.util.List.class,many = @Many(select = "cn.indi.dao.IRoleDao.findRoleByUserId")),
    })
    UserInfo findByName(String username) throws Exception;

    @Select("select * from users")
    List<UserInfo> findAll() throws Exception;

    //添加用户信息，密码加密
    @Insert("insert into users(email,username,password,phoneNum,status) values (#{email},#{username},#{password},#{phoneNum},#{status})")
    void save(UserInfo userInfo) throws Exception;

    @Select("select * from users where id = #{id}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "email", column = "email"),
            @Result(property = "password", column = "password"),
            @Result(property = "phoneNum", column = "phoneNum"),
            @Result(property = "status", column = "status"),
            @Result(property = "roles", column = "id",javaType = java.util.List.class,many = @Many(select = "cn.indi.dao.IRoleDao.findRoleByUserId"))
    })
    UserInfo findById(int id) throws Exception;

    @Select("select * from role where id not in(select roleId from users_role where userId = #{userId})")
    List<Role> findOtherRoles(int userId);

    @Insert("insert into users_role (userId,roleId) values (#{userId},#{roleId})")
    /**如果你的映射器的方法需要多个参数, 这个注解可以被应用于映射器的方法 参数来给每个参数一个名字。
     * 否则,多 参数将会以它们的顺序位置来被命名 (不包括任何 RowBounds 参数) 比如。 #{param1} , #{param2} 等 ,
     * 这是默认的。使 用 @Param(“person”),参数应该被命名为 #{person}。
     */
    void addRoleToUser(@Param("userId") int userId, @Param("roleId") int roleId);
}

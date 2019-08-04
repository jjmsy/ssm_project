package cn.indi.service.impl;

import cn.indi.dao.IUserDao;
import cn.indi.domain.Role;
import cn.indi.domain.UserInfo;
import cn.indi.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service("userService")
@Transactional//添加事务
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserDao userDao;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = null;
        try {
           userInfo = userDao.findByName(username);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //处理自己的用户对象封装成userDetails
        //User user = new User(userInfo.getUsername(),"{noop}"+userInfo.getPassword(),getAuthority(userInfo.getRoles()));
        User user = new User(userInfo.getUsername(),userInfo.getPassword(), userInfo.getStatus() == 0?false:true,true,true,true,getAuthority(userInfo.getRoles()));
        return user;
    }
    public List<SimpleGrantedAuthority> getAuthority(List<Role> roles){
        List<SimpleGrantedAuthority> list = new ArrayList <>();
        for (Role role : roles) {
            list.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName()));
        }

        return list;
    }

    @Override
    public List <UserInfo> findAll() throws Exception {
        return userDao.findAll();
    }

    @Override
    public void save(UserInfo userInfo) throws Exception {
        //spring-security对密码进行加密处理,加密之后再重新给set回去,则得到的密码就是加密之后的
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        userDao.save(userInfo);
    }

    @Override
    public UserInfo findById(int id) throws Exception {
        return userDao.findById(id);
    }

    @Override
    public List <Role> findOtherRoles(int userId) throws Exception {
        return userDao.findOtherRoles(userId);
    }

    @Override
    public void addRoleToUser(int userId, int[] roleIds) {
        for (int roleId : roleIds) {
            userDao.addRoleToUser(userId,roleId);
        }
    }
}

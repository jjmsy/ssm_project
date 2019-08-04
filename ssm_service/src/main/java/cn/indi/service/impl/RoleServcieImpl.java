package cn.indi.service.impl;

import cn.indi.dao.IRoleDao;
import cn.indi.domain.Permission;
import cn.indi.domain.Role;
import cn.indi.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleServcieImpl implements IRoleService {
    @Autowired
    private IRoleDao roleDao;
    @Override
    public List<Role> findAll() throws Exception {
        return roleDao.findAll();
    }

    @Override
    public void save(Role role) throws Exception{
        roleDao.save(role);
    }

    @Override
    public Role findById(int roleId) throws Exception {
        return roleDao.findById(roleId);
    }

    @Override
    public List <Permission> findOtherPermissions(int roleId) throws Exception {
        return roleDao.findOtherPermissions(roleId);
    }

    @Override
    public void addPermissionToRole(int roleId, int[] ids) throws Exception {
        for (int id : ids) {
            roleDao.addPermissionToRole(roleId,id);
        }
    }
}

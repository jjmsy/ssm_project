package cn.indi.service.impl;

import cn.indi.dao.IPermissionDao;
import cn.indi.domain.Permission;
import cn.indi.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PermissionImpl implements IPermissionService {
    @Autowired
    private IPermissionDao permissionDao;
    @Override
    public List<Permission> findAll() throws Exception {
        return permissionDao.findAll();
    }

    @Override
    public void save(Permission permission)throws Exception{
        permissionDao.save(permission);
    }
}

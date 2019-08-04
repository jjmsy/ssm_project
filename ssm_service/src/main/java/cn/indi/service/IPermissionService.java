package cn.indi.service;

import cn.indi.domain.Permission;

import java.util.List;

public interface IPermissionService {

    List<Permission> findAll() throws Exception;

    void save(Permission permission)throws Exception;
}

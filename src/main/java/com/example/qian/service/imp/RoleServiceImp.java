package com.example.qian.service.imp;

import com.example.qian.dao.RoleDao;
import com.example.qian.model.RoleModel;
import com.example.qian.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleServiceImp implements RoleService {
    @Autowired
    private RoleDao roleDao;

    public RoleModel findRoleById(int id){
        return roleDao.findRoleById(id);
    }

}

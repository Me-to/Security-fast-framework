package com.example.qian.service;

import com.example.qian.model.RoleModel;
import org.springframework.stereotype.Service;


@Service
public interface RoleService {
     RoleModel findRoleById(int id);
}

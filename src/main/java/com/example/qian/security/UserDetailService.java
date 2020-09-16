package com.example.qian.security;

import com.example.qian.model.RoleModel;
import com.example.qian.model.UserModel;
import com.example.qian.service.RoleService;
import com.example.qian.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class UserDetailService implements UserDetailsService {

    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UserCodeException {
        if (username==null||username.trim().length()<=0) {
            throw new UserCodeException("用户名为空");
        }
        UserModel userModel = userService.findUserByUsername(username);
        if (userModel == null){
            throw new UserCodeException("用户不存在!");
        }

        RoleModel roleModel = roleService.findRoleById(userModel.getRole());
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new GrantedAuthorityImp(roleModel.getName()));
        return new UserDetailsImp(userModel, roleModel, authorities);

    }

}

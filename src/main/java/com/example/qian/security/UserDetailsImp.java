package com.example.qian.security;

import com.example.qian.model.RoleModel;
import com.example.qian.model.UserModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.LinkedList;

/**
 * 用来整合用户和权限
 */


public class UserDetailsImp implements UserDetails {
    private Collection<? extends GrantedAuthority> authorities;
    private String username;
    private String password;

    /***
     * 角色ID
     */
    private long roleId;
    /**
     * 角色名
     */
    private String roleName;

    /**
     * 用户唯一ID
     */
    private String uid;

    /**
     * 访问许可
     */
    private LinkedList<String> visitPermission;
    /**
     * 是否启用
     */
    private boolean enabled;


    public UserDetailsImp(UserModel userModel, RoleModel roleModel, Collection<? extends GrantedAuthority> grantedAuthorities) {
        this.authorities = grantedAuthorities;
        this.username = userModel.getUsername();
        this.password = userModel.getPassword();
        this.uid = userModel.getUid();
        this.roleId =  roleModel.getId();
        this.roleName = roleModel.getName();
        this.enabled = userModel.getEnable() == 1; //如果是1的话,则表示可用返回true
    }

    public UserDetailsImp() {

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }


    public long getRoleId() {
        return roleId;
    }

    public String getRoleName() {
        return roleName;
    }


    public String getUid() {
        return uid;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}

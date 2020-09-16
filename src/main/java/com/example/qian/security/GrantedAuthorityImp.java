package com.example.qian.security;

import org.springframework.security.core.GrantedAuthority;

public class GrantedAuthorityImp implements GrantedAuthority {
    private String authority;
    @Override
    public String getAuthority() {
        return authority;
    }


    public GrantedAuthorityImp(String authority) {
        this.authority = authority;
    }

    public GrantedAuthorityImp() {
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
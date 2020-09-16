package com.example.qian.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.security.RolesAllowed;

@Controller
public class AuthController {

    /* 只有角色ONE才能访问 */
    @Secured("admin")
    @GetMapping("/one")
    public void one(){
        System.out.println("one");;
    }

    /* 只有角色TWO才能访问 */
    @PreAuthorize("hasRole('TWO')")
    @GetMapping("/two")
    public String two(){
        return "auth/two";
    }

    /* 只有角色THREE才能访问 */
    @RolesAllowed("THREE")
    @GetMapping("/three")
    public String three(){
        return "auth/three";
    }

    /* 权限不足时默认展示的页面 */
    @GetMapping("/limit")
    public String limit(){
        return "auth/limit";
    }
}
package com.example.qian;

import com.example.qian.model.RoleModel;
import com.example.qian.model.UserModel;
import com.example.qian.security.MyBCryptPasswordEncoder;
import com.example.qian.service.RoleService;
import com.example.qian.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestApplication {

    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;
    @Test
    public void test1(){
        System.out.println("1");
    }


    /**
     * 用来生成自己的账号和密码
     */
    @Test
    public void test2() {

        MyBCryptPasswordEncoder myBCryptPasswordEncoder=new MyBCryptPasswordEncoder();
        UserModel userModel = new UserModel();
        userModel.setUid((UUID.randomUUID().toString().replace("-","")));
       //自己的username
        /**
         * 最好这个username保持唯一性,否则就是加一个id来进行唯一性的查询,我加的是数据库索引唯一
         **/
        userModel.setUsername("qq1");
       //自己的密码
        userModel.setPassword(myBCryptPasswordEncoder.encode("123"));
       //自己的登录状态
        userModel.setEnable(1);
       //自己的登录权限
        userModel.setRole(2);
        int i = userService.insertUser(userModel);
       //0是失败,1是成功
        System.out.println("执行状态----" + i);
    }

    @Test
    public void test3(){
        RoleModel roleById = roleService.findRoleById(1);
        System.out.println(roleById);
    }
}


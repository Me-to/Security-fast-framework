package com.example.qian.service.imp;


import com.example.qian.dao.UserDao;
import com.example.qian.model.UserModel;
import com.example.qian.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImp implements UserService {
    @Autowired
    public UserDao userDao;

    public UserModel findUserByUsername(String usesrname){
        return userDao.findUserByUsername(usesrname);
    }

   public int insertUser(UserModel userModel){
      return userDao.insertUser(userModel);
   }
}

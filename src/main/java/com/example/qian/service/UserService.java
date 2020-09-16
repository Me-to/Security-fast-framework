package com.example.qian.service;

import com.example.qian.model.UserModel;
import org.springframework.stereotype.Service;


@Service
public interface UserService {
     UserModel findUserByUsername(String usesrname);
     int insertUser(UserModel userModel);
}

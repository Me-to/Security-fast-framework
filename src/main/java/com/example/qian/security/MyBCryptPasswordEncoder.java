package com.example.qian.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 密码加密
 */
@Component
public class MyBCryptPasswordEncoder extends BCryptPasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        String encode = super.encode(rawPassword);
        return encode.replace("$2a$", "$2y$");
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        encodedPassword = encodedPassword.replace("$2y$", "$2a$");
        return super.matches(rawPassword, encodedPassword);
    }
}

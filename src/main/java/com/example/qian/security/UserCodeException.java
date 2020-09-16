package com.example.qian.security;

import org.springframework.security.core.AuthenticationException;

/**
 * 自定义异常抛出
 */
public class UserCodeException extends AuthenticationException {
     UserCodeException(String msg) {
        super(msg);
    }

}

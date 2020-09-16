package com.example.qian.security.Image;

import org.springframework.security.core.AuthenticationException;

/**
 * 验证码类型的异常类
 */
public class ImagaeCodeException extends AuthenticationException {
    private static final long serialVersionUID = 5022575393500654458L;

    ImagaeCodeException(String message) {
        super(message);
    }
}

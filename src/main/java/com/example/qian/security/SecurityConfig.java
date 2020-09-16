package com.example.qian.security;

import com.example.qian.security.Image.ImageCodeFilter;
import com.example.qian.security.erro.AuthLimitHandler;
import com.example.qian.security.erro.SecurityAuthenticationHandler;
import com.example.qian.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)  //开启权限控制
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //注入自定义密码
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new MyBCryptPasswordEncoder();
    }


    //记住我
    @Autowired
    private RememberMeHandler rememberMeHandler;

    @Autowired
    SecurityAuthenticationHandler authenticationHandler;

    @Autowired
    private ImageCodeFilter imageCodeFilter;

    @Autowired
    UserDetailService userDetailService;

    @Autowired
    UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

       http.addFilterBefore(imageCodeFilter, UsernamePasswordAuthenticationFilter.class);  //图片验证码
        http.csrf().disable()
                .formLogin() //表单方式
//                .loginPage("/login.html")      //指定了跳转到登录页面的请求
                .loginPage("/login.html")
                .loginProcessingUrl("/login")  //对应登录页面form表单的 url
                .successHandler(authenticationHandler)
                .failureHandler(authenticationHandler)
                //用户权限管理
                .and()
                .exceptionHandling()
                .accessDeniedHandler(new AuthLimitHandler())
                .and()
                .rememberMe()
                .tokenRepository(rememberMeHandler)  //配置token持久化
                .tokenValiditySeconds(60 * 60 * 24)  //过期时间,单位为秒
                .userDetailsService(userDetailsService())  //自动处理登录逻辑
                .and()
                .authorizeRequests() //授权配置
                .antMatchers("/login.html", "/static/**", "/code/image", "/css/**", "/insert").permitAll()  //不拦截
                .anyRequest()        //所有请求
                .authenticated() ;   //都需要认证

    }

    //设定使用自己的密码格式
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //注入userDetailsService的实现类
        auth.userDetailsService(userDetailService).passwordEncoder(new MyBCryptPasswordEncoder());
    }
}

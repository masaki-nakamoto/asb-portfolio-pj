package com.spring.springbootapplication.config;  //依存関係を分ける

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncoderConfig {
@Bean  //部品化して他でも渡せる
  public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
  }
}

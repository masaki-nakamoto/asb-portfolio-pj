package com.spring.springbootapplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.springbootapplication.dto.UserAdd;
import com.spring.springbootapplication.repository.UserRepository;


@Service
public class UserInfoService {
  // フィールド
  // user情報 Mapper
  @Autowired
  private UserRepository userRepository;
  // password ServiceConfig
  @Autowired
  private PasswordEncoder passwordEncoder;
  public UserInfoService(UserRepository userRepository){
    this.userRepository = userRepository;
  }

  // user登録情報
  public void create(UserAdd userAdd){
    userAdd.setPassword(passwordEncoder.encode(userAdd.getPassword()));
    System.out.println("UserInfoSevice");  //debug
    System.out.println(userAdd);  //dubug
    userRepository.save(userAdd);
  }
}

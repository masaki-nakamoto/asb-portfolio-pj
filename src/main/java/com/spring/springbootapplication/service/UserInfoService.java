package com.spring.springbootapplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.springbootapplication.dao.UserInfoMapper;
import com.spring.springbootapplication.dto.UserAdd;

import com.spring.springbootapplication.entity.UserInfo;

import org.springframework.security.core.Authentication;


@Service
public class UserInfoService {
  // user情報 Mapper
  @Autowired
  private UserInfoMapper userInfoMapper;
  // password ServiceConfig
  @Autowired
  private PasswordEncoder passwordEncoder;


  // user登録情報
  public void save(UserAdd userAdd){
    userAdd.setPassword(passwordEncoder.encode(userAdd.getPassword()));
    userInfoMapper.save(userAdd);
  }

  // user情報取得
  public UserInfo findById(Long id){
    return userInfoMapper.findById(id);
  }
}

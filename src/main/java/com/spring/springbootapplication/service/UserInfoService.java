package com.spring.springbootapplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.springbootapplication.dao.UserInfoMapper;
import com.spring.springbootapplication.dto.UserAdd;
import com.spring.springbootapplication.entity.UserInfo;

// 追加↓
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;



@Service
public class UserInfoService implements UserDetailsService{
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
  // public UserInfo findById(Long id){
  //   return userInfoMapper.findById(id);
  // }

  // userログイン
  @Autowired
  public UserInfoService(UserInfoMapper userInfoMapper, PasswordEncoder passwordEncoder){
    this.userInfoMapper = userInfoMapper;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
    UserInfo userInfo = userInfoMapper.findByEmail(email)
    .orElseThrow(() -> new UsernameNotFoundException("don't find : " + email));

    String dbHash = userInfo.getPassword();
    System.out.println("DBハッシュ値: " + dbHash); //ハッシュ化は確認できた

    return User.builder()
    .username(userInfo.getEmail())
    .password(userInfo.getPassword())
    .roles("USER")
    .build();
  }
}
